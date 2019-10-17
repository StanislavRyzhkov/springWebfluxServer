package company.ryzhkov.market.entity.message;

import lombok.Getter;

@Getter
public class Message {
    public static final Message ACCESS_DENIED = new Message("Доступ запрещен");
    private final String text;

    public Message() { this.text = ""; }

    public Message(String text) { this.text = text; }
}
