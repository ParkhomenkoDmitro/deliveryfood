# Delivery food backend
RESTful server based on spring-boot and hibernate frameworks.

## Build & run
1. Build
```
cd path_to_project_root_dir/
mvn clean install
```
 2. Run
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
