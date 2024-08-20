## yoonnsshop
yoonnsshop은 Java와 Spring 기반의 이커머스 플랫폼 프로젝트로, 실제 개발 과정에서 마주칠 수 있는 다양한 기술적 도전과 해결 과정을 탐구합니다.

이 프로젝트는 다음과 같은 핵심 영역에 중점을 두고 진행하고 있습니다:

1. Java, Spring 기반 어플리케이션 개발 및 JPA의 심층적 이해
2. 웹 퍼포먼스 튜닝 기법 탐구
3. 대규모 트래픽 처리를 위한 시스템 설계
4. Monolithic 아키텍처에서 MSA(Microservices Architecture)로의 전환 과정

## Setup
1. 코드 다운로드 

프로젝트를 로컬 환경에 설정하기 위해 저장소를 내려받습니다.
```bash
git clone https://github.com/magic-library/yoonnsshop.git
cd yoonnsshop
```

2. 환경 변수 설정
프로젝트 루트 디렉토리에 있는 .env.sample 파일을 복사하여 .env 파일을 생성합니다:
```bash
cp .env.sample .env
```
.env 파일을 열고 필요한 환경 변수 값을 입력합니다. 각 변수에 대한 설명은 .env.sample 파일에서 확인할 수 있습니다.

## Build
프로젝트를 빌드하려면 다음 명령어를 실행하세요. 이 명령어는 Gradle을 사용하여 프로젝트를 클린 빌드합니다.
```bash
make build
```

## Start
프로젝트를 시작하는 방법은 Gradle을 사용하는 방법, Docker를 사용하는 방법 두 가지가 있습니다.
2. Docker를 사용한 방법
```bash
make docker-run
```

전체 과정(빌드부터 실행까지)을 한 번에 수행하려면:
```bash
make all
```

## Test
```bash
make test 
```

## Packaging
프로젝트 패키징은 빌드 과정에 포함되어 있습니다. JAR 파일은 build/libs/ 디렉토리에 생성됩니다.
Docker 이미지를 별도로 빌드하려면:
```bash
make docker-build
```

## License
MIT
