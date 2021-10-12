# expense [![CI](https://github.com/m1theus/expense/actions/workflows/ci.yml/badge.svg)](https://github.com/m1theus/expense/actions/workflows/ci.yml) [![Deploy expense-bff to AWS](https://github.com/m1theus/expense/actions/workflows/expense-bff-aws.yml/badge.svg)](https://github.com/m1theus/expense/actions/workflows/expense-bff-aws.yml) [![Deploy expense-api to AWS](https://github.com/m1theus/expense/actions/workflows/expense-api-aws.yml/badge.svg)](https://github.com/m1theus/expense/actions/workflows/expense-api-aws.yml)


Api responsável por gerenciar gastos/despesas.

---

### expense-api - [Swagger](http://expense-api.us-east-1.elasticbeanstalk.com/swagger-ui/index.html)

Dependências

- Java 11
- Gradle 7+
- [Spring](https://spring.io/)
  - Spring Boot WebFlux
  - Spring Data Jpa
  - Spring Data R2DBC
  - Spring Validation
- [Swagger](https://springfox.github.io/springfox/docs/current/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://docs.docker.com/engine/)

---

### Executando o projeto localmente

Basta executar o shell `start.sh`

```bash
./start.sh
```

ou

```bash
// docker
docker-compose -p expense-api up -d

// migrations
./gradlew flywayMigrate -i

// app
./gradlew bootRun
```

---
### expense-bff - [API](http://expense-bff.us-east-1.elasticbeanstalk.com/)

Dependências
- [NodeJS](https://nodejs.org/en/)
- [Express](https://expressjs.com/pt-br/)
- [node-fetch](https://github.com/node-fetch/node-fetch)
- [cors](https://github.com/expressjs/cors)
- [dotenv](https://github.com/motdotla/dotenv)

Executando o projeto localmente

```bash
// instalando dependências
yarn install ou npm install

// app
yarn start ou npm run start
```

---
### expense-ui - [API](https://expense-ui.vercel.app/)

Dependências
- [ReactJS](https://pt-br.reactjs.org/)
- [Create React App](https://create-react-app.dev/)
- [MUI](https://mui.com/)
- [axios](https://github.com/axios/axios)
- [date-fns](https://date-fns.org/)

Executando o projeto localmente

```bash
// instalando dependências
yarn install ou npm install

// app
yarn start ou npm run start
```
