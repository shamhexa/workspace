package com.play;

import io.restassured.RestAssured;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class capitalCity {
    public static String captial;
    private ArrayList<HashMap<String,Object>> res;
    private HashMap<String,Object> retCountryDetail;

    public ArrayList<String> retrieveCaptial(String inputStr){

        ArrayList<String> res = RestAssured.given()
                                .baseUri("https://restcountries.eu")
                                .basePath("/rest/v2")
                                .when().get("/name/{name}",inputStr)
                                .then().extract().jsonPath().get("capital");
        return res;
    }
    public HashMap<String,Object> retrieveBasedOnCode(String iStr1){
        if(!iStr1.isEmpty() & iStr1.length()<=3) {
            retCountryDetail = RestAssured.given()
                    .baseUri("https://restcountries.eu")
                    .basePath("/rest/v2")
                    .when().get("/alpha/{code}", iStr1.trim())
                    .then().extract().jsonPath().get("");
        }else{
            System.out.println("Invalid Country code : Valid Country Code are 2 or 3 Digit");
        }
        return retCountryDetail;
    }

    //@NotNull
    public ArrayList<HashMap<String,Object>> retrieveAll(String iStr){

        if(!iStr.isEmpty()){
            res = RestAssured.given()
                    .baseUri("https://restcountries.eu")
                    .basePath("/rest/v2")
                    .when().get("/name/{name}",iStr)
                    .then().extract().jsonPath().get("");
        }else{
            System.out.println("Country Name Cann't be Null or Blank");
        }

        return res;
    }

    public static void main(String args[]) throws IOException {

        capitalCity cc = new capitalCity();

        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner in = new Scanner(System.in);
        captial = br.readLine();
        if(!captial.isEmpty()){
            System.out.println("The Capital City for :"+captial+" is :"+cc.retrieveAll(captial));
        }else{
            System.out.println("Please Enter Valid Country Name or Code");
        }*/

        String cont;
        do {
            System.out.println("Enter Country Name to View All the Details :");
            Scanner country = new Scanner(System.in);
            String cName = country.nextLine();
            System.out.println("The Capital City for :" + cName + " is :" + cc.retrieveAll(cName));
            System.out.println("Enter the Country Code to View All the Details : ");
            cName = country.nextLine();
            System.out.println("The Capital City for :" + cName + " is :" + cc.retrieveBasedOnCode(cName));
            Scanner in = new Scanner(System.in);
            System.out.println("Do you want to continue. Please Enter Y or N");
            cont = in.next();
        } while (cont.equalsIgnoreCase("Y"));


    }
}
