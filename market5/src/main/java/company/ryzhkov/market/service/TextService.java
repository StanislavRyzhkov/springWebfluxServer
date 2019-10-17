package company.ryzhkov.market.service;

import company.ryzhkov.market.entity.text.*;
import company.ryzhkov.market.exception.NotFoundException;
import company.ryzhkov.market.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.NavigableSet;

@Service
public class TextService {
    private final static String TEXT_NOT_FOUND = "Текст не найден";
    private final static String CREATED = "Комментарий создан";

    private TextRepository textRepository;

    @Autowired
    public void setTextRepository(TextRepository textRepository) { this.textRepository = textRepository; }

    private Mono<Text> findTextByEnglishTitle(String englishTitle) {
        return textRepository.findByEnglishTitle(englishTitle).defaultIfEmpty(new Text()).map(e -> {
            if (e.getId() == null) throw new NotFoundException(TEXT_NOT_FOUND);
            return e;
        });
    }

    public Mono<TextFull> getFullTextByEnglishTitle(String englishTitle) {
        return findTextByEnglishTitle(englishTitle).map(TextFull::createInstance);
    }

    public Flux<TextInfo> getAllArticles() {
        return textRepository.findByKindOrderByCreatedDesc("ARTICLE").map(TextInfo::createInstance);
    }

    public Flux<TextInfo> getAllArticles(int number) {
        return textRepository
                .findByKindOrderByCreatedDesc("ARTICLE", PageRequest.of(0, number))
                .map(TextInfo::createInstance);
    }

    public Mono<String> createReply(CreateReplyAndUsernameDto dto) {
        return toReplyAndArticle(dto).flatMap(this::insertReply).map(e -> CREATED);
    }

    private Mono<ReplyAndArticle> toReplyAndArticle(CreateReplyAndUsernameDto dto) {
        return textRepository
                .findByEnglishTitle(dto.getCreateReplyDto().getEnglishTitle())
                .map(e -> {
                    Reply reply = new Reply();
                    reply.setUsername(dto.getUsername());
                    reply.setContent(dto.getCreateReplyDto().getContent());
                    reply.setCreated(new Date());
                    return new ReplyAndArticle(reply, e);
                });
    }

    private Mono<Text> insertReply(ReplyAndArticle replyAndArticle) {
        Text article = replyAndArticle.getArticle();
        NavigableSet<Reply> replies = article.getReplies();
        replies.add(replyAndArticle.getReply());
        article.setReplies(replies);
        return textRepository.save(article);
    }
}
