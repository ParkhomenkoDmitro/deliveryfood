# Delivery food backend
RESTful server based on spring-boot and hibernate frameworks.

## Build & run
* Build with tests (unit & IT)
```
cd path_to_project_root_dir/
mvn clean install
```
Note: build without IT tests:
```
mvn clean install -Dskip.integration.tests=true
```
* Run
```
cd rout/
```
###### Development profile:
```
mvn spring-boot:run -Dspring.profiles.active="development"
```
###### Production profile:
```
mvn spring-boot:run -Dspring.profiles.active="production"
```
