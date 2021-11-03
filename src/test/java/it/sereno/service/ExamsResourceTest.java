package it.sereno.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ExamsResourceTest {

	@Test
	public void testreadMethod() {
		given().queryParam("code", "AAA-000").when().get("/exams/read").then().statusCode(200).body("code",
				is("AAA-000"));
	}

}