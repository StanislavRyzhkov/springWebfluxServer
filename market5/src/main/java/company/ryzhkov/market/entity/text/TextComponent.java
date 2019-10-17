package company.ryzhkov.market.entity.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextComponent implements Comparable<TextComponent> {
    private int number;
    private String tag;
    private String content;
    private String source;

    @Override
    public int compareTo(TextComponent o) {
        return number - o.getNumber();
    }
}
