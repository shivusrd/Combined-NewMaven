package restassured;

import com.aventstack.extentreports.Status;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;


public class Test01_GET extends Baselibrary {

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
        String requestLogs = requestLogOutputStream.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        String responseLogs = responseLogOutputStream.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        test.log(Status.INFO, "<details><summary><b>Request</b></summary><pre>" + requestLogs + "</pre></details>");
        test.log(Status.INFO, "<details><summary><b>Response</b></summary><pre>" + responseLogs + "</pre></details>");

        // Clear the log streams for the next test
        requestLogOutputStream.reset();
        responseLogOutputStream.reset();
    }

    @Test
    void test01() {
        String testName = "test01 - GET https://reqres.in/api/users?page=2";
        logger.info("Starting " + testName);

        RestAssured.get("https://reqres.in/api/users?page=2");

        logRequestResponseInReport(testName);

        Response resp = RestAssured.get("https://reqres.in/api/users?page=2");
        int statusCode = resp.getStatusCode();
        Assert.assertEquals(statusCode, HttpURLConnection.HTTP_OK);
        logger.info("Assertion passed: Status code is 200.");
        logger.info(testName + " completed.");
    }

    @Test
    void test02() {
        String testName = "test02 - GET https://reqres.in/api/users?page=2 (with logging)";
        logger.info("Starting " + testName);

        RestAssured.get("https://reqres.in/api/users?page=2").then().statusCode(200);

        logRequestResponseInReport(testName);

        logger.info(testName + " completed.");
    }

    @Test
    void test03() {
        String testName = "test03 - GET https://httpbin.org/ (with logging)";
        logger.info("Starting " + testName);

        RestAssured.get("https://httpbin.org/").then().statusCode(200);

        logRequestResponseInReport(testName);

        logger.info(testName + " completed.");
    }

}
