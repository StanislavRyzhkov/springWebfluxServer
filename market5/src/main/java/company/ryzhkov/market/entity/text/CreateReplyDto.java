package company.ryzhkov.market.entity.text;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class CreateReplyDto implements Serializable {

    @NotBlank
    @Size(max = 1000, message = "Некорректный ввод")
    private final String englishTitle;

    @NotBlank
    @Size(max = 1000, message = "Размер сообщения превышает 1000 символов")
    private final String content;

    @JsonCreator
    public CreateReplyDto(@JsonProperty("englishTitle") String englishTitle, @JsonProperty("content") String content) {
        this.englishTitle = englishTitle;
        this.content = content;
    }

    public CreateReplyAndUsernameDto toCreateReplyAndUsernameDto(String username) {
        return new CreateReplyAndUsernameDto(this, username);
    }
}
