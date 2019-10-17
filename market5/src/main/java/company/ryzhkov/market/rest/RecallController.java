package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.entity.recall.RecallCreateDto;
import company.ryzhkov.market.service.RecallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recall")
@CrossOrigin(origins = "*")
public class RecallController {
    private RecallService recallService;

    @Autowired
    public void setRecallService(RecallService recallService) { this.recallService = recallService; }

    @PostMapping
    public Mono<Message> createRecall(@Valid @RequestBody Mono<RecallCreateDto> dto) {
        return recallService.createRecall(dto).map(Message::new);
    }
}
