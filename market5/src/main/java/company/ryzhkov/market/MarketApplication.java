package company.ryzhkov.market;

import company.ryzhkov.market.util.Bootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MarketApplication implements ApplicationRunner {
	private Bootstrap bootstrap;

	@Value("${password.hash.strength}")
	private int encoderStrength;

	@Autowired
	public void setBootstrap(Bootstrap bootstrap) { this.bootstrap = bootstrap; }

	public static void main(String[] args) { SpringApplication.run(MarketApplication.class, args); }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String[] arguments = args.getSourceArgs();
		if (arguments.length > 0) resolveTask(arguments[0]);
	}

	private void resolveTask(String argument) {
		if (argument.equals("-key")) bootstrap.createKey();
		if (argument.equals("-admin")) bootstrap.createAdminUser();
		if (argument.equals("-article")) bootstrap.createText("ARTICLE");
		if (argument.equals("-text")) bootstrap.createText("COMMON");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(encoderStrength); }
}
