# moa-seoul-api

모아 서울 API Server

---

## Run

### Requirement

- Java >= 11ga
- docker >= 3

### Setup

```bash
    git clone https://github.com/BuGLifer/moa-seoul-api.git && cd moa-seoul-api
    cp src/main/resources/application-sample.yml src/main/resources/application-local.yml
    vim src/main/resources/application-local.yml  # edit application-local.yml file
    cp docker/sample.env docker/local.env
    vim docker/local.env  # edit local.env file
    sh gradlew bootJar
```

### Run

```bash
    docker-compose --env-file docker/local.env up -d
    nohup java -jar build/lib/*.jar --spring.profiles.active=local &
```
