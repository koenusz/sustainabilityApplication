package nl.mycompany.webapp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import ru.xpoft.vaadin.SpringVaadinServlet;

import com.vaadin.annotations.VaadinServletConfiguration;

@WebServlet(urlPatterns = "/*", name = "SustainabilityUIServlet", asyncSupported = true, initParams={@WebInitParam(name="beanName", value="sustainabilityApplicationUI")})
@VaadinServletConfiguration(ui = SustainabilityApplicationUI.class, productionMode = false)
public class SustainabilityServlet  extends SpringVaadinServlet  {

	
	private static final long serialVersionUID = 8164776784342185354L;
	
	 
	 /*@SuppressWarnings("serial")
	 private final SessionInitListener listener = new SessionInitListener() {
	   public void sessionInit(SessionInitEvent event) throws ServiceException {
	            event.getService();
	            final VaadinSession session = event.getSession();
	        
	            session.addUIProvider(new SustainabilityUIProvider());
	   }
	 };*/
	 
	 public void init(ServletConfig servletConfig) throws ServletException {
		 
		 super.init(servletConfig);
	    //getService().addSessionInitListener(listener);
	  }



   

}
