package spec;

import static io.restassured.RestAssured.given;

public class DropsAuthSpec {
    private static final String BASE_URL = "https://api2.icodrops.com/portfolio/api/user/";

    public static void createUser(UserSpec user) {
        String userValue = "{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}";
        given()
                .contentType("application/json")
                .body(userValue)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(200);
    }

    public static String createConfirmationLink(String token) {
        return "https://api.icodrops.com/portfolio/emailConfirm?token=" + token;
    }
}

