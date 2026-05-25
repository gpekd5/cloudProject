## 🛠️ Cloud Project

> ### 👨‍🏫 프로젝트 소개

#### Spring Boot 애플리케이션을 AWS에 배포하며 클라우드 기반 백엔드 구조를 학습한 프로젝트입니다.

#### EC2, RDS, S3, Parameter Store를 활용해 운영 환경에서 필요한 인프라 구성과 배포 흐름을 경험했습니다.

---

> ### 📌 주요 개선 사항 (과제 제출 요구사항 포함)

<details>
<summary>LV0. 요금 폭탄 방지 AWS Budget 설정</summary>

- 월별 예산 100달러 설정
- 예산의 80% 초과할 경우 이메일 알림 발생 설정
![AWS Budget 설정 화면](image/lv0_aws_budget.png)
</details>

<details>
<summary>LV1. 네트워크 구축 및 핵심 기능 배포</summary>

- 인프라 구축(VPC & EC2)
  - VPC 설정 후 Public/Private Subnet 분리
  - Public Subnet에 EC2 생성
![img.png](image/lv1_aws_ec2_publicIP.png)


- 애플리케이션 개발 (팀원 정보 저장 및 조회 API)
  - 팀원 이름, 나이, MBTI를 JSON으로 받아 저장하는 API `POST` `/api/members`
  - 저장된 팀원 정보 조회 API `GET` `/api/members/{id}`
  - Profile 분리 (local: H2, prod: MySQL)
  - 로그 전략 (API 요청마다 `INFO`레벨로 로그 기록, 에러 발생 시 `ERROR`레벨)
</details>

<details>
<summary>LV2. DB 분리 및 보안 연결</summary>

- 인프라 구축
  - RDS: Public Subnet에 MySQL 생성
  - 보안 그룹 체이닝: STEP1에서 생성한 EC2 보안 그룹ID만 허용
   ![img.png](image/lv2_security_chaining.png)
  - Parameter Store: DB 접속 정보(url, username, password)와 확인용 파라미터 저장
   ![img.png](image/lv2_actuator.png)

</details>

<details>
<summary>LV3. 프로필 사진 기능 추가와 권한 관리</summary>

- 인프라 구축
  - S3 버킷 생성: "모든 퍼블릭 액세스 차단"
  - IAM Role, IAM Policy 생성
- API 구현
  - 프로필 이미지 S3 버킷 업로드 및 이미지 URL DB 업데이트
  - Presigned URL 반환
  - postman 캡쳐 이미지
  ![img.png](image/lv3_postman.png)
  - s3 저장 이미지 
  ![img_1.png](image/lv3_S3.png)
