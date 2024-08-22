예의 차리지 않고 반말로 글을 작성하는 것에 양해 부탁드립니다!

# 공통 응답에 대한 작은 팁

기본적인 `API`는 만들어 놓은 상태이다.

여기서는 공통 에러 응답 및 공통 성공 응답에 대한 작은 팁들을 소개해 보는 것이 주 목적이다.

따라서 다른 부분에 대한 설명은 건너뛰거나 언급만 한다.

이 깃허브 저장소를 만들게 된 이유가 있다.

현재 회사에서 `node.js`와 `express`조합의 서버를 타입스크립트, `nest.js`로 마이그레이션하고 있는 상황에서 나온 질문때문이다.

처음 프로젝트 시작시 `스프링 프레임워크`처럼 공통 유틸, 인터셉터와 로깅, 공통 에러 처리 모듈등 필요한 것들을 먼저 작업을 했다.

`nest.js`가 처음이고 다른 프레임워크를 다뤄 본 경험이 없기 때문에 관련 내용에 대한 설명들을 차근히 하고 있는 중이다.

주니어분들이 관련 내용들을 훝어보다가 공통 에러 처리 부분을 보고 컨트롤러의 응답 처리 방식도 공통으로 프레임워크에서 처리할 수 없냐는 질문을 던졌다.

`nest.js`에서도 충분히 가능하고 프로젝트가 작은 단위라 그 의견을 받아서 데코레이터를 활용해서 작업을 한 마무리했다.

# 과거 이걸로 고통받았던 기억이 떠오르기 시작한다!

아는 분들은 패스해도 되는 별거 없는 팁이지만 이 방법을 모르시거나 궁금하신 분들에게는 도움이 되었으면 하는 바램으로 단숨에 작성하게 되었다. 

`스프링 프레임워크` 또는 `스프링 부트`든 퉁쳐서 `스프링`이라고 표현할 것이다.

# 요구사항

- java version: 21    
- kotlin version: 2.0.0     
- spring boot: 3.3.1    
- rdbms: mySql     
- build: gradle-kotlin    

# 공통 에러 응답에 대한 팁

`Effective Java`에 따르면 비검사, 즉 `Unchecked Exception`을 구현하거나 또는 표준 예외를 사용하도록 권장한다.

그래서 대부분 프레임워크의 최상위에서 알아서 처리하도록 하는 방식을 사용하게 된다.

손을 대지 않고도 코를 풀수 있는 이 방법에는 문제가 하나 있다.

에러에 패키지부터 무슨 디비를 쓰고 있는지 등등등 별의별 신기한 메세지가 클라이언트로 전달된다.

"문제가 되나요?"

라는 질문에 문제가 될 수도 있고 아닐 수도 있다라고 뿐이 말을 못하겠다.

하지만 불필요한 정보까지 필터링 없이 그대로 보여주는 것은 분명 문제라고 볼 수 있다.

## @RestControllerAdvice 를 활용한 공통 에러 응답

`스프링`에서 가장 손쉽게 사용하는 방법은 `@RestControllerAdvice`을 이용하는 것이다

먼저 `Effective Java`에 의거해서 `Exception`, `RuntimeException`, `Throwable`, `Error`을 직접 사용 하지 말라고 강조한다.

`IllegalArgumentException`처럼 `RuntimeException`을 구현하고 있는 표준 예외를 사용하게 된다.

~~코틀린에서는 애초에 `unchecked Exception`을 강제하고 있지만~~

필요에 의한 커스텀 익셉션은 팀 내에서 정의해서 사용하는 방식으로 많이들 생각할 것이다.

여기서는 `DB`같은 곳에서 데이터를 조회할 때 데이터가 없다면 해당 에러를 던지기 위해서 `NotFoundException`을 만들어 사용해 보자.

익셉션 이름은 어떤 특정 상황을 잘 표현할 수 있다면 무엇이 되든 상관없다.

예를 들면 `EntityNotFoundException`이라든가...

하지만 범용적이지 못할 것 같으니 그냥 `NotFoundException`으로 진행시키자.

~~역시 이름 짓는게 젤 힘들다~~

```kotlin
class NotFoundException(message: String? = "Not found.") : RuntimeException(message)
```

그리고 공통 에러 응답을 위한 객체를 하나 만드는 과정을 거치게 된다.

```kotlin
data class ApiErrorResponse(
    val code: Int,
    val message: String,
)
```
그리고 아래와 같이 클래스를 하나 정의하면 끝난다.

