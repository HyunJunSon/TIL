## SQL 중심적인 개발의 문제점
    - 지금시대는 객체는 관계형 db에 보관
    - 객체에 필드 하나만 추가해도 관련된 쿼리를 다 고쳐야 한다.
![img_1.png](img_1.png)

    - 개발자 == sql 매퍼??
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)

    - 진정한 의미의 계층 분할이 어렵다.
    - 객체답게 모델링 할수록 매핑 작업만 늘어난다.
    - 객체를 자바 컬렉션에 저장 하듯이 db에 저장할 수는 없을까?

## JPA 소개
![img_5.png](img_5.png)
![img_6.png](img_6.png)
![img_7.png](img_7.png)
![img_8.png](img_8.png)
![img_9.png](img_9.png)

    - JPA는 오픈소스에서 시작한 ORM(자바표준)

![img_10.png](img_10.png)
![img_11.png](img_11.png)
![img_12.png](img_12.png)
![img_13.png](img_13.png)
![img_14.png](img_14.png)
![img_15.png](img_15.png)
![img_16.png](img_16.png)
![img_17.png](img_17.png)
![img_19.png](img_19.png)
![img_18.png](img_18.png)

    - 같은 트랜젝션 안에서만 가능함
![img_20.png](img_20.png)
