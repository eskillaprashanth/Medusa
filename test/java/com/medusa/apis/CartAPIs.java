package com.medusa.apis;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.cart.payloads.AddingCartWithQuantityAsStringPayload;
import org.cart.payloads.AddingItemToCartPayload;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.api.endpoints.EndPoints.CREATING_CART_ENDPOINT;
public class CartAPIs {
    String baseURI;
    String cartId;
    String adding_Item_To_Cart;
    String adding_Item_To_Cart_Quantity_As_String;
    public static Response response;
    String varientId;
    private static String requestBody = "{}";

    @BeforeMethod
    public void setup() throws IOException {
        try (InputStream input = new FileInputStream("TestData/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            baseURI = prop.getProperty("base_url");
            adding_Item_To_Cart=prop.getProperty("adding_Item_To_Cart");
            adding_Item_To_Cart_Quantity_As_String= prop.getProperty("adding_Item_To_Cart_Quantity_As_String");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        RestAssured.baseURI = baseURI;
        RequestSpecification request = given();
        response = request.body(requestBody).post(CREATING_CART_ENDPOINT);
        String cartId  = response.jsonPath().getString("cart.id");
        this.cartId = cartId;

    }
    @Test(priority=0)
    public void creatingNewCart(){
        RestAssured.baseURI = baseURI;
        RequestSpecification request = given();
        response = request.body(requestBody).post(CREATING_CART_ENDPOINT);
        System.out.println("response body is  " + response.asString());
        Assertions.assertEquals("cart", response.jsonPath().getString("cart.object"));
    }
    @Test(priority=1)
    public void addingItemToCartTest() throws IOException {
        ObjectMapper object=new ObjectMapper();
        AddingItemToCartPayload payload= object.readValue
                (new File(adding_Item_To_Cart), AddingItemToCartPayload.class);
        RequestSpecification request = given();
        RestAssured.baseURI = baseURI;
        System.out.println("request body is "+ payload.toString());
        String varientId=payload.getVariant_id();
        this.varientId=varientId;
        response = request.body(payload).contentType("application/json").post(CREATING_CART_ENDPOINT+"/"+cartId+"/line-items");
        System.out.println("response body is  " + response.asString());

}
@Test(priority=2)
public void addingItemCartWithQuantityAsStringTest() throws IOException {
    ObjectMapper object=new ObjectMapper();
    AddingCartWithQuantityAsStringPayload payload= object.readValue
            (new File(adding_Item_To_Cart_Quantity_As_String), AddingCartWithQuantityAsStringPayload.class);
    RequestSpecification request = given();
    RestAssured.baseURI = baseURI;
    System.out.println("request body is "+ payload.toString());

    response = request.body(payload).contentType("application/json").post(CREATING_CART_ENDPOINT+"/"+cartId+"/line-items");
    System.out.println("response body is  " + response.asString());
    Assertions.assertEquals("invalid_data", response.jsonPath().getString("type"));
    Assertions.assertEquals("quantity must be an integer number", response.jsonPath().getString("message"));

}
    @Test(priority=3)
    public void deletingTheCartItemTest(){
        RequestSpecification httpRequest = given();
        RestAssured.baseURI = baseURI;
        String final_url = CREATING_CART_ENDPOINT+"/"+cartId+"/line-items/"+varientId;
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