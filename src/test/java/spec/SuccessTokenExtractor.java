package spec;

import org.apache.commons.text.StringEscapeUtils;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuccessTokenExtractor {
    public static String extractTokenFromMessage(String messageContent) {
        // Декодируем MIME-контент
        String decodedContent = decodeMimeContent(messageContent);
        // Декодируем HTML-сущности
        String htmlDecodedContent = StringEscapeUtils.unescapeHtml4(decodedContent);

        // Регулярное выражение для извлечения токена
        String pattern = "token=3D([\\w-]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(htmlDecodedContent);

        if (m.find()) {
            // Возвращаем токен без префикса "3D"
            return m.group(1);
        }
        return null;
    }

    private static String decodeMimeContent(String content) {
        try {
            // Декодируем MIME-контент
            return MimeUtility.decodeText(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }
}

