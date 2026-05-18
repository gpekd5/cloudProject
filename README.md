## 🛠️ Cloud Project

> ### 👨‍🏫 프로젝트 소개

#### Spring Boot 애플리케이션을 AWS에 배포하며 클라우드 기반 백엔드 구조를 학습한 프로젝트입니다.

#### EC2, RDS, S3, Parameter Store를 활용해 운영 환경에서 필요한 인프라 구성과 배포 흐름을 경험했습니다.

---

> ### 📌 주요 개선 사항

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

#### 1. 

#### 문제

#### 원인

#### 해결

#### 느낀 점


👉 자세한 정리

---

> ### 📘 개념 학습

#### 1.

---

> ### ✅ 회고

