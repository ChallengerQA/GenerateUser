package spec;

import java.util.UUID;

public class UserSpec {


    private String username;
    private String email;
    private String password;

    public UserSpec(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserSpec generateRandomUser() {
        String email = "monkey" + UUID.randomUUID().toString().substring(0, 4) + "@abaot.com";
        String password = "123123";
        String username = "monkeyy" + UUID.randomUUID().toString().substring(0, 8);
        return new UserSpec(username, email, password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

