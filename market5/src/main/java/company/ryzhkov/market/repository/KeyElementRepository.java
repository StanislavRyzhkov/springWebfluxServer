package company.ryzhkov.market.repository;

import company.ryzhkov.market.entity.key.KeyElement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface KeyElementRepository extends ReactiveMongoRepository<KeyElement, String> {

    @Override
    <S extends KeyElement> Mono<S> insert(S entity);

    @Override
    Flux<KeyElement> findAll();
}
