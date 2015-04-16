package nl.mycompany.questionaire.conf;

import org.activiti.engine.test.ActivitiRule;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.subethamail.wiser.Wiser;

@Configuration
public class TestContextConfiguration {
	
	
	@Bean
	public ActivitiRule activityRule(ProcessEngineFactoryBean pefb)
	{
		try {
			return new ActivitiRule(pefb.getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Bean(initMethod="start", destroyMethod="stop")
	public Wiser wiserServer()
	{
		Wiser wiser = new Wiser();
		wiser.setPort(9898);
		return wiser;
	}
	
	

}
