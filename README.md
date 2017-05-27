# How to get it running

1. Build (includes integration testing)
    * `mvn clean verify`
2. Run it on Tomcat
    * `mvn tomcat7:run`
3. Use `Advanced REST Client` (or any other REST tool, like `Postman`) to play around
    * Try getting all the cars and then just a single one
    * Try getting the car(s) as both `application/json` and `application/xml`
    * Try adding a car by PUTting either json or xml representation

# Security
By default, the app is starting with digest authentication method. If you wish to change it, take a look at the tomcat configuration in pom.xml file.
