package restassured;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import baselibrary.Baselibrary;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class Test02_Get extends Baselibrary {

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
    public void test_01() {
        String testName = "test_01 - GET https://reqres.in/api/users?page=2 with assertions";
        logger.info("Starting " + testName);

        given().
                get("https://reqres.in/api/users?page=2").
        then().
                statusCode(200).
                body("data.id[2]", is(9)).
                body("data.first_name", hasItems("Byron"));

        logRequestResponseInReport(testName); // Log request and response

        given(). // Re-execute to log all details to console (optional, for debugging)
                get("https://reqres.in/api/users?page=2").
        then().
                log().
                all();

        logger.info("Assertions passed: Status code is 200, data.id[2] is 9, and data.first_name contains 'Byron'.");
        logger.info("Test_01 completed.");
    }
}