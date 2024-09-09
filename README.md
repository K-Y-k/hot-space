# :rocket: HotSpace_Project

- 개발 인원: 1명

- 개발 기간: 2024.07 ~ 2024.08

- API 문서 : https://documenter.getpostman.com/view/24388769/2sAXjNXAS9


# :page_facing_up: 목차
- <a href="#0"> 어떤 프로젝트인가요?(프로젝트의 목적) </a>
- <a href="#2"> 사용 기술 </a>
   * <a href="#2.1"> FrontEnd </a> 
   * <a href="#2.2"> BackEnd </a>
   * <a href="#2.3"> API 서비스스 </a>
- <a href="#3"> E-R 다이어그램 </a>
- <a href="#4"> 아키텍처 구성 </a>
- <a href="#5"> 시연 영상 </a>
- <a href="#6"> 주요 기능 </a>
- <a href="#7"> 어려웠던 점 및 극복 </a> 
- <a href="#8"> 프로젝트를 통해 느낀점 </a> 
- <a href="#9"> 프로젝트 관련 추가 포스팅 </a> 


## <b id="0"> ❓ 어떤 프로젝트인가요?</b>
- 오픈 API를 활용해 보고 싶었고 학습했던 웹 개발 능력을 키우기 위해 이 프로젝트를 진행하게 되었습니다.

- 인기가 많은 식당, 이용 시설 등 방문하면 테이블/좌석이 없는 경우가 많아 불편함을 겪었습니다.

  직접 방문하기 전에 이용할 수 있는 좌석이 있는지 빠르게 확인할 수 있는 요소가 있다면 

  시간과 거리 이동의 비용을 줄일 수 있을 것 같다는 생각이 들었습니다.


- 그래서 테이블/좌석을 실시간으로 확인할 수 있는 서비스를 생각했고

  식당만이 아닌 테이블/좌석만 있다면 모든 이용시설로 대중화되었을 때

  편리함을 제공할 수 있다고 생각하여 개발해보고 싶었습니다.


## <b id="1"> 🏢 개발 환경 </b>
- ### IDE
  * IntelliJ IDEA Community Edition 2022
  * Visual Studio Code 1.7.8
  * Postman
  * GitHub

## <b id="2"> 🛠 사용 기술 </b>
### <b id="2.1"> FrontEnd </b>
- #### Language / FrameWork / Library
  * Html/Css
  * Javascript
  * Bootstrap 5.2
  * Jquery
  * Thymeleaf
  * Swiper.js

### <b id="2.2"> BackEnd </b>
- #### Language / FrameWork / Library
  * Java 11 openjdk
  * SpringBoot 2.7.7
  * Jpa(Spring Data JPA)

- #### Build tool
  * Gradle 7.6

- #### Emebedded Web Server
  * Apache Tomcat 9.0
  
- #### Database
  * H2

### <b id="2.3"> API 서비스 </b>
- Naver Maps API


## <b id="3"> 🔑 E-R 다이어그램 </b>
<img src="https://github.com/user-attachments/assets/867e40c5-e488-4a36-8829-aadc6a0638b1" width="800" height="500"/>


## <b id="4"> ⚙️ 아키텍처 구성 </b>
<img src="https://github.com/user-attachments/assets/38a932b8-3cc0-4888-83b4-f8d60c1eabcd" width="800" height="650"/>

