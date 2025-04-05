package restassured;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import baselibrary.Baselibrary;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class Test02_POST extends Baselibrary {

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
    public void test01_post() {
        String testName = "test01_post - POST https://reqres.in/api/users";
        logger.info("Starting " + testName);

        JSONObject req = new JSONObject();
        req.put("name", "shivam");
        req.put("job", "QA");
        logger.info("Request body: " + req.toJSONString());

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201);

        logRequestResponseInReport(testName);

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().all(); // Keep for console logging

        logger.info(testName + " completed.");
    }

    @Test
    public void test02_put() {
        String testName = "test02_put - PUT https://reqres.in/api/users/2";
        logger.info("Starting " + testName);

        JSONObject req = new JSONObject();
        req.put("name", "shivam");
        req.put("job", "QA");
        logger.info("Request body: " + req.toJSONString());

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .statusCode(200);

        logRequestResponseInReport(testName);

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .log().all(); // Keep for console logging

        logger.info(testName + " completed.");
    }

    @Test
    public void test03_patch() {
        String testName = "test03_patch - PATCH https://reqres.in/api/users/2";
        logger.info("Starting " + testName);

        JSONObject req = new JSONObject();
        req.put("name", "shivam");
        req.put("job", "QA");
        logger.info("Request body: " + req.toJSONString());

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .statusCode(200);

        logRequestResponseInReport(testName);

        given().header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString())
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all(); // Keep for console logging

        logger.info(testName + " completed.");
    }

    @Test
    public void test04_delete() {
        String testName = "test04_delete - DELETE https://reqres.in/api/users/2";
        logger.info("Starting " + testName);

        when().delete("https://reqres.in/api/users/2")
        .then()
                .statusCode(204);

        logRequestResponseInReport(testName);

        when().delete("https://reqres.in/api/users/2")
        .then()
                .log().all(); // Keep for console logging

        logger.info(testName + " completed.");
    }
}
