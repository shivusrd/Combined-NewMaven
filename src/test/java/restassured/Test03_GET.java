package restassured;

import org.testng.Assert;
import org.testng.annotations.Test;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class Test03_GET extends Baselibrary {

    @Test
    void Test03() {
        logger.info("Starting Test03 - GET https://reqres.in/api/users/2");

        // Specify base URI
        RestAssured.baseURI = "https://reqres.in/api/users";
        logger.info("Base URI set: " + RestAssured.baseURI);

        // Request object
        RequestSpecification httpRequest = RestAssured.given();
        logger.info("RequestSpecification created.");

        // Response object
        Response resp = httpRequest.request(Method.GET, "/2");
        logger.info("GET request sent to /2");

        // Print response in console window
        String response = resp.getBody().asString();
        logger.info("Response body: " + response);

        // Status code validation
        int statusCode = resp.getStatusCode();
        logger.info("Status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        logger.info("Assertion passed: Status code is 200.");

        // Status line verification
        String statusLine = resp.getStatusLine();
        logger.info("Status line: " + statusLine);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
        logger.info("Assertion passed: Status line is HTTP/1.1 200 OK.");

        // Validating headers
        String contentType = resp.header("Content-Type");
        logger.info("Content-Type header: " + contentType);

        logger.info("Test03 completed.");
    }
	
}
