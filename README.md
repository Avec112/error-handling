# Requirements 
* Java 13 (please change in pom.xml if need older version of Java)
* Spring Boot 2.2.4 (older may work also)
* Thymeleaf dependency

# Prepare the project
* Recommend you start by cloning this repo
* Delete the following:
  * src/main/java/io/avec/errorhandling/controller/MyErrorController.java
  * src/main/resources/error
  * src/main/resources/error.html

# Tasks
## Step 1: Override default "Whitelabel Error Page"
1. Start the server (defaults to port 8080)
2. Click non existing url http://localhost:8080/non_existing
3. This will display a default "Whitelabel Error Page" (404)
4. Open `application.properties` and add `server.error.whitelabel.enabled=false`
5. Restart server
6. Reload url http://localhost:8080/non_existing
7. Now Spring Boot redirects error handling to Tomcat (default servlet engine).
Also we got a stacktrace in our console
8. Comment out `server.error.whitelabel.enabled=false`
9. Instead create a generic `error.html` in `resources/templates`. Give it any text.
10. Restart server and reload url http://localhost:8080/non_existing
11. Did you notice that the application now picks up error.html?
12. Create another file. This time `404.html` and put it in `resources/templates/error`. 
Give it the text "404 Not Found". 
13. Restart server and reload url http://localhost:8080/non_existing
14. This time the `404.html` is picked up because that is the actual error. "404 Not Found"
15. Click the existing url http://localhost:8080/hello
16. The generic exception is shown again. This is because while `/hello exists`, it throws a RuntimeException.
Also notice that `/hello` throws a stack trace.
17. Create another file. This time `500.html` and put it in `resources/templates/error`.
Give it the text "500 Internal Server Error"
17. Verify that the new page `500.html` is picked up.

We have now seen how you can override the default "Whitelabel Error Page", with 
a generic `error.html` and specific error pages like `400.html` and `500.html`

## Step 2: Custom ErrorController
Now we will try to add a even more configurable error handling mechanism. 
This will be done by implementing the interface `ErrorController`

Create the class `MyErrorController` like this
```
@Controller
public class MyErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // TODO: log error details here

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            } /*else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }*/
        }

        // display generic error
        return "error";
    }
}
```

Class `MyErrorController` will override the default `BasicErrorController` provided by default.
All error handling will from now on be handled by `MyErrorController`.

Further steps could be to provide more information to be injected from the controller into 
the error pages. This is of course possible but outside the scope of this demonstration.
Also what if you have a rest service and something fails will the client get some error.html page in return? (Tip: Take a look into `MyErrorController2.java` and `500.html`)  

Feel free to experiment further. 
