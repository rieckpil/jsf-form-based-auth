@echo off
call mvn clean package
call docker build -t vs/jsf-form-based-auth .
call docker rm -f jsf-form-based-auth
call docker run -d -p 8080:8080 -p 4848:4848 --name jsf-form-based-auth vs/jsf-form-based-auth