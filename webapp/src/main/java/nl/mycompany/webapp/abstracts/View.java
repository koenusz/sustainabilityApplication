package nl.mycompany.webapp.abstracts;

public interface View extends com.vaadin.navigator.View {
	
	public void fireViewEvent(ViewEvent event);

}
