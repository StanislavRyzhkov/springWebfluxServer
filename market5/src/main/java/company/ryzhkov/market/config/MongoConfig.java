package company.ryzhkov.market.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = {"company.ryzhkov.market.repository"})
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.name}")
    private String dbName;

    @Bean
    @Override
    public MongoClient reactiveMongoClient() { return MongoClients.create(dbHost); }

    @Override
    protected String getDatabaseName() { return dbName; }
}
