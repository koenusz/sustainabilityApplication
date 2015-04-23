package nl.mycompany.webapp.aspect;

import nl.mycompany.webapp.SustainabilityApplicationUI;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class EngineLoginAspect {
	
	@Before("@annotation(engineLogin)")
	public void engineLogin(EngineLogin engineLogin)
	{
		SustainabilityApplicationUI.engineLogin();
	}
	
	@After("@annotation(engineLogin)")
	public void engineLogout(EngineLogin engineLogin)
	{
		SustainabilityApplicationUI.engineLogout();
	}

}
