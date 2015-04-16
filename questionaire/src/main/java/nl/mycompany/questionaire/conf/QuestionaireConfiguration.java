package nl.mycompany.questionaire.conf;

import nl.mycompany.questionaire.identity.CurrentUserFactoryBean;
import nl.mycompany.questionaire.identity.aspect.RequireGroupAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionaireConfiguration {

	
	@Bean
	public CurrentUserFactoryBean currentUserFactory()
	{
		return new CurrentUserFactoryBean();
	}

	
	
}
