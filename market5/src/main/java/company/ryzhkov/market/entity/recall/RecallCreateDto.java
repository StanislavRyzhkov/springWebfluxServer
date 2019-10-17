package company.ryzhkov.market.entity.recall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class RecallCreateDto {
    private final static String TOO_LONG_AUTHOR = "Слишком длинное имя";
    private final static String TOO_LONG_EMAIL = "Слишком длинный email";
    private final static String INVALID_EMAIL = "Некорректный email";
    private final static String TOO_LONG_TOPIC = "Слишком длинное поле тема";
    private final static String TOO_LONG_TEXT = "Слишком длинный текст сообщения";

    @NotBlank
    @Size(max = 120, message = TOO_LONG_AUTHOR)
    private final String author;

    @NotBlank
    @Size(max = 120, message = TOO_LONG_EMAIL)
    @Email(message = INVALID_EMAIL)
    private final String email;

    @NotBlank
    @Size(max = 120, message = TOO_LONG_TOPIC)
    private final String topic;

    @NotBlank
    @Size(max = 4500, message = TOO_LONG_TEXT)
    private final String text;

    @JsonCreator
    public RecallCreateDto(
            @JsonProperty("author") String author,
            @JsonProperty("email") String email,
            @JsonProperty("topic") String topic,
            @JsonProperty("text") String text) {
        this.author = author;
        this.email = email;
        this.topic = topic;
        this.text = text;
    }
}
