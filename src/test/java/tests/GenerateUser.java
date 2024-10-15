package tests;

import org.junit.jupiter.api.Test;
import spec.DropsAuthSpec;
import spec.MailSpec;
import spec.SuccessTokenExtractor;
import spec.UserSpec;

import java.io.IOException;


import static io.restassured.RestAssured.given;

public class GenerateUser1 {

    @Test
    void fullFlowCreateUserAndCheckMessages() throws InterruptedException, IOException {
        // Генерация случайных данных аккаунта
        UserSpec user = UserSpec.generateRandomUser();

        // Создание нового пользователя на майле
        MailSpec.createAccount(user.getEmail(), user.getPassword());

        // Создание пользователя на ДТ
        DropsAuthSpec.createUser(user);

        // Извлечение токена для майла
        String token = MailSpec.getToken(user.getEmail(), user.getPassword());

        Thread.sleep(10000);

        // Получение сообщения
        String messagesResponse = MailSpec.getMessages(token);
        String downloadUrl = MailSpec.getDownloadUrl(messagesResponse);

        // Получение содержимого из downloadUrl
        String messageContent = MailSpec.getMessageContent(token, downloadUrl);

        // Извлечение токена из сообщения с помощью нового класса
        String confirmationToken = SuccessTokenExtractor.extractTokenFromMessage(messageContent);
        if (confirmationToken == null) {
            throw new RuntimeException("Confirmation token not found in the message content");
        }

        // Генерация ссылки с использованием токена
        String confirmationLink = DropsAuthSpec.createConfirmationLink(confirmationToken);

        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Confirmation Link: " + confirmationLink);

        // Автоматическое подтверждение майла
        given()
                .when()
                .get(confirmationLink)
                .then()
                .statusCode(200);
    }
}