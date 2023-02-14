package com.medusa.apis;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.customer.payloads.CreatingCustPayload;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.api.endpoints.EndPoints.CUSTOMER_ENDPOINT;

public class CustomersAPIs {

public String creatingCust_request_file_path;
    public String baseURI;
    public static Response response;
    @BeforeMethod
    public void setup() throws IOException {
        try (InputStream input = new FileInputStream("TestData/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            creatingCust_request_file_path = prop.getProperty("creatingCust_request_file_path");
            baseURI = prop.getProperty("base_url");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

}
    @Test(priority=0)
    public void creatingCustomerTest() throws IOException {
        ObjectMapper object=new ObjectMapper();
        RestAssured.baseURI = baseURI;
        CreatingCustPayload data= object.readValue
                (new File(creatingCust_request_file_path), CreatingCustPayload.class);
        RequestSpecification request = given();
        System.out.println("request body is "+ data.toString());
        response = request.body(data).contentType("application/json").post(CUSTOMER_ENDPOINT);
        System.out.println("response body is  " + response.asString());

    }

    @Test(priority=1)
    public void gettingTheCustomer(){
        RequestSpecification httpRequest = given();
        String final_url = baseURI+CUSTOMER_ENDPOINT+"/me";
        Response response = httpRequest
                .request(Method.GET, final_url);
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is:" + responseBody);


    }
    }
