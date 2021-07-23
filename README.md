# Shopping - 쇼핑앱
![image](https://user-images.githubusercontent.com/38930501/126750507-19c68abf-381b-411f-ada4-dcc75b948b40.png)

# 아키텍처

 <img width="266" alt="화면 캡처 2021-07-12 105719" src="https://user-images.githubusercontent.com/38930501/125222021-2aa89e00-e304-11eb-9352-a4144083fd1a.png">

# 사용한 기술 및 라이브러리
* Presentation Layer
  - Activity
  - Fragment
  - ViewModel
  - LiveData

* Repository Layer
  - Remote Data Source(Retrofit2)
  - Local Data Source(Room)

* Common
  - Coroutines
  - Android X
  - Koin (for Light-Weight DI)
  - View-Binding

* Third-Party Library
  - Glide
  - Retrofit2
  - OkHttp3
  - Firebase Auth

# 실제화면

|제품 리스트 & 새로고침|제품 상세 및 주문기능(상)|제품 상세 및 주문기능(하)|
|---|---|---|
|![image](https://user-images.githubusercontent.com/38930501/126747775-9501fdc5-e6ef-429c-9762-db0510fd5865.png)|![image](https://user-images.githubusercontent.com/38930501/126747947-56f27a82-4ce0-4361-90e2-fda08126fda9.png)|![image](https://user-images.githubusercontent.com/38930501/126747995-2bc1954d-88d1-4b84-8617-be82d15b7e01.png)|

|구글 로그인|프로필 정보 및 주문 리스트 불러오기(주문전)|프로필 정보 및 주문 리스트 불러오기(주문후)|
|---|---|---|
|![image](https://user-images.githubusercontent.com/38930501/126748375-70d6c248-66bd-41ce-b120-954756878a7e.png)|![image](https://user-images.githubusercontent.com/38930501/126748822-0556957f-e719-4eef-82df-e93be16bdeff.png)|![image](https://user-images.githubusercontent.com/38930501/126748976-4c71343c-7af6-4f90-9777-f790a0e6ad3c.png)|
