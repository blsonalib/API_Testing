package com.fundoopush;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FundooPushTesting {
    String tokens = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMGI0MDZkZmU3NTRjMDAzMjFjODYyYiJ9LCJpYXQiOjE1Nzc3OTU3MTgsImV4cCI6MTU3Nzg4MjExOH0.AzvghkNTpXaCkwsPRFSokIUj88qoDtNIw-qJIiibrHk";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://fundoopush-backend-dev.bridgelabz.com";
            }

    @Test
    public void givenFundooPush_OnPOSTRegistration_ShouldReturnSuccessRegistration() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "sonalibankar19@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .post("/registration");
        int statusCode = response.getStatusCode();
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTValidEmailAndValidPassword_ShouldReturnSuccessfullyLogin() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "sonabankar05@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .post("/login");

        System.out.println(response);
        int statusCode = response.getStatusCode();
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTInvalidEmailAndValidPassword_ShouldReturnErrorMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .post("/login");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTLogOut_ShouldReturnSuccesFullyLogOut() {
        Response response = RestAssured.given().
                header("token",tokens)
                .when()
                .post("/logout");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        System.out.println( "Logged out successfully from the system");
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTAddRediret_ShouldReturnSuccessFullMessage() {
        File testFile = new File("/home/admin1/Downloads/red-garden-plant-green-56866.jpg");
      Response response = RestAssured.given()
              .accept(ContentType.JSON)
              .header("token",tokens)
              .multiPart("image",testFile)
              .formParam("title","flower")
              .formParam("description","rose flower")
              .formParam("redirect_link","www.google.com")
              .formParam("is_published",false)
              .formParam("archive",false)
              .formParam("youtube_flag",false)
              .formParam("youtube_url","www.youtube.com")
              .formParam("video_link","https://www.youtube.com/channel/UCng7gac45gf215Le7eVFjPg?view_as=subscriber")
              .when().
              post("/redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void givenFundooPush_OnPOSTWrongUrl_ShouldReturnErrorMessage() {
        File testFile = new File("/home/admin1/Downloads/red-garden-plant-green-56866.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .multiPart("image",testFile)
                .formParam("title","flower")
                .formParam("description","rose flower")
                .formParam("redirect_link","com")
                .formParam("is_published",false)
                .formParam("archive",false)
                .formParam("youtube_flag",false)
                .formParam("youtube_url","com")
                .formParam("video_link","https://www.youtube.com/channel/UCng7gac45gf215Le7eVFjPg?view_as=subscriber")
                .when()
                .post("/redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void givenFundooPush_OnPUTUpdateRedirect_ShouldReturnSuccessfullMessage() {
        File testFile = new File("/home/admin1/Downloads/happy-new-year-letters-and-gold.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam("_id","5e0b4281fe754c00321c8631")
                .multiPart("image",testFile)
                .formParam("title","new year")
                .formParam("description","happy new year")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published",false)
                .formParam("archive",false)
                .formParam("youtube_flag",false)
                .formParam("youtube_url","www.youtube.com")
                .formParam("video_link","https://www.youtube.com/channel/UCng7gac45gf215Le7eVFjPg?view_as=subscriber")
                .when()
                .put("/redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPUTUpdateRedirectLinkWrong_ShouldReturnErrorMessage() {
        File testFile = new File("/home/admin1/Downloads/happy-new-year-letters-and-gold.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam("_id","5e0b4281fe754c00321c8631")
                .multiPart("image",testFile)
                .formParam("title","new year")
                .formParam("description","happy new year")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published",false)
                .formParam("archive",false)
                .formParam("youtube_flag",false)
                .formParam("youtube_url","www.youtube.com")
                .formParam("video_link","https:")
                .when()
                .put("/redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnGETRedirect_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .when()
                .get("/redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnGETWithoutParameterRedirect_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/bl-redirects");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTHashtagEdit_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam("{ \"redirect_id\": \"5e0b49c11d45f50033f09c2b\", \"hashtag\": \"#bridgelabz #solutions #mumbai #bangalore #fundoopush\"}")
                .when()
                .post("/hashtag/edit");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush1_OnPOSTWithoutHashtagSymbolEdit_ShouldReturnErrorMessage() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam( "{ \"redirect_id\": \"5e0b49c11d45f50033f09c2b\", \"hashtag\": \"bridgelabz\"}")
                .when()
                .post("/hashtag/edit");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnGETDataAsPerHashTag_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .when()
                .get("/redirects/hashtag/pune");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnGETWithHashFunction_ShouldReturnErrorMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .when()
                .get("/redirects/hashtag/%23%23%23%23pune");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTWebScrapingApi_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam( "{ \"url\": \"https://study.com/academy/lesson/what-is-an-html-document-structure-types-examples.html\"}")
                .when()
                .post("/web-scraping");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTWebScrapingApiForHTTP_ShouldReturnErrorMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam( "{ \"url\": \"http://study.com/academy/lesson/what-is-an-html-document-structure-types-examples.html\"}")
                .when()
                .post("/web-scraping");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTWebScrapingForWrongFile_ShouldReturnErrorMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam(  "{ \"url\": \"https://study.com/academy/lesson/what-is-an-html-document-structure-types-examples.gdshgh\"}")
                .when()
                .post("/web-scraping");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTHashTagSearchApi_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam( "hashtag","#bridgelabz")
                .when()
                .post("/search/hashtag");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTWithoutHashTagSymbolSearchApi_ShouldReturnErrorMessage() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam( "hashtag","bridgelabz")
                .when()
                .post("/search/hashtag");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush_OnPOSTJobs_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .body("{ \"redirect_id\": \"5e0b36f4f709c704106d4a8b\", \"years_of_experience\": \"1\", \"salary\": \"3.6\", \"location\": \"Mumbai\", \"company_profile\": \"Ideation\", \"hashtag\": \"#bridgelabz\"}")
                .when()
                .post("/jobs");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }


    @Test
    public void givenFundooPush1_OnPOSTAddedHashTagForJobs_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokens)
                .formParam("job_id","5d5e306726f53e1bfaade87f")
                .formParam("hashtag","#Bridgelabz #Mumbai #Bangalore")
                .when()
                .post("/jobs/hashtag/add");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenFundooPush1_OnPOSTRemoveForJobs_ShouldReturnSuccessfullMessage() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .formParam("job_id","5e09d4b74d22670032530fbe")
                .formParam("hashtag_id","5d662f00f149c627ab5d3efd")
                .when()
                .post("/jobs/hashtag/remove");
        int statusCode = response.getStatusCode();
        String responceBody = response.asString();
        System.out.println(responceBody);
        MatcherAssert.assertThat(statusCode,Matchers.equalTo(HttpStatus.SC_OK));
    }
}
