<h2>LMS#lms-service APIs</h2>
<br>created date: 18 Dec, 2019, by Hoang N.V

********************************************************************************************************* 
# Technology
	> Spring Web	
	> PostgreSQL
	> Spring AOP
	> Spring Data with 
	> Swagger 2

### Features
	* Provide simple crud 

### Configuration & data
	+ [configuration] use K8s ConfigMap

### Unit Test
	gradlew build -x test to ignore unit tests
	gradlew bootRun  --> run application
	test one or many Class :   
	gradlew test --tests full_package_and_ClassName
	gradlew build test --tests *ClassName --> to test one or some classes  
	Browse APIs via swagger: http://localhost:8080/swagger-ui.html#
