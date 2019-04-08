package com.testPlay;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Test2 {

    //capitalCity cc = new capitalCity();
    public RequestSpecification spec;
    public HashMap<String,Object> response;

    @BeforeEach
    public void setup(){
        spec = new RequestSpecBuilder().setBaseUri("https://restcountries.eu").setContentType(ContentType.JSON).build();
    }


    public void verifyRespective(){
        String iStr = "ab ";
        System.out.println(iStr.trim());
        response = given()
                .baseUri("https://restcountries.eu")
                .basePath("/rest/v2")
                .when().get("/alpha/{code}",iStr.trim())
                .then().extract().jsonPath().get("");

        System.out.println(response);


    }

    @Test
    public void verifyCapitalofFinland(){
        String str = "finland";
        ArrayList<String> res = given().spec(spec).get("/rest/v2/name/{name}",str).then().extract().jsonPath().get("capital");
        System.out.println(res.get(0));
        Assertions.assertEquals("Helsinki", res.get(0));
    }

    @Test
    public void verifyServiceFailureStatusCode(){
        String str = "Finland1";
        int i = given().spec(spec).get("/rest/v2/name/{name}", str).then().extract().statusCode();
        Assertions.assertNotEquals(200,i);
    }

    @Test
    public void verifyCountryNotNull(){
        String res = given().spec(spec).get("/rest/v2/name/{name}", " ").then().extract().response().asString();
        Assertions.assertNotNull(res);
    }

    @Test
    public void verifyJSONAttributeIntegerorNot(){
        int res = given().spec(spec).get("/rest/v2/name/{name}", "Finland").then().extract().jsonPath().getInt("population[0]");
        //int res1 = given().spec(spec).get("/rest/v2/name/{name}", "Finland").then().extract().body("$.[?(@.");
        Assertions.assertEquals(5491817,res);
    }

    @Test
    public void verifyServiceSuccessStatusCode(){
        int statusCode = given().spec(spec).get("/rest/v2/name/{name}", "finland").then().extract().statusCode();
        Assertions.assertEquals(200,statusCode);
    }

    @Test
    public void getPopulationvalueForFinland(){
        given(spec).pathParam("name","Finland").get("/rest/v2/name/{name}").then()
                .statusCode(200)
                .body("population", is(5491817),
                        "cioc",equalTo("FIN"));
    }

}
