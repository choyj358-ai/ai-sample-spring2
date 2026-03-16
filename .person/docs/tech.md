# 기술 스택 (Technical Stack)

## 1. 개요
본 프로젝트는 현대적인 웹 개발 표준과 학습 용이성을 고려하여 Java 21 및 Spring Boot 3.3.4 환경을 기반으로 구축되었습니다.

---

## 2. 개발 환경
- **언어 (Language)**: Java 21 (LTS)
- **프레임워크 (Framework)**: Spring Boot 3.3.4
- **빌드 도구 (Build Tool)**: Gradle 9.3.1
- **버전 관리 (Version Control)**: Git

---

## 3. 백엔드 (Back-end)
- **웹 서버 (Web Server)**: Spring Boot Starter Web (Embedded Tomcat)
- **데이터베이스 (Database)**: H2 Database (In-Memory / File)
- **ORM**: Spring Data JPA
- **보안/인증 (Auth)**: HTTP Session-based Authentication
- **공통 라이브러리**: Lombok

---

## 4. 프론트엔드 (Front-end)
- **템플릿 엔진 (View Engine)**: Mustache
- **스타일링 (Styling)**: CSS (기본 스타일 및 부트스트랩 활용 권장)

---

## 5. 실행 및 빌드 방법
1. **JDK 21**이 설치되어 있어야 합니다.
2. 프로젝트 루트 디렉토리에서 `./gradlew bootRun` 명령어로 실행합니다.
3. H2 콘솔 주소: `http://localhost:8080/h2-console` (설정된 경우)