URL: https://cloudproject-hglfjeklsd-files-331608077829-ap-northeast-2-an.s3.ap-northeast-2.amazonaws.com/uploads/profiles/2/245efb8c-690e-44b7-946e-ff7b0f0baedc-profile_2.png?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEA0aDmFwLW5vcnRoZWFzdC0yIkcwRQIgcgXsptKSmnXoVmS%2Fw36Z0whrtUfEqFh9LixWOfnGLf4CIQCrgtJ3DyXKZrvRDQsZ8V5FyECKN%2BaOsPNRvsobCMhTsyrSBQjX%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F8BEAAaDDMzMTYwODA3NzgyOSIMU7Y418kaiK08JCh9KqYFdRKIYXri5sdwAnD1aAJEvNlWxRnZXLJsLAooI%2BliX2HjP1bEB8x1O0BYVSH4que%2F8f8Oy%2BkbVdtIyg9fJ5aJ2RPyn6ERHKP%2Fxye%2BWtqy%2BkoD2bknNqTX2Rq6%2Fj8c57NDo4Qfih%2Fg%2FM1t8h4qBh6VL%2BLu2lvsjqj7AL24JzI9BZ4FeuihKOref4qm2tDrOQ7xntQhZfKdcju8b48ukhWOY6lcbjcBx2nUrvQSihzvFDPwXsMI608KTGbgiL0dcxJYbtc%2FI0jov3CdeqIRPazC18pcqMqD%2BBCluPT%2F4agOyld5dbcekGiH0BcWQbH65r2DJst39Xp2%2B%2FCzbJ0Pl70zXqHVCg0mYA4llYi6RTpgjN2%2FolXv2Dvi7EXb4A3JnoyNKTV%2B8QEKBYiGIRynb0h0DpWirmwmg4Od6hjestlATBIsS1k74ulH32FsFtfarmrDAZs%2FoJmfTRj3Owk9jW91aVQJol2Xv04OJgTKVsJ8ut3EJBZudKnkAbK6NmZp4RbF3S%2FGw6psm3TH7A%2BK%2Ftf0%2BpAzKWxYMXCmM6MBslDzSPl%2Fh53Jp9lGoTk9cOH047GgBP6YGZAtCrIzoyj1ZHdokXOv4F5cos05ZNF49YJQC6w9dAwN1uwrlt16aV%2BrF0Iuklp3khGmCdmrbnWR%2F12O2pA6pFekXyhUKfwvvKoTsa4qXG7f9drmOnJ1VJ1EcGomkusJDwpP6F8m%2BTqwEKHi8t%2BBC66eB4vnYiuCGekMD3HK8m7RBqaQkzsRpckrCTrEjiTNVFi00TvhDFVuvBzdMYI%2FPkHCy1FWLR32zNy%2F%2BURyUM%2Bc0QRlukl6T%2BLYxCXsJgKuZFzkHTGHNepY2uiq4F89IWvP8rMWkXXyKKVQOl2KoCYIva9YV%2B14JgdSNhyhIilW%2FluDMIHLsdAGOrEBtahb1PSeoWWUz1mCvOmn8CCFmZb1ZC11tmncFdKHCqq9MiE8TEhciFwKnowA6pWQ74iBcnOzDvQKsfxuX1Moo%2FKA%2Bzb6%2FBDY9KFDTjTHb5mNjXdZQc3sNR4u2DpYJIdjzUqHXfNC30OE8C5PZ9asjs1oZXP6E4m%2FbvpFAq0j3PaU%2B5GDtAidmBcyL7cuOB8qJKeBlezBQ3Af9qyHdsZCUTgnX1f%2FI5Lr6ugOpmb8nhZd&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260519T141029Z&X-Amz-SignedHeaders=host&X-Amz-Credential=ASIAU2NLBWYC7LK23ZJY%2F20260519%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=604800&X-Amz-Signature=e805bae63fc20c45aa8a0f1cf60cc5a26ce6c7607f43d0d611f0d82f42c1a62f 
</details>

<details>
<summary>LV4. Docker & CI/CD 파이프라인 구축</summary>

- Docker 도입
  - `Dockerfile`을 작성하여 Spring Boot 애플리케이션을 Docker 이미지로 빌드
  - 운영 DB는 Docker MySQL이 아닌 기존 AWS RDS MySQL 사용

- Github Actions CI/CD 구성
  - `.github/workflows/deploy.yml` 작성
  - PR 생성 시 Build & Test 수행
  - main 브랜치에 merge/push 될 경우 Docker 이미지 빌드 및 Docker Hub Push 수행
  - Docker Hub 이미지: `gpekd5/cloudproject:latest`

- EC2 자동 배포
  - SSH 포트 개방 및 Private Key 저장을 피하기 위해 OIDC + AWS Systems Manager Run Command 방식 사용
  - Github Actions가 OIDC로 AWS 임시 권한을 획득
  - SSM Run Command를 통해 EC2 내부에서 `docker pull`, `docker run` 명령 실행
  - EC2에서는 Docker Hub의 최신 이미지를 Pull 받아 기존 컨테이너를 교체 실행

- 보안 구성
  - Docker Hub Access Token은 Github Secrets로 관리
  - AWS Role ARN, EC2 Instance ID는 Github Actions 설정값으로 관리
  - EC2는 `CloudProjectEC2S3Role`을 통해 Parameter Store, S3, SSM 권한 사용
  - SSH 22번 포트를 Github Actions에 개방하지 않고 배포 수행 (내IP만 허용)

- 과제 제출 캡처
  - Github Actions 성공 화면
  ![img.png](image/lv4_github_actions.png)

  - EC2에서 `sudo docker ps` 실행 결과
  ![img_1.png](image/lv4_ec2_ps.png)

</details>

