package nl.mycompany.webapp.conf;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.aspect.EngineLoginAspect;
import nl.mycompany.webapp.ui.question.QuestionairePresenter;
import nl.mycompany.webapp.ui.question.SingleQuestionComponent;
import nl.mycompany.webapp.ui.question.SingleQuestionComponentFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableVaadin
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@ComponentScan({"nl.mycompany.webapp"})
public class WebappConfiguration {
	
	private static final Logger LOG = Logger
			.getLogger(WebappConfiguration.class);
	
	

	@Bean
	public SingleQuestionComponentFactory singleQuestionComponentFactory()
	{
		return new SingleQuestionComponentFactory(){
			public SingleQuestionComponent createSingleQuestionComponent(Question question)
			{
				return singleQuestionComponent(question);
			}
		};
	}
	
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SingleQuestionComponent singleQuestionComponent(Question question) {
    	LOG.debug("component created");
        return new SingleQuestionComponent(question);
    }
		
    @Bean
    EngineLoginAspect engineLogingAspect()
    {
    	return new EngineLoginAspect();
    }
    
}
