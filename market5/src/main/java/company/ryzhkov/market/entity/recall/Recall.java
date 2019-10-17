package company.ryzhkov.market.entity.recall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recalls")
public class Recall {

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created;

    private String author;
    private String email;
    private String topic;
    private String text;

    public static Recall createInstance(RecallCreateDto dto) {
        Recall recall = new Recall();
        recall.setCreated(new Date());
        recall.setAuthor(dto.getAuthor());
        recall.setEmail(dto.getEmail());
        recall.setTopic(dto.getTopic());
        recall.setText(dto.getText());
        return recall;
    }

    @Override
    public String toString() { return String.format("Author %s topic %s\n", author, topic); }
}
