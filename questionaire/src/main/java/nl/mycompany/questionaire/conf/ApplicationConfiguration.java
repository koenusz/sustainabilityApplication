package nl.mycompany.questionaire.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@Import({ RepositoryConfiguration.class , ActivitiConfiguration.class, QuestionaireConfiguration.class})
@ComponentScan({"nl.mycompany.questionaire", "nl.mycompany.core" , "nl.mycompany.webapp"})
@PropertySource("classpath:application.properties")
@EnableVaadin
//to enable the aspect to be configured by spring
@EnableSpringConfigured
public class ApplicationConfiguration {
	
	

}
