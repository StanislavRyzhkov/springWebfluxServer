package company.ryzhkov.market.entity.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyAndArticle {
    private final Reply reply;
    private final Text article;
}
