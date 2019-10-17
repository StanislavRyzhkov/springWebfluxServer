package company.ryzhkov.market.entity.text;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ReplyDto implements Comparable<ReplyDto> {
    private final String username;
    private final String content;
    private final Date date;
    private final String created;

    private ReplyDto(String username, String content, Date date) {
        this.username = username;
        this.content = content;
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.created = sdf.format(this.date);
    }

    @Override
    public int compareTo(ReplyDto o) {
        return -this.date.compareTo(o.getDate());
    }

    public static ReplyDto createInstance(Reply reply) {
        return new ReplyDto(reply.getUsername(), reply.getContent(), reply.getCreated());
    }
}
