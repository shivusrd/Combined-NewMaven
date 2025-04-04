package restassured;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test04_POST extends Baselibrary {

    @Test
    void Test04() {
        logger.info("Starting Test04 - POST https://reqres.in/api/users/users");

        // Specify base URI
        RestAssured.baseURI = "https://reqres.in/api/users";
        logger.info("Base URI set: " + RestAssured.baseURI);

        // Request object
        RequestSpecification httpRequest = RestAssured.given();
        logger.info("RequestSpecification created.");

        // Request body
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");
        logger.info("Request body: " + requestParams.toJSONString());

        // Set header
        httpRequest.header("Content-Type", "application/json");
        logger.info("Header set: Content-Type=application/json");

        // Response object
        Response resp = httpRequest.request(Method.POST, "/users");
        logger.info("POST request sent to /users");

        // Print response in console window
        String response = resp.getBody().asString();
        logger.info("Response body: " + response);

        // Status code validation
        int statusCode = resp.getStatusCode();
        logger.info("Status code: " + statusCode);
        Assert.assertEquals(statusCode, 201);
        logger.info("Assertion passed: Status code is 201.");

        /*
        // Success validation (commented out in your code)
        String successCode = resp.jsonPath().getString("id");
        Assert.assertEquals(successCode, "959");
        */

        // Status line verification
        String statusLine = resp.getStatusLine();
        logger.info("Status line: " + statusLine);
        Assert.assertEquals(statusLine, "HTTP/1.1 201 Created");
        logger.info("Assertion passed: Status line is HTTP/1.1 201 Created.");

        logger.info("Test04 completed.");
    }

}
