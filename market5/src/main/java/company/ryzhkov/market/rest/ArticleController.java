package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.entity.text.CreateReplyDto;
import company.ryzhkov.market.entity.text.TextFull;
import company.ryzhkov.market.entity.text.TextInfo;
import company.ryzhkov.market.security.TokenProvider;
import company.ryzhkov.market.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {
    private TextService textService;
    private TokenProvider provider;

    @Autowired
    public void setTextService(TextService textService) { this.textService = textService; }

    @Autowired
    public void setProvider(TokenProvider provider) { this.provider = provider; }

    @GetMapping("all")
    public Flux<TextInfo> getAllArticles() { return textService.getAllArticles(); }

    @GetMapping("two")
    public Flux<TextInfo> getNewTwoArticles() { return textService.getAllArticles(2); }

    @GetMapping("detail/{englishTitle}")
    public Mono<TextFull> getArticleByEnglishTitle(@PathVariable("englishTitle") String englishTitle) {
        return textService.getFullTextByEnglishTitle(englishTitle);
    }

    @PostMapping("reply")
    @PreAuthorize("hasRole('USER')")
    public Mono<Message> createReply(
            ServerHttpRequest request,
            @Valid @RequestBody Mono<CreateReplyDto> createReplyDto
    ) {
        return Mono.zip(createReplyDto, provider.getUsername(request))
                .map(e -> e.getT1().toCreateReplyAndUsernameDto(e.getT2()))
                .flatMap(textService::createReply)
                .map(Message::new);
    }
}
