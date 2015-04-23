package nl.mycompany.webapp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.spring.server.SpringVaadinServlet;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class SustainabilityServlet extends SpringVaadinServlet {

	private static final long serialVersionUID = 8164776784342185354L;

	// override the default servlet functionality, this is needed to make sure
	// the login to the activiti engine is trasactional.
	/*@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SustainabilityApplicationUI.engineLogin();
		try {
			super.service(req, resp);
		} finally {
			SustainabilityApplicationUI.engineLogout();
		}
	}*/

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);
		// getService().addSessionInitListener(listener);
	}

}
