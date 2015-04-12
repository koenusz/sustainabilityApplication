package nl.mycompany.questionaire.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages="nl.mycompany.questionaire.repository")
public class RepositoryConfiguration {
	

}
