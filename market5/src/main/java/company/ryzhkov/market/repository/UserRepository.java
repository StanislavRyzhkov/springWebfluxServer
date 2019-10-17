package company.ryzhkov.market.repository;

import company.ryzhkov.market.entity.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);

    Mono<User> findByUsernameAndStatus(String username, String status);

    Mono<User> findByEmail(String email);

    @Override
    Mono<User> findById(String s);

    @Override
    <S extends User> Mono<S> insert(S entity);

    @Override
    <S extends User> Mono<S> save(S entity);

    @Override
    Flux<User> findAll();
}
