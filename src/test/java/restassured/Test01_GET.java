package restassured;

import java.net.HttpURLConnection;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class Test01_GET extends Baselibrary {

    @BeforeClass
    public void setupClass() {
        RestAssured.useRelaxedHTTPSValidation();
        logger.info("Relaxed HTTPS Validation enabled for this class.");
    }

    @Test
    void test01() {
        logger.info("Starting test01 - GET https://reqres.in/api/users?page=2");
        Response resp = RestAssured.get("https://reqres.in/api/users?page=2");
        logger.info("Response Body: " + resp.getBody().asString());
        logger.info("Response String: " + resp.asString());
        logger.info("Status Code: " + resp.getStatusCode());
        logger.info("Status Line: " + resp.getStatusLine());
        logger.info("Content Type: " + resp.getHeader("content type"));
        logger.info("Response Time: " + resp.getTime());

        int statusCode = resp.getStatusCode();
        Assert.assertEquals(statusCode, HttpURLConnection.HTTP_OK);
        logger.info("Assertion passed: Status code is 200.");
        logger.info("Test01 completed.");
    }

    @Test
    void test02() {
        logger.info("Starting test02 - GET https://reqres.in/api/users?page=2 (with logging)");
        RestAssured.given().get("https://reqres.in/api/users?page=2").then().statusCode(200).log().all();
        logger.info("Test02 completed.");
    }

    @Test
    void test03() {
        logger.info("Starting test03 - GET https://httpbin.org/ (with logging)");
        RestAssured.given().get("https://httpbin.org/").then().statusCode(200).log().all();
        logger.info("Test03 completed.");
    }

}
