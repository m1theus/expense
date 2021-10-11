#!/bin/bash

docker-compose -p expense-api up -d

chmod +x ./gradlew

./gradlew flywayMigrate -i

SERVER_PORT=8080 ./gradlew bootRun
