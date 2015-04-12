package nl.mycompany.questionaire;

import java.io.IOException;
import java.util.Properties;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
	
	ConfigurableApplicationContext context;
	
	private static final Logger LOG = Logger
			.getLogger(ProcessInitializer.class);
	
	public static void main(String[] args) {
		new Application();
		
		
    	
	}
	
	public Application() {
		super();
		new Application( new AnnotationConfigApplicationContext(ApplicationConfiguration.class));
		
		System.out.println("start");
		
		Properties log4j  = new Properties();
		
		try {
			log4j.load(Application.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(log4j.isEmpty())
		{
			System.err.println("logging properties not loaded");
		} else{
			//PropertyConfigurator.configure(log4j);
			System.out.println("logging properties loaded");
		}
		
		LOG.info("start");
	}
	
	
	public Application(ConfigurableApplicationContext context) {
		super();
		this.context = context;
	}



	

}
