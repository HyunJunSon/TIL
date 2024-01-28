## 로그

![img.png](img.png)
![img_1.png](img_1.png)
        
        - 개발서버는 debug 출력 
        - 운영서버는 info를 많이 출력한다.

![img_2.png](img_2.png)

### 올바른 로그 사용법
![img_3.png](img_3.png)

    - +연산을 하면 쓰지도 않는 데이터를 더하기로 연산을 해서 컴퓨팅 자원을 낭비하게 된다.
    - 따라서 의미없는 연산을 피하기 위해서 더하기 연산보다는 {} 에 값을 넣는 형태를 사용하는게 베스트 프랙티스이다.
![img_4.png](img_4.png)


## 요청매핑
![img_6.png](img_6.png)
![img_5.png](img_5.png)

- consume 은 요청헤더의 content-type 기반 으로 맵핑
- produces 는 요청헤더의 accept 기반 으로 맵핑

## 요청매핑 api 예시
![img_7.png](img_7.png)
![img_8.png](img_8.png)

![img_9.png](img_9.png)

    - required = false 로 하고 받는것을 int 타입으로 하면 에러남. 왜냐하면 null이 들어갈수 없기 때문
    - 이때는 Integer로 받아야 한다.

    - null 과 ""은 다르다. String 값을 빈값으로 요청을 보낼경우 빈 문자열이 들어가 있다. 따라서 통과가 된다. (조심해야 한다.)
![img_10.png](img_10.png)
![img_11.png](img_11.png)

    - 요청에 빈공백이 들어와서 default값으로 처리해준다.

## @RequestBody는 생략이 불가능하다 대신 @ModelAttribute가 붙는다.
![img_12.png](img_12.png)


## 정적리소스 제공 경로
![img_13.png](img_13.png)

## 스프링부트 기본 메세지 컨버터
![img_14.png](img_14.png)
![img_15.png](img_15.png)

## 요청핸들러 어댑터 구조
![img_16.png](img_16.png)
![img_17.png](img_17.png)
![img_18.png](img_18.png)
![img_19.png](img_19.png)

## 확장
![img_20.png](img_20.png)