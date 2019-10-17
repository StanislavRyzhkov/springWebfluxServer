package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.text.TextFull;
import company.ryzhkov.market.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/text")
@CrossOrigin(origins = "*")
public class CommonTextController {
    private TextService textService;

    @Autowired
    public void setTextService(TextService textService) { this.textService = textService; }

    @GetMapping("/{englishTitle}")
    public Mono<TextFull> about(@PathVariable String englishTitle) {
        return textService.getFullTextByEnglishTitle(englishTitle);
    }
}
