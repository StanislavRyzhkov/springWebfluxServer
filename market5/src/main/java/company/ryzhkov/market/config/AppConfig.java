package company.ryzhkov.market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebFlux
public class AppConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/api/media/**")) {
            registry
                    .addResourceHandler("/api/media/**")
                    .addResourceLocations("file:media/")
                    .setCacheControl(CacheControl.maxAge(365L, TimeUnit.DAYS));
        }
    }
}
