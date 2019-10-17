package company.ryzhkov.market.repository;

import company.ryzhkov.market.entity.recall.Recall;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RecallRepository extends ReactiveMongoRepository<Recall, String> {

    @Override
    <S extends Recall> Mono<S> insert(S entity);
}
