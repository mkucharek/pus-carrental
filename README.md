# Steps

1. Import the project to IDEA
  * make sure to choose "Maven" as the project type
2. Build (includes integration testing)
  * `mvn clean verify`
3. Run it on Tomcat
  * `mvn tomcat7:run`
4. Use `Advanced REST Client` (or any other, like `Postman`) to play around
  * Try getting all the cars and then just a single one
  * Try getting the car(s) as both `application/json` and `application/xml`
  * Try adding a car by PUTting either json or xml representation

# Slides

*coming soon*
