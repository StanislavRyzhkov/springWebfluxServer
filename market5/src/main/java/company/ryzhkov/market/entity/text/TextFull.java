package company.ryzhkov.market.entity.text;

import lombok.Getter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class TextFull implements Serializable {
    private final String id;
    private final String created;
    private final String title;
    private final String englishTitle;
    private final List<TextComponent> textComponents;
    private final NavigableSet<ReplyDto> replies;

    public TextFull() {
        this.id = "";
        this.created = "";
        this.title = "";
        this.englishTitle = "";
        this.textComponents = new ArrayList<>();
        this.replies = new TreeSet<>();
    }

    private TextFull(
            String id,
            Date created,
            String title,
            String englishTitle,
            List<TextComponent> textComponents,
            NavigableSet<Reply> replies
    ) {
        this.id = id;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.created = sdf.format(created);
        this.title = title;
        this.englishTitle = englishTitle;
        this.textComponents = textComponents;
        this.replies = replies
                .stream()
                .map(ReplyDto::createInstance)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static TextFull createInstance(Text text) {
        return new TextFull(
                text.getId(),
                text.getCreated(),
                text.getTitle(),
                text.getEnglishTitle(),
                text.getTextComponents(),
                text.getReplies()
        );
    }
}
