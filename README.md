API 요구 사항
* 		회원 가입
* 		로그인
* 		내 정보 요청
* 		토큰 생성
* 		헤더에 Authorization Bearer 토큰를 넣어서 요청한다.

필수 사항
* 		로컬, 개발, 운영 모드 별로 환경 변수 관리
* 		JWT 토큰을 사용한 인증 처리
* 		로그인 후 JWT 토큰 발급
* 		서비스 레이어별 DTO 처리
* 		E2E 테스트 케이스

1차리뷰
지금은 복붙이더라도, 뜻을 알고 복붙해야 함. 한줄한줄 예사롭게 보지 말 것
Rest 라서 html을 주진 않음 : resource 폴더를 전체적으로 쓰지 않음
환경변수 분리 : application-<activate>로 파일째 분리함
jpa.hibernate.ddl-auto 의 속성을 봐야 함. create-drop은 db를 싹 지웠다 새로 만드는 문제가 있음
Pre build 를 gradle에 설정하는게 있음
Annotation을 쓸 지 말지 일관적으로 하는게 좋음
@Data의 경우 이것저것 많이 들어있어서, 필요한것만 따로 지정해두는게 좋음
반환형은 제네릭을 활용해서 반드시 분리할 것.
@autowired 말고 생성자 주입을 쓸 것
Private public, final 여부 다 열거할 것
config와 controller를 구별 할 것
entitiy에서 fetch타입을 알 것.
(Mapper를 쓰진 않지만) ArrayList같은 구현체 말고 list같은 인터페이스로 반환할 것
sql을 연동할때 schema.sql을 통해 스키마를 획득하고, data.sql을 통해 초기값을 지정하는 방법이 있음
테스트 라이브러리를 통해 Assertions.assertEquals 라는 gtest같은게 있음.
test시 sql서버가 돌아가고 있어야하는게 아닌 다른 방법으로 할 것. mock이나 spy…
