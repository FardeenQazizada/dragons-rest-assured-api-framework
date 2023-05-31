package tek.api.sqa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class SecurityPractice extends APITestConfig {

	@Test
	public void testGenerateTokenValidUser() {

		Map<String, String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password", "tek_supervisor");
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		String generatedToken = response.jsonPath().get("token");
		Assert.assertNotNull(generatedToken);
	}
	
	@Test
	public void tokenWithInvalidTest() {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("username", "something");
		requestBody.put("password", "tek_supervisor");
		RequestSpecification request = RestAssured.given().body(requestBody);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 400);
		String errorMessage = response.jsonPath().get("errorMessage");
		Assert.assertEquals(errorMessage, "User not found");
	}
	

}
