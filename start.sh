#!/usr/bin/env bash

mvn clean install -Dskip.integration.tests=true
cd rout
mvn spring-boot:run -Dspring.profiles.active="development"