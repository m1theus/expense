./gradlew flywayMigrate -i \
  "-Pflyway.url=jdbc:postgresql://${DATABASE_HOST}:5432/" \
  "-Pflyway.user=${DATABASE_USER}" \
  "-Pflyway.password=${DATABASE_PASSWORD}" \
  "-Pflyway.schemas=expenseapi"
