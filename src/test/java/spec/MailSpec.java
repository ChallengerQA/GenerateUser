package spec;

import java.io.IOException;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MailSpec {

    private static final String BASE_URL = "https://api.mail.tm";

    public static String createAccount(String email, String password) {
        return given()
                .contentType("application/json")
                .body("{\"address\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .when()
                .post(BASE_URL + "/accounts")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    public static String getToken(String email, String password) {
        return given()
                .contentType("application/json")
                .body("{\"address\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .when()
                .post(BASE_URL + "/token")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public static String getMessages(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL + "/messages/")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    public static String getDownloadUrl(String messagesResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(messagesResponse);
        return rootNode.path("hydra:member").get(0).path("downloadUrl").asText();
    }

    public static String getMessageContent(String token, String downloadUrl) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL + downloadUrl)
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }
}

