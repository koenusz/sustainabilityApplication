package nl.mycompany.webapp;


import nl.mycompany.webapp.ui.login.LoginPresenter;

import org.apache.log4j.Logger;
import org.mockito.Mockito;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;


public class MockUI extends SustainabilityApplicationUI {
	
	private static final Logger LOG = Logger.getLogger(MockUI.class);

	
	
    public MockUI() {
        init(null);
}

@Override
protected void init(VaadinRequest request) {
        UI.setCurrent(this);
        setSession(new VaadinSession(null));
        setId("unittest");
        request = Mockito.mock(VaadinRequest.class);
        Mockito.when(request.getParameter("v-loc")).thenReturn(
                        "http://localhost/unittest/");
        Mockito.when(request.getParameter("v-cw")).thenReturn("1024");
        Mockito.when(request.getParameter("v-ch")).thenReturn("1024");

        LOG.debug(" recieving request " +  request);
        
        getPage().init(request);
}
}
