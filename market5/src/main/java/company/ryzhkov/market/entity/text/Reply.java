package company.ryzhkov.market.entity.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply implements Comparable<Reply> {

    private String username;
    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created;

    @Override
    public int compareTo(Reply o) {
        return -created.compareTo(o.getCreated());
    }
}
