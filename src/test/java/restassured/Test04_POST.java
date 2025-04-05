package restassured;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import baselibrary.Baselibrary;
import io.restassured.http.Method;

public class Test04_POST extends Baselibrary {

    private ByteArrayOutputStream requestLogOutputStream;
    private PrintStream requestLogPrintStream;
    private ByteArrayOutputStream responseLogOutputStream;
    private PrintStream responseLogPrintStream;

    @BeforeClass
    public void setupClass() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(true));

        requestLogOutputStream = new ByteArrayOutputStream();
        requestLogPrintStream = new PrintStream(requestLogOutputStream);
        RestAssured.filters(new RequestLoggingFilter(requestLogPrintStream));

        responseLogOutputStream = new ByteArrayOutputStream();
        responseLogPrintStream = new PrintStream(responseLogOutputStream);
        RestAssured.filters(new ResponseLoggingFilter(responseLogPrintStream));

        logger.info("Relaxed HTTPS Validation and Request/Response Logging enabled for this class.");
    }

    private void logRequestResponseInReport(String testName) {
        test.log(Status.INFO, "<details><summary><b>Request</b></summary><pre>" + requestLogOutputStream.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</pre></details>");
        test.log(Status.INFO, "<details><summary><b>Response</b></summary><pre>" + responseLogOutputStream.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</pre></details>");
        // Clear the log streams for the next test
        requestLogOutputStream.reset();
        responseLogOutputStream.reset();
    }

    @Test
    void Test04() {
        String testName = "Test04 - POST https://reqres.in/api/users/users";
        logger.info("Starting " + testName);

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

        // Set request body
        httpRequest.body(requestParams.toJSONString());

        // Response object
        Response resp = httpRequest.request(Method.POST, "/users");
        logger.info("POST request sent to /users");

        logRequestResponseInReport(testName);

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

        // Optional: Log request and response again to console with all details
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
        .when()
                .post("/users")
        .then()
                .log().all();
    }
}


