package restassured;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Test02_Get extends Baselibrary {

    @BeforeClass
    public void setupClass() {
        RestAssured.useRelaxedHTTPSValidation();
        logger.info("Relaxed HTTPS Validation enabled for this class.");
    }

    @Test
    public void test_01() {
        logger.info("Starting test_01 - GET https://reqres.in/api/users?page=2 with assertions");

        given().
            get("https://reqres.in/api/users?page=2").
        then().
            statusCode(200).
            body("data.id[2]", is(9)).
            body("data.first_name", hasItems("Byron")).
            log().
            all();

        logger.info("Assertions passed: Status code is 200, data.id[2] is 9, and data.first_name contains 'Byron'.");
        logger.info("Test_01 completed.");
    }
}
