package company.ryzhkov.market.util;

import company.ryzhkov.market.entity.message.Message;
import org.springframework.web.bind.support.WebExchangeBindException;

public class Util {

    public static Message messageOnInvalid(WebExchangeBindException e) {
        return new Message(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
