## HTTP 헤더 개요

![img_35.png](img_35.png)
![img_36.png](img_36.png)
![img_37.png](img_37.png)
![img_38.png](img_38.png)

- 표현(represent) 라는 표현을 쓰는 이유는?
: 실제 db 리소스를 html/json 으로 표현하는 것이라서

![img_39.png](img_39.png)

## 표현

![img_40.png](img_40.png)
![img_41.png](img_41.png)
![img_42.png](img_42.png)
![img_43.png](img_43.png)
![img_44.png](img_44.png)

- Transfer-Encoding(전송 코딩)을 사용하면 content-length 를 사용하면 안됨
- identity --> 압축 안함

# 협상 헤더
![img_45.png](img_45.png)
![img_46.png](img_46.png)
![img_47.png](img_47.png)
![img_48.png](img_48.png)
![img_49.png](img_49.png)
![img_50.png](img_50.png)
![img_51.png](img_51.png)
![img_52.png](img_52.png)
![img_53.png](img_53.png)
![img_54.png](img_54.png)

# 전송방식

![img_55.png](img_55.png)
![img_56.png](img_56.png)
![img_57.png](img_57.png)
![img_58.png](img_58.png)
- 분할전송시에는 cotent-length를 넣으면 안된다.

![img_59.png](img_59.png)

# 일반정보

![img_60.png](img_60.png)
![img_61.png](img_61.png)
![img_62.png](img_62.png)
![img_63.png](img_63.png)
- origin server : 중간에 있는 많은 proxy server 말고 궁극적으로 도달하는 서버

## 특별한정보
![img_64.png](img_64.png)
![img_65.png](img_65.png)
- 호스트 헤더는 필수 기재 사항임

![img_66.png](img_66.png)
![img_67.png](img_67.png)
- 많이 구현 하지는 않는 사항

![img_68.png](img_68.png)
![img_69.png](img_69.png)
![img_70.png](img_70.png)
![img_71.png](img_71.png)

## 쿠키

![img_72.png](img_72.png)
![img_73.png](img_73.png)
![img_74.png](img_74.png)
![img_75.png](img_75.png)
![img_76.png](img_76.png)

### 대안1
![img_77.png](img_77.png)
![img_78.png](img_78.png)
![img_79.png](img_79.png)
![img_80.png](img_80.png)
![img_81.png](img_81.png)
![img_82.png](img_82.png)
![img_83.png](img_83.png)
![img_84.png](img_84.png)
![img_85.png](img_85.png)
![img_86.png](img_86.png)
![img_87.png](img_87.png)
![img_88.png](img_88.png)

- SameSite
: 현재요청하는 도메인과 쿠키에 설정된 도메인이 같은 경우에만 쿠키를 전송할 수 있음