## <b id="5"> ▶️ 시연 영상 </b>
[![Video Label](http://img.youtube.com/vi/vsiD-NyUB4M/0.jpg)](https://youtu.be/vsiD-NyUB4M)


## <b id="6"> ✔️ 주요 기능 </b>
 - ### 회원가입 / 유효성 검사 / 로그인 / 수정 / 탈퇴
   - 닉네임, 아이디, 비밀번호, 프로필 이미지로 구성했고
   - 각 폼에서는 유효성 검사가 진행됩니다.
    
     <img src="https://github.com/user-attachments/assets/3136b420-6e1b-42d5-9f4e-2eb46eeaf764" width="50%"/>

  - ### 마커/정보창 생성, 현재 위치 조회/이동, 주소 변환, 주소 검색/이동
    - 현재 위치를 마커로 생성했고 마커를 클릭하면 클릭한 좌표를 주소로 변환해서 정보창에 출력하도록 했습니다.
    - 주소를 검색하면 해당 주소를 좌표로 변환하여 이동합니다.

      <img src="https://github.com/user-attachments/assets/6161be0b-0924-413b-b4bf-18775b67a19b" width="50%"/>   
     
 - ### 가게 등록 / 수정 / 삭제 / 리스트
    - 가게 관련된 정보와 네이버 지도의 좌표로 구성하여 등록/수정/삭제를 적용했습니다.
      
     <img src="https://github.com/user-attachments/assets/dbe3126f-4fd8-48f0-8d44-5d7f546922c2" width="50%"/>

 - ### 카테고리 분류, 일정 반경의 가게 조회, 가게 상세 조회
   - 현재 지도에서의 중심 기준으로 일정 반경에 등록된 가게들을 조회하여 마커로 생성했습니다.
   - 이용 시설 종류에 따라 분류하여 조회할 수 있습니다.
   - 마커를 클릭 시 등록한 가게의 세부 정보를 확인할 수 있고 여러 이미지인 경우 이미지 슬라이드를 적용했습니다. 
    
     <img src="https://github.com/user-attachments/assets/440bf030-6a9b-4cf4-9596-00d92ccee95d" width="50%"/>
     
 - ### 테이블 배치, 이용여부 변경
   - 테이블 배치에서 테이블을 드래그하여 배치할 수 있습니다.
   - 테이블 현황 관리에서 테이블을 클릭하여 이용 여부를 변경할 수 있습니다.
    
     <img src="https://github.com/user-attachments/assets/01e3da49-e631-48f8-90bb-0401152be171" width="50%"/>   
     
 - ### 예약 신청, 승인/거절
   - 고객은 가게 상세 조회를 통해 테이블 현황을 확인하여

     빈 테이블을 클릭하여 예약 신청을 할 수 있습니다.
   - 가게는 예약 신청한 내역을 승인 및 거절할 수 있습니다.

     <img src="https://github.com/user-attachments/assets/d0725a46-44a7-4f33-b640-c86f77cf9eeb" width="50%"/>   
 

## <b id="7"> 🔥 어려웠던 점 및 극복 </b>
### 1. 깃 브랜치 전략
- 실무 개발처럼 협업한 경험이 없어서 이러한 경험을 간접적으로 경험해 보기 위해
  
  이번 개인 프로젝트에서 각 도메인의 브랜치를 생성하여 깃 브랜치 전략을 적용해 보았습니다.
- 처음에는 어려웠지만 데스크 PC와 노트북에서 pull, merge 작업을 통해 자주 전환히면서 개발하여 극복했습니다.  

### 2. 네이버 지도 API 활용
- 처음 접한 오픈 API라서 활용하는 방법이 어려웠으나,

  공식 문서를 참고하여 생성부터 하나씩 구현해가며 원리를 이해할 수 있었습니다.

### 3. 테이블 배치
- 드래그를 통한 테이블 배치는 프론트엔드 기술이라서 어려움을 겪었지만,

  필요한 이벤트 동작을 조사 후 적용해나가며 극복했습니다.

## <b id="8"> 💡 프로젝트를 통해 느낀점 </b>
- <b>깃 브랜치 전략</b>에 익숙해짐.
- <b>오픈 API의 활용법</b>을 알 수 있었음.
- 기본적인 <b>엔티티 설계 및 관계 매핑, 리포지토리/서비스/컨트롤러/뷰의 계층과 MVC 구조에 능숙</b>해졌고

  <b>쿼리 파라미터의 전송, 전달에 능숙</b>해짐.
- 기능을 구현할 때 발생할 수 있는 <b>모든 테스트 케이스들을 면밀히 분석해야 함</b>.
- <b>Ajax로 JSON 호출 및 응답</b>에 익숙해짐.
- 구현 중 막히는 상황이 많았지만 그런 상황속에도 <b>포기하지 않고 끝까지 완수하는 과정을 통해 끈기를 기를 수 있었음</b>.


## <b id="9"> 📒 프로젝트 관련 추가 포스팅 </b>
- 🔗 <a href="https://blog.naver.com/kyk7777_/223570003783" target="_blank">프로젝트 명세서</a>
- 🔗 [[네이버 지도 API] - 지도 생성 / 마커 생성 / 정보창 출력 / 선택 좌표 주소변환 / 주소 검색 및 이동](https://blog.naver.com/kyk7777_/223571039126)
- 🔗 [[가게 도메인] - CRUD / 현재 위치에서 일정 반경의 가게들 조회 / 마커를 클릭한 가게 정보 출력 / 이미지 슬라이드 / 가게 관리 리스트](https://blog.naver.com/kyk7777_/223572220190)
- 🔗 [[테이블/좌석 도메인] - 배치 / 현황 / 이용 여부 변경](https://blog.naver.com/kyk7777_/223573152250)
- 🔗 [[예약 도메인] - 예약 모달 / 예약 신청 / 예약 내역 / 승인 및 취소](https://blog.naver.com/kyk7777_/223573793515)