<details>
<summary>LV5. 고가용성 아키텍처와 보안 도메인 연결</summary>

- Private Subnet 환경 구성
  - EC2와 RDS를 Private Subnet에 새로 구성
  - RDS는 Public Access를 비활성화
  - Private EC2에서만 RDS 3306 포트 접근 가능하도록 보안 그룹 설정

- NAT Gateway 구성
  - Public Subnet에 NAT Gateway 생성
  - Private EC2가 외부 이미지 Pull 및 패키지 설치를 할 수 있도록 Route Table 설정

- ALB 및 HTTPS 구성
  - Public Subnet에 ALB 생성
  - Target Group은 Private EC2의 8080 포트로 연결
  - Health Check Path는 `/actuator/health`로 설정
  - ACM 인증서 `*.hgbrain.click`을 HTTPS 443 리스너에 적용
  - HTTP 80 요청은 HTTPS 443으로 리다이렉트

- Route 53 도메인 연결
  - `api.hgbrain.click` A 레코드를 생성
  - Alias로 ALB에 연결
  - 최종 접속 URL: `https://api.hgbrain.click/actuator/health`

- Auto Scaling Group 구성
  - Launch Template 생성
  - User Data를 통해 Docker 설치 및 컨테이너 실행 자동화
  - ASG를 Private Subnet과 Target Group에 연결
  - Desired 1 / Min 1 / Max 2로 설정

- 과제 제출 캡처
  - HTTPS 도메인 접속 결과
    ![img.png](image/lv5_https_health.png)
  - 주소
    - https://api.hgbrain.click/actuator/health

  - Target Group Registered targets Healthy 화면
    ![img.png](image/lv5_target_group_healthy.png)

</details>

<details>
<summary>LV6. 글로벌 성능 최적화 CloudFront CDN 적용</summary>

- CloudFront 배포 구성
  - S3 버킷을 Origin으로 하는 CloudFront 배포 생성
  - S3 버킷은 Public Access를 차단한 상태로 유지
  - CloudFront만 S3 객체를 조회할 수 있도록 버킷 정책 설정

- 이미지 조회 방식 변경
  - 기존 S3 Presigned URL 반환 방식에서 CloudFront 도메인 기반 URL 반환 방식으로 수정
  - `CLOUDFRONT_DOMAIN` 값을 Parameter Store에 추가
  - 프로필 이미지 조회 API 응답이 CloudFront URL을 반환하도록 변경

- 최종 검증
  - 프로필 이미지 조회 API 호출 시 CloudFront URL 반환 확인
  - 반환된 CloudFront 이미지 URL을 브라우저에서 직접 조회 확인

- 과제 제출 캡처
  - CloudFront 이미지 URL 조회 결과
    ![img.png](image/lv6_postman.png)

  - 주소
    - https://d36ddbxe0gdvp6.cloudfront.net/uploads/profiles/1/a87d996c-8af8-4ff2-9cd3-43883f2925e9-profile_1.png
![img.png](image/lv6_image.png)
</details>




---

> ### ⏲️ 개발기간

- 2026.05.18 ~ 2026.05.25

---

> ### 📚️ 기술스택

| 구분 | 사용 기술 |
|---|---|
| Language | Java 17 |
| Backend | Spring Boot, Spring Web |
| Data Access | Spring Data JPA |
| Database | MySQL |
| Validation | Spring Boot Validation |
| Test | JUnit 5, Mockito |
| Build / Tool | Gradle, Lombok |
| IDE | IntelliJ IDEA |
| Version Control | Git, GitHub |

---

> ### 🔥 Trouble Shooting

<details>
<summary>1. EC2 환경에서 RDS 연결 실패</summary>

#### 문제
- EC2에서 Spring Boot 애플리케이션을 `prod` profile로 실행하는 과정에서 JPA 초기화 오류 발생

#### 원인
- 처음에는 DB URL, Parameter Store 주입, RDS 보안 그룹 문제를 의심했지만,
- 확인 결과 `Parameter Store`에 저장한 `DB_USERNAME 값`과 `실제 RDS 마스터 사용자명`이 일치하지 않아 발생한 문제

#### 해결
- `Parameter Store`의 `DB_USERNAME` 값을 실제 RDS 사용자명 수정한 뒤,
  EC2에서 환경변수를 다시 주입하고 애플리케이션을 재실행