```kotlin
@RestControllerAdvice
class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) = ApiErrorResponse(
        code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = ex.message!!,
    )
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException) = ApiErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            message = ex.message!!,
    )

    // more
    // 필요하면 여기에다 계속 진행시켜!
}
```
~~너무 간단하잖아!!!!~~

혹시 뭔가 막 하나씩 일일이 제어하는 것을 좋아하는 변태적인 코딩 스타일을 가지고 있는가?

그렇다면 `@RestControllerAdvice`를 사용하지 않고 컨트롤러의 엔드포인트에 `@ExceptionHandler`을 활용해서 하나씩 제어할 수 도 있다.

~~실제로 이런 방식을 좋아하는 분을 본 적이 있습니다!!!~~

## 좀더 디테일하게 만들어 볼 수 없을까?

하지만 `스프링`에서 우리가 예기치 못한 `Internal Server Error`가 발생한 경우 에러의 형태를 보면 다음과 같다.

```json
{
    "timestamp": "2024-08-19T07:22:18.135+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/members/1"
}
```
오호라? 

이게 딱 좋아 보인다!!!

그렇다면 기존에 만들어 놓은 `ApiErrorResponse`를 좀 수정해 보자.

```kotlin
data class ApiErrorResponse(
    val status: Int,
    val error: String,
    val path: String,
    val timestamp: LocalDateTime,
)
```
`path`부분은 어떻게 처리할 수 있을까?

`@ExceptionHandler`가 어떻게 사용되는 지 한번 살펴보자.

먼저 `스프링`는 기본적으로 `HandlerExceptionResolver`인터페이스를 구현한 `Resolver`들을 등록한다.

이 부분은 `HandlerExceptionResolverComposite`를 살펴보면 잘 알 수 있다.

해당 클래스의 내부에 리스트로 담고 있는 `resolver`를 보면 그 중 하나가 `ExceptionHandlerExceptionResolver`이다.

이것은 `@ExceptionHandler`가 붙은 메소드에 특정 시그니쳐 사용을 가능하게 한다.

그 중에 하나가 `HttpServletRequest`이다.

`HttpServletRequest`를 통해서 요청한 `requestURI`를 가져올 수 있기 때문에 다음과 같이 변경해 보자.

```kotlin
@RestControllerAdvice
class GlobalExceptionAdvice {

    /**
     * for internal server error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: HttpServletRequest) = ApiErrorResponse(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        error = ex.message!!,
        path = request.requestURI,
        timestamp = now(),
    )
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    protected fun handleNotFoundException(ex: NotFoundException, request: HttpServletRequest) = ApiErrorResponse(
        status = HttpStatus.NOT_FOUND.value(),
        error = ex.message!!,
        path = request.requestURI,
        timestamp = now(),
    )
}
```
일단 초반에 디비에는 어떤 데이터도 없기 때문에 아이디로 멤버 정보를 요청하는 `API`를 호출하면 다음과 같이 원하는 형태로 나오는 것을 보게 된다.

```json
{
  "status": 404,
  "error": "멤버 아이디 [1]로 조회된 사용자 정보가 없습니다.",
  "path": "/members/1",
  "timestamp": "2024-08-19T17:17:51.048872"
}
```

근데 이게 끝일까?

## 스프링 시큐리티 에러도 잡힐까?

