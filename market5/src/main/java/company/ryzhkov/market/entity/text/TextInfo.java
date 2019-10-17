package company.ryzhkov.market.entity.text;

import lombok.Getter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class TextInfo implements Serializable {
    private final String id;
    private final String mainImage;
    private final String firstParagraph;
    private final String title;
    private final String englishTitle;
    private final String date;

    public TextInfo() {
        this.id = "";
        this.mainImage = "";
        this.firstParagraph = "";
        this.title = "";
        this.englishTitle = "";
        this.date = "";
    }

    private TextInfo(
            String id,
            String mainImage,
            String firstParagraph,
            String title,
            String englishTitle,
            Date date
    ) {
        this.id = id;
        this.mainImage = mainImage;
        this.firstParagraph = firstParagraph;
        this.title = title;
        this.englishTitle = englishTitle;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.date = sdf.format(date);
    }

    public static TextInfo createInstance(Text article) {
        return new TextInfo(
                article.getId(),
                article.getTextComponents().get(1).getSource(),
                article.getTextComponents().get(3).getContent(),
                article.getTitle(),
                article.getEnglishTitle(),
                article.getCreated()
        );
    }
}
