package nl.mycompany.questionaire.conf;

import nl.mycompany.questionaire.identity.CurrentUserFactoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class QuestionaireConfiguration {

	
	@Bean
	public CurrentUserFactoryBean currentUserFactory()
	{
		return new CurrentUserFactoryBean();
	}
	
	/*@Bean
	public RequireGroupAspect requireGroupAspect()
	{
		return Aspects.aspectOf(RequireGroupAspect.class);
	}*/


	
	
}
