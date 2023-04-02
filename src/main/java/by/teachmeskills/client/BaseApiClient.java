package by.teachmeskills.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    static final String BASE_URL = "https://reqres.in/";

    public BaseApiClient() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    public Response post(String path, Object body) {
        return getRequestSpecification()
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public Response put(String path, Map<String, ?> parameterNameValuePairs, Object body) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .body(body)
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }

    public Response patch(String path, Map<String, ?> parameterNameValuePairs, Map<String, String> map) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .body(map)
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path) {
        return getRequestSpecification()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response getWithDelay(String path, int delay) {
        return getRequestSpecification()
                .queryParam("delay", delay)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response delete(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }

    protected static RequestSpecification getRequestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
