#!/bin/sh
mvn clean package && docker build -t vs/jsf-form-based-auth .
docker rm -f jsf-form-based-auth || true && docker run -d -p 8080:8080 -p 4848:4848 --name jsf-form-based-auth vs/jsf-form-based-auth