`Spring WebFlux`와 관련된 깃허브 [musicshop: 브랜치 09-webflux-with-security](https://github.com/basquiat78/musicshop/tree/09-webflux-with-security)에는 이와 관련한 내용이 있다.

`스프링 시큐리티`는 실제 리퀘스트가 들어오는 `Dispatcher Servlet`을 타기 이전에 필터 역할을 한다.

따라서 `@RestControllerAdvice`에서 에러가 잡힐 수가 없다.

`Dispatcher Servlet`은 들어온 요청을 어디로 보낼지 라우팅 역할을 한다.

`스프링`에서는 이것을 구현해 요청을 어느 엔드포인트로 보낼지 결정한다.

하지만 `스프링 시큐리티`에서 그 이전에 필터에서 에러를 던지면 `@RestControllerAdvice`입장에서는 당연히 알 수 없는 것이다.

`Spring WebFlux`라면 손이 좀 가지만 `ErrorWebExceptionHandler`를 구현하면 된다.

`ErrorWebExceptionHandler`이 녀석은 비동기 요청 처리를 하는 스트림 파이프라인내에서 발생하는 모든 예외를 처리하도록 설계되어 있기 때문이다.

게다가 `Spring WebFlux`은 탐캣을 사용하면 사용할 수 도 있겠지만 기본적으로 `netty`를 사용한다.

그러나 `Spring MVC`에는 이런 놈이 없기 때문에 좀 다른 방식으로 접근해야 한다.

여기서는 `스프링 시큐리티`가 주된 목적이 아니기에 만일 `스프링 시큐리티` 관련 내용을 준비한다면 그때 상세하게 알아 볼 생각이다.

다만 키워드로 볼 수 있는 부분은 바로 `스프링 시큐리티`가 제공하는 두 개의 중요한 인터페이스를 먼저 알아야 한다.

인증에 대한 `AuthenticationEntryPoint`와 인가 예외 처리인 `AccessDeniedHandler` 이 두개이다.

다음과 같이 간략하게 코드만 소개해 본다.

```kotlin
@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest, 
        response: HttpServletResponse, 
        authException: AuthenticationException
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(
            """{
                "status": 401,
                "message": "Authentication error",
                "path": "${request.requestURI}",
                "timestamp": "${LocalDateTime.now()}"
            }""".trimIndent()
        )
    }
}

@Component
class CustomAccessDeniedHandler: AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(
            """{
                "status": 403,
                "message": "Access denied",
                "path": "${request.requestURI}",
                "timestamp": "${LocalDateTime.now()}"
            }""".trimIndent()
        )
    }
}
```
대충 이렇게 구현한 커스텀 객체를 `스프링 시큐리티`설정에서 `exceptionHandling`을 통해 등록을 하면 된다.

`스프링 시큐리티`를 함께 사용한다면 `Spring MVC`방식에선 이 두 가지를 혼용해서 전역 에러 처리를 해야 한다.

# 손이 많이 가는 성공 응답 객체

만일 여러분이 `REST API`을 `HATEOAS`규약으로 구축한다고 하면 이야기가 달라진다.

서버가 어떤 리소스를 제공할 때, 예를 들면 특정 아이디의 사용자 정보를 요청한다고 해보자.

그렇다면 그와 연관된 링크는 무엇이 있을지 한번 생각을 해보면 이런 것들을 떠올릴 수 있다.

```
1. 특정 아이디의 사용자 정보를 요청한다. 
    e.g) GET {{ your domain }}/members/10

2. 특정 아이디의 사용자 정보를 요청하는 셀프 링크를 제공한다. 
    e.g) GET {{ your domain }}/members/10 

3. 그 아이디의 사용자 정보를 변경할 수 있는 링크를 제공한다. 
    e.g) PUT (or PATCH) {{ your domain }}/members/10

4. 그 아이디의 사용자 정보를 삭제할 수 있는 링크를 제공한다. 
    e.g) DELETE {{ your domain }}/members/10
```
하지만 여기선 `HATEOAS`는 다루지 않을 것이다.

## 일단 보내고 보자!

```kotlin
@RestController
@RequestMapping("/members")
class MemberQueryController(
    private val queryMemberUseCase: QueryMemberUseCase,
    private val queryAllMembersUseCase: QueryAllMembersUseCase,
) {

    @GetMapping("")
    fun memberById(request: QueryMember): List<MemberDto> {
        return queryAllMembersUseCase.execute(request)
    }

    @GetMapping("/{id}")
    fun memberById(@PathVariable("id") id: Long): MemberDto {
        return queryMemberUseCase.execute(id)
    }
}
```
만일 위와 같이 컨트롤러를 작성하게 되면 `json`형식은 이런 모양이 될 것이다.

```json
{
  "id": 1,
  "name": "name_1",
  "nickName": "nickName_1"
}

// 이거나

[
  {
    "id": 1,
    "name": "name_1",
    "nickName": "nickName_1"
  },
  {
    "id": 2,
    "name": "name_2",
    "nickName": "nickName_2"
  },
]
```

하지만 앞서 공통 에러 처리 방식처럼 어떤 특정 형식으로 고정해서 보낸다면 프론트 개발자와 소통에서 좀 더 유리할 수 있을 것이다.

즉, 일관성을 유지하는 방식이 데이터를 다룰 때 편하기 때문이다.

## 고전적인 래퍼 클래스를 이용한 방법

보통 `Envelope Pattern`이라고 알려져 있는 `봉투 패턴`이 있다.

이 용어를 모르더라도 아마 여러분들은 이미 이 방법을 사용하고 있을 확률이 `99.9999999%`이다.

대부분 최종적으로 클라이언트에 보낼 객체 정보를 그대로 보내기 보다는 `스프링 부트`에서 기본적으로 제공하는 `ResponseEntity<T>`같은 것을 사용할 수 있다.

하지만 `ResponseEntity<T>`이 반환되는 타입을 강제할 수 있긴 하지만 뭔가 부족하다는 느낌을 준다.

그래서 좀 더 확장 가능하게 커스터마이징을 하는 경우가 많은데 이 방법을 한번 이용해서 공통 응답 객체를 클라이언트에 보내보도록 하자.

가장 일반적인 방식일 것이고 많이들 사용하는 방식일 것이다.

## 초간단 공통 응답 객체

일단 고려해 볼 것은 팀 내에서 공통 응답 포맷을 먼저 설정하는 것이다.

먼저 초간단하게 우리가 원하는 결과를 담는 `data`라는 필드가 존재할 수 있다.

그리고 만일 이 결과가 단일 객체가 아닌 리스트 형태라면 페이지 정보도 함께 보낼 필요가 있다.

이 프로젝트에서는 기존 방식의 `Offset-based Pagination`방식이 아닌 `Cursor-based Pagination`방식을 사용할 것이다.

그렇다면 다음과 같이 간단하게 정의해 볼 수 있다.

```kotlin
@JsonPropertyOrder("data", "pagination")
data class ApiResponse<T>(
    val data: T,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val pagination: Pagination?
) {
    companion object {
        /**
         * ApiResponse를 생성하는 정적 메소드
         * @param data
         * @param pagination
         * @return ResponseResult<T>
         */
        fun <T> create(data: T, pagination: Pagination? = null) = ApiResponse(data, pagination)
    }

}

data class Pagination(
    val nextId: Long?,
    val last: Boolean,
)
```
`Cursor-based Pagination`은 다음 조회할 `nextId`와 해당 페이지 정보가 마지막 페이지인지를 알려주는 `last`로 구성되어져 있다.

물론 `Offset-based Pagination`이라면 그에 맞춰서 `Pagination`객체를 변경하면 될 것이다.

이와 관련해 다음과 같은 유틸리티도 만든다.

```kotlin
fun <T> isLast(data: List<T>, size: Long): Boolean {
    val dataSize = data.size
    return dataSize <= size.toInt()
}

fun <T: EntityLongIdentifiable> getNextId(data: List<T>): Long? {
    if(data.isEmpty()) {
        return null
    }
    return data.last().id
}

fun <T: EntityLongIdentifiable> make(data: List<T>, size: Long): Pair<List<T>, Pagination> {
    val isLast = isLast(data, size)
    // 마지막 페이지라면 조회한 리스트를 그대로
    val result = if(isLast) data else data.dropLast(1)
    // 마지막 페이지라면 nextId는 null로 세팅
    val nextId = if(isLast) null else getNextId(result)
    val pagination = Pagination(
        nextId = nextId,
        last = isLast
    )
    return result to pagination
}
```
제네릭하게 사용하기 위해서 엔티티의 경우 `Long`인 경우에는 다음과 같은 인터페이스를 하나 만들었다.

```kotlin
package io.basquiat.response.global.type

/**
 * 식별자 관련 인터페이스
 * created by basquiat
 */
interface EntityLongIdentifiable {
    val id: Long?
}

/**
 * member entity
 * created by basquiat
 */
@Entity
@DynamicUpdate
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name: String,

    nickName: String,

    ): EntityLongIdentifiable, BaseTimeOnlyPreUpdateEntity() {

    @Column(name = "nick_name", nullable = false, length = 100, unique = true)
    var nickName: String = nickName
        private set

    fun changeNickName(nickName: String) {
        this.nickName = nickName
    }

}
```
`Member`엔티티는 `EntityLongIdentifiable`를 구현하고 있다.

제네릭 입장에서 객체내에 `id`라는 멤버 변수가 있는지 알 수 없다.

당연히 어떤 멤버 변수가 존재하는지도 알 수가 없다.

해결법은 제네릭에서 해당 인터페이스를 구현한 경우 알 수 있도록 코드를 작성하는 것이다.

~~코드를 짜고 보니 참 번거롭네....~~

`Cursor-based Pagination`의 경우에는 스크롤을 통해 조회할 때마다 `nextId`를 기준으로 `limit`만큼 가져온다.

이런 이유로 쿼리를 날리는 시점에서는 현재 커서 위치가 마지막 페이지인지 다음 페이지 정보가 더 있는지 알 수 없다.

따라서 요청 정보에서 `size`에 1를 더해서 쿼리를 날리고 넘어온 리스트 길이를 통해 다음 페이지 정보가 더 있는지 파악해야 한다.

예를 들어 10개의 데이터를 요청했을 때 11개의 데이터를 디비에 찔러 본다.

만일 11개가 나온다면 다음 페이지가 있다는 것을 알 수 있다.

근데 만일 11개의 데이터를 가져왔는데 11개보다 덜 나온다면 다음 페이지는 데이터가 없다는 것을 알 수 있다.

이 방법을 이용해서 현재 리스트 정보가 마지막 페이지인지 파악이 가능하다.

이런 방식을 적용한 것이 위에 언급한 유틸리티 함수이다.

데이터 생성은 `MemberMutationServiceTest`에서 `createMemberLoopTEST`테스트 코드를 실행해서 20개의 row 데이터를 생성한다.

## 일단 변경해 보자.

`QueryMemberUseCase`와 `QueryAllMembersUseCase`클래스를 한번 보자.

```kotlin
@Service
class QueryAllMembersUseCase(
    private val query: MemberQueryService,
) {

    fun execute(request: QueryMember): ApiResponse<List<MemberDto>> {
        val data = query.findByConditions(request.toQueryCondition())
        // 비어 있다면
        if(data.isEmpty()) {
            return ApiResponse.create(emptyList(), null)
        }
        val size = request.size ?: 10
        val (result, pagination) = make(data, size)
        return ApiResponse.create(result.map(MemberDto::toDto), pagination)
    }

}

@Service
class QueryMemberUseCase(
    private val query: MemberQueryService,
) {

    fun execute(id: Long): ApiResponse<MemberDto> {
        return ApiResponse.create(query.fetchByIdOrThrow(id).let(MemberDto::toDto))
    }

}
```
위 코드와 같이 `ApiResponse`라는 객체로 감싸도록 수정하면 된다.

그리고 이런 방식으로 하나씩 저 형식에 맞춰서 모든 컨트롤러와 유즈케이스에서 `ApiResponse`객체에 담아서 보내도록 수정한다.

```json
{
    "data": {
        "id": 1,
        "name": "funnyjazz_1",
        "nickName": "basquiat_1",
        "createdAt": "2024-08-20T13:24:25",
        "updatedAt": null
    }
}

{
  "data": [
    {
      "id": 20,
      "name": "funnyjazz_20",
      "nickName": "basquiat_20",
      "createdAt": "2024-08-20T13:24:25",
      "updatedAt": null
    },
    {
      "id": 19,
      "name": "funnyjazz_19",
      "nickName": "basquiat_19",
      "createdAt": "2024-08-20T13:24:25",
      "updatedAt": null
    },
    .
    .
    .
  ],
  "pagination": {
    "nextId": 11,
    "last": false
  }
}
```

원하는 형식으로 잘 보여준다.

여기에 입맛에 맞춰서 `path`, `timestamp`정보도 보내주고 싶다면 그에 맞춰 수정하면 끝이다.

물론 `path`의 경우에는 컨트롤러단에서 `HttpServletRequest`를 받아서 처리해야 하기에 손이 가지만 원하는 대로 변경이 가능해 진다.

~~와우 나이스하잖아!~~

기왕 하는 김에 `timestamp`, `path`, `status`도 추가해서 디테일하게 뭔가 그럴싸하게 작업 완료를 한다.

최종 성공 응답 `JSON`포맷
```json
{
    "status": 200,
    "data": {
        "id": 1,
        "name": "funnyjazz_1",
        "nickName": "basquiat_1",
        "createdAt": "2024-08-20T13:24:25",
        "updatedAt": null
    },
    "path": "/members/1",
    "timestamp": "2024-08-21T13:02:50.535441"
}
```

여기까지가 바로 여러분들이 일반적으로 작업하는 방식일 것이다.

## 근데요? 저는 봉투 패턴인지 뭔지 이것도 사용하고 싶지 않은데요?

주니어분이 가만히 보다가 공통 에러 처리 하는 것을 봐버렸기 때문에 응답 객체도 비슷하게 할 수 있지 않을까 하고 질문을 한다.

```
위에서 에러 처리를 공통으로 할 수 있던데? 
컨트롤러단과 유즈케이스에서 일일히 저렇게 ApiResponse에 담는 것이 불합리해 보입니다.
그냥 컨트롤러 단에서 dto나 리스트로 보내면 공통으로 ApiResponse 담아서 응답을 주는 방법은 없는 건가요?
방금 nest.js에서 @Catch()와 ExceptionFilter을 사용해서 처리한 것처럼 비슷하게 할 수 있을거 같은데요.
```

## ResponseBodyAdvice을 사용하라!!

먼저 나의 의견을 말하자면 나는 이렇게 모든 것을 공통적으로 작업하는 것을 그리 좋아하지 않는다.

~~뭐 딱히 싫어하지도 않는다.~~ 
