package company.ryzhkov.market.repository;

import company.ryzhkov.market.entity.text.Text;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TextRepository extends ReactiveMongoRepository<Text, String> {

    Mono<Text> findByEnglishTitle(String englishTitle);

    Flux<Text> findByKindOrderByCreatedDesc(String kind);

    Flux<Text> findByKindOrderByCreatedDesc(String kind, Pageable pageable);

    @Override
    <S extends Text> Mono<S> insert(S entity);

    @Override
    <S extends Text> Mono<S> save(S entity);
}
