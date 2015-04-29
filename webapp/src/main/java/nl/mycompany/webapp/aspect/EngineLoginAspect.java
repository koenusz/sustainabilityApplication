package nl.mycompany.webapp.aspect;

import nl.mycompany.webapp.SustainabilityApplicationUI;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class EngineLoginAspect {
	
	private static final Logger LOG = Logger
			.getLogger(EngineLoginAspect.class);
	
	@Before("@annotation(engineLogin)")
	public void engineLogin(EngineLogin engineLogin)
	{
		if (SustainabilityApplicationUI.getCurrent().getLoggedInUser() != null) {
			LOG.debug("logging into engine for user "
					+ SustainabilityApplicationUI.getCurrent().getLoggedInUser());
			SustainabilityApplicationUI.getCurrent().getCurrentUserFactoryBean()
					.setCurrentUsername(SustainabilityApplicationUI
							.getCurrent().getLoggedInUser().getName());
		}
	}
	
	@After("@annotation(engineLogin)")
	public void engineLogout(EngineLogin engineLogin)
	{
		SustainabilityApplicationUI.getCurrent().getCurrentUserFactoryBean()
		.setCurrentUsername(null);
	}

}