#### 느낀 점
- 단순한 설정 오류도 설정 파일, 환경변수, 네트워크, 인증 정보가 함께 얽혀 있어 원인을 바로 찾기 어렵다는 것을 경험

👉 자세한 정리

https://velog.io/@gpekd5/Cloud-%EA%B3%BC%EC%A0%9C-TroubleShooting-EC2-%EC%A0%91%EC%86%8D-%ED%9B%84-%EC%9A%B4%EC%98%81%ED%99%98%EA%B2%BD-%EC%8B%A4%ED%96%89-%EC%98%A4%EB%A5%98

</details>

<details>
<summary>2. AWS 보안 그룹 설정 오류 수정</summary>

#### 문제
- RDS 접근 설정을 구성하는 과정에서 `EC2 보안 그룹`에 `3306` 규칙을 추가하여 RDS 접근 제어와 혼동

#### 원인
- 과제 요구사항의 “RDS 인바운드에 EC2 보안 그룹 ID만 허용”이라는 내용을 보고,
  EC2 보안 그룹 자체에 `3306` 규칙을 추가하는 것으로 잘못 이해
- EC2 보안 그룹과 RDS 보안 그룹이 각각 다른 리소스의 인바운드 트래픽을 제어한다는 점을 구분하지 못함

#### 해결
- EC2 보안 그룹에서는 `3306` 규칙 제거
- RDS 전용 보안 그룹을 생성하고, RDS 인바운드 `3306` 소스를 EC2 보안 그룹으로 지정
- SSH `22` 포트는 전체 공개가 아닌 내 IP(`/32`)만 허용

#### 느낀 점
- 보안 그룹 설정은 포트만 여는 것이 아니라, 어떤 리소스로 들어오는 요청인지 기준으로 분리해서 이해 필요

👉 자세한 정리

https://velog.io/@gpekd5/Cloud-%EA%B3%BC%EC%A0%9C-TroubleShooting-%EB%B0%A9%ED%99%94%EB%B2%BD-%EA%B3%B5%EC%9C%A0-%EB%AC%B8%EC%A0%9C

</details>

<details>
<summary>3. GitHub Actions CI/CD 및 EC2 자동 배포 구성 문제</summary>

#### 문제
- GitHub Actions를 통해 Docker Hub에 이미지를 Push하고 EC2에 자동 배포하는 과정에서 여러 오류 발생
- 초기 SSH 배포 방식에서는 EC2 보안 그룹의 `22` 포트 개방 범위 문제가 발생
- 이후 SSM Run Command 방식으로 변경하는 과정에서 테스트 환경변수, JAR 파일 누락, SSM 관리형 노드 미등록, Docker 미설치 문제 발생

#### 원인
- GitHub Actions 테스트 환경에 `S3_BUCKET_NAME`, `AWS_REGION` 등 환경변수가 없어 Spring Context 로딩 실패
- GitHub Actions의 Job 간 실행 환경이 분리되어 `ci` Job에서 생성한 JAR 파일이 `build-and-push` Job에 공유되지 않음
- SSH 방식은 GitHub Actions 러너 IP가 고정되어 있지 않아 보안 그룹 설정이 애매함
- EC2 Role에 SSM 권한이 부족하거나 Docker가 설치되어 있지 않아 SSM 배포 명령 실행 실패

#### 해결
- CI 단계에 테스트용 더미 환경변수 추가
- `build-and-push` Job에서 `./gradlew clean bootJar`를 다시 실행하여 JAR 생성
- SSH 배포 방식 대신 `OIDC + AWS Systems Manager Run Command` 방식으로 변경
- EC2 Role에 `AmazonSSMManagedInstanceCore` 정책 추가
- EC2에 Docker 설치 후 SSM Run Command를 통해 `docker pull`, `docker run` 실행

#### 느낀 점
- CI/CD는 단순히 YAML을 작성하는 것이 아니라, 빌드 환경과 실제 배포 환경의 차이를 이해해야 함
- SSH 포트 개방 없이 배포하기 위해 OIDC와 SSM을 활용하면 보안적으로 더 안전한 자동 배포 구성이 가능함

