package restassured;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.gherkin.model.Given;
import com.google.gson.JsonObject;

import baselibrary.Baselibrary;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Test02_POST extends Baselibrary {

	@BeforeClass
	public void setupClass() {
		RestAssured.useRelaxedHTTPSValidation();
		logger.info("Relaxed HTTPS Validation enabled for this class.");
	}

	@Test
	public void test01_post() {
		logger.info("Starting test01_post - POST https://reqres.in/api/users");

		JSONObject req = new JSONObject();
		req.put("name", "shivam");
		req.put("job", "QA");
		logger.info("Request body: " + req.toJSONString());

		given().header("Content-Type", "application/json") // Corrected Content-Type
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString()).when()
				.post("https://reqres.in/api/users").then().statusCode(201).log().all();

		logger.info("Test01_post completed.");
	}

	@Test
	public void test02_put() {
		logger.info("Starting test02_put - PUT https://reqres.in/api/users/2");

		JSONObject req = new JSONObject();
		req.put("name", "shivam");
		req.put("job", "QA");
		logger.info("Request body: " + req.toJSONString());

		given().header("Content-Type", "application/json") // Corrected Content-Type
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString()).when()
				.put("https://reqres.in/api/users/2").then().statusCode(200).log().all();

		logger.info("Test02_put completed.");
	}

	@Test
	public void test03_patch() {
		logger.info("Starting test03_patch - PATCH https://reqres.in/api/users/2");

		JSONObject req = new JSONObject();
		req.put("name", "shivam");
		req.put("job", "QA");
		logger.info("Request body: " + req.toJSONString());

		given().header("Content-Type", "application/json") // Corrected Content-Type
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(req.toJSONString()).when()
				.patch("https://reqres.in/api/users/2").then().statusCode(200).log().all();

		logger.info("Test03_patch completed.");
	}

	@Test
	public void test04_delete() {
		logger.info("Starting test04_delete - DELETE https://reqres.in/api/users/2");

		when().delete("https://reqres.in/api/users/2").then().statusCode(204).log().all();

		logger.info("Test04_delete completed.");
	}
}
