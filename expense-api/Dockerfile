FROM adoptopenjdk/openjdk11:alpine

ADD ./build/distributions/*.tar /

EXPOSE 80 443

ENTRYPOINT ["/expense-api/bin/expense-api"]