👉 자세한 정리

https://velog.io/@gpekd5/Cloud-%EA%B3%BC%EC%A0%9C-TroubleShooting-GitHub-Actions-CICD-%EA%B5%AC%EC%84%B1-%EB%B0%8F-%EB%B0%A9%EC%8B%9D-%EA%B0%9C%EC%84%A0-%EA%B3%BC%EC%A0%95

</details>

<details>
<summary>4. ASG 적용 후 인스턴스별 배포 버전 불일치 문제</summary>

#### 문제
- LV6에서 프로필 이미지 조회 API를 CloudFront URL 반환 방식으로 수정
- Postman으로 같은 API를 여러 번 호출했을 때 응답 결과가 번갈아 출력됨
- 한 번은 CloudFront URL, 다른 한 번은 기존 S3 Presigned URL 반환

#### 원인
- ALB Target Group에 EC2 인스턴스 2대가 등록된 상태
- 기존 CI/CD 배포 방식은 특정 EC2 인스턴스 1대에만 Docker 컨테이너 재배포
- 한 인스턴스는 최신 버전, 다른 인스턴스는 구버전 컨테이너가 실행 중인 상태
- ALB가 요청을 분산하면서 인스턴스별로 다른 응답 반환

#### 해결
- 각 EC2 인스턴스에 접속하여 컨테이너 상태와 API 응답 직접 확인
- `curl localhost:8080/api/members/{id}/profile-image`로 인스턴스별 응답 비교
- 구버전 컨테이너가 실행 중인 인스턴스도 최신 Docker 이미지로 재실행
- 이후 모든 요청에서 CloudFront URL 반환 확인

#### 느낀 점
- ASG 환경에서는 특정 EC2 1대만 배포하면 신버전과 구버전이 섞일 수 있음
- ALB + Target Group 구조에서는 모든 인스턴스의 배포 버전 일치 여부 확인이 중요함
- 추후에는 ASG Instance Refresh 또는 태그 기반 SSM Run Command로 전체 인스턴스 배포 방식 개선 필요

👉 자세한 정리

https://velog.io/@gpekd5/Cloud-%EA%B3%BC%EC%A0%9C-TroubleShooting-ASG-%EC%A0%81%EC%9A%A9-%ED%9B%84-%EC%9D%B8%EC%8A%A4%ED%84%B4%EC%8A%A4%EB%B3%84-%EB%B0%B0%ED%8F%AC-%EB%B2%84%EC%A0%84-%EB%B6%88%EC%9D%BC%EC%B9%98-%EB%AC%B8%EC%A0%9C

</details>

<details>
<summary>5. API 로그 처리 위치 개선</summary>

#### 문제
- 기존에는 Controller에서 요청 로그를 남기고, Service에서 일부 완료 로그를 남기는 구조
- `@RequestBody` 파싱 실패나 `@Valid` 검증 실패처럼 Controller 진입 전 발생하는 예외의 경우 요청 로그가 남지 않을 수 있음
- Service의 완료 로그는 API 응답 완료 시점이 아니라 특정 비즈니스 로직 종료 시점에 가까움

#### 원인
- 요청/응답 흐름 로그와 비즈니스 이벤트 로그의 역할이 분리되지 않음
- Controller, Service에 로그가 분산되어 API 전체 흐름 파악이 어려움

#### 해결
- `OncePerRequestFilter` 기반 `ApiLoggingFilter` 추가
- API 요청 Method, URI, 응답 Status, 처리 시간을 공통 로그로 기록
- Actuator Health Check 요청은 로그 대상에서 제외
- Controller의 단순 요청 로그 제거
- Service에는 저장, 수정, 삭제, 파일 처리 등 의미 있는 비즈니스 이벤트 로그만 유지

#### 느낀 점
- API 요청/응답 전체 흐름은 AOP보다 Filter에서 처리하는 것이 더 적절하다고 판단
- 예외 로그는 `GlobalExceptionHandler`, 비즈니스 이벤트 로그는 Service로 역할 분리 필요
- 추후 로그량 증가 시 동기/비동기 로그 방식과 Logback AsyncAppender 적용 검토 예정

</details>
---

> ### 📘 개념 학습

#### 1.

---

> ### ✅ 회고

