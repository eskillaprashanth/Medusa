package com.medusa.apis;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.api.endpoints.EndPoints.COLLECTION_ENDPOINT;
public class CollectionAPIs {
String baseURI;
    @BeforeMethod
    public void setup() throws IOException {
        try (InputStream input = new FileInputStream("TestData/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            baseURI = prop.getProperty("base_url");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    public void getListOfCollections(){
        RequestSpecification httpRequest = given();
        RestAssured.baseURI = baseURI;
        String final_url = baseURI+COLLECTION_ENDPOINT;
        Response response = httpRequest
                .request(Method.GET, final_url);
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is:" + responseBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200,
                "Correct status code returned");
    }
    }

