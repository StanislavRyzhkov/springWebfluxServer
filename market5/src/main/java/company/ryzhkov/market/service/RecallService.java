package company.ryzhkov.market.service;

import company.ryzhkov.market.entity.recall.Recall;
import company.ryzhkov.market.entity.recall.RecallCreateDto;
import company.ryzhkov.market.repository.RecallRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecallService {
    private final static String RECALL_CREATED = "Мы получили Ваш отзыв";
    private RecallRepository recallRepository;

    @Autowired
    public void setRecallRepository(RecallRepository recallRepository) { this.recallRepository = recallRepository; }

    public Mono<String> createRecall(Mono<RecallCreateDto> dto) {
        return dto
                .map(Recall::createInstance)
                .flatMap(recallRepository::insert)
                .doOnNext(e -> log.info("Recall {} created", e.toString()))
                .map(e -> RECALL_CREATED);
    }
}
