package company.ryzhkov.market.entity.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateReplyAndUsernameDto {
    private final CreateReplyDto createReplyDto;
    private final String username;
}
