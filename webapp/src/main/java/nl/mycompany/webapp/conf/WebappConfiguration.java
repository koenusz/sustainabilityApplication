package nl.mycompany.webapp.conf;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.aspect.EngineLoginAspect;
import nl.mycompany.webapp.ui.process.answerquestion.AnswerQuestionPresenter;
import nl.mycompany.webapp.ui.process.answerquestion.AnswerQuestionPresenterFactory;
import nl.mycompany.webapp.ui.process.answerquestion.NewAnswerQuestionProcessFormFactory;
import nl.mycompany.webapp.ui.process.answerquestion.NewAnswerQuestionProcessFormImpl;
import nl.mycompany.webapp.ui.process.answerquestion.SingleQuestionComponent;
import nl.mycompany.webapp.ui.process.answerquestion.SingleQuestionComponentFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableVaadin
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableSpringConfigured
public class WebappConfiguration {

	private static final Logger LOG = Logger
			.getLogger(WebappConfiguration.class);

	@Bean
	public SingleQuestionComponentFactory singleQuestionComponentFactory() {
		return new SingleQuestionComponentFactory() {
			
			@Override
			public SingleQuestionComponent createSingleQuestionComponent(Question question
					) {
				return singleQuestionComponent(question);
			}
		};
	}

	@Bean
	public NewAnswerQuestionProcessFormFactory startAnswerQuestionFormFactory() {
		return new NewAnswerQuestionProcessFormFactory() {

			@Override
			public NewAnswerQuestionProcessFormImpl startAnswerQuestionForm(
					) {
				return new NewAnswerQuestionProcessFormImpl();
			}
		};
	}
	

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	SingleQuestionComponent singleQuestionComponent(Question question)
	{
		return new SingleQuestionComponent(question);
	}

	@Bean
	EngineLoginAspect engineLogingAspect() {
		return new EngineLoginAspect();
	}

}
