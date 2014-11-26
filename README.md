# Steps

1. Import the project to IDEA
  * make sure to choose "Maven" as the project type
2. Run it on Tomcat
  * make sure you added your exploded war file in the `Deployment` tab
3. Use `Advanced REST Client` (or any other, like `Postman`) to play around
  * Try getting all the cars and then just a single one
  * Try getting the car(s) as both `application/json` and `application/xml`
  * Try adding a car by PUTting either json or xml representation

# Homework

Create a simple RESTful webservice – the book library

* Use 4 standard HTTP methods (`@POST`, `@GET`, `@PUT`, `@DELETE`) to mimic CRUD (Create, Read, Update, Delete) mechanism
* Allow reading in XML, JSON **and** plain text format
* Book entry should have the following fields:
  * Id
  * Title
  * Author

# Slides

*coming soon*
