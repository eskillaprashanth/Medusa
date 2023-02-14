package com.medusa.apis;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.authapi.payloads.LoginPayload;
import org.authapi.payloads.LoginWithEmailAsIntegerPayload;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.api.endpoints.EndPoints.AUTH_ENDPOINT;

import org.junit.jupiter.api.Assertions;
public class AuthAPIs {
public String login_request_file_path;
String login_with_email_as_integer;
    public String baseURI;
    public static Response response;
    @BeforeMethod
    public void setup() throws IOException {
        try (InputStream input = new FileInputStream("TestData/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            login_request_file_path = prop.getProperty("login_request_file_path");
            login_with_email_as_integer=prop.getProperty("login_with_email_as_integer");
            baseURI = prop.getProperty("base_url");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Test(priority=0)
    public void customerLoginTest() throws IOException {
        ObjectMapper object=new ObjectMapper();
        LoginPayload payload= object.readValue
                (new File(login_request_file_path), LoginPayload.class);
        RequestSpecification request = given();
        RestAssured.baseURI = baseURI;
        System.out.println("request body is "+ payload.toString());
        response = request.body(payload).contentType("application/json").post(AUTH_ENDPOINT);
        System.out.println("response body is  " + response.asString());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200,
                "Correct status code returned");
        Assertions.assertEquals("prashantheskilla1@gmail.com", response.jsonPath().getString("customer.email"));
        Assertions.assertEquals("prashanth", response.jsonPath().getString("customer.first_name"));
        Assertions.assertEquals("eskilla", response.jsonPath().getString("customer.last_name"));

    }
    @Test(priority=1)
    public void loginWithEmailAsInteger() throws IOException {
        ObjectMapper object=new ObjectMapper();
        LoginWithEmailAsIntegerPayload payload= object.readValue
                (new File(login_with_email_as_integer), LoginWithEmailAsIntegerPayload.class);
        RequestSpecification request = given();
        RestAssured.baseURI = baseURI;
        System.out.println("request body is "+ payload.toString());
        response = request.body(payload).contentType("application/json").post(AUTH_ENDPOINT);
        System.out.println("response body is  " + response.asString());
        Assertions.assertEquals("email must be an email", response.jsonPath().getString("message"));
    }
    @Test(priority=2)
    public void checkIfEmailExistTest(){
        RequestSpecification httpRequest = given();
        RestAssured.baseURI = baseURI;
        String final_url = baseURI+AUTH_ENDPOINT+"/prashantheskilla1@gmail.com";
        Response response = httpRequest
                .contentType("application/json")
                .request(Method.GET, final_url);
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is:" + responseBody);
        Assertions.assertEquals("true", response.jsonPath().getString("exists"));
    }
    @Test(priority=3)
    public void customerLogoutTest(){
        RequestSpecification httpRequest = given();
        RestAssured.baseURI = baseURI;
        String final_url = baseURI+AUTH_ENDPOINT;
        Response response = httpRequest
                .contentType("application/json")
                .request(Method.DELETE, final_url);
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is:" + responseBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200,
                "Correct status code returned");
    }
}
