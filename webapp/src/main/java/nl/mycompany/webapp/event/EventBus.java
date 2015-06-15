package nl.mycompany.webapp.event;

import java.util.ArrayList;
import java.util.List;

import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.client.ClientPresenter;

import org.apache.log4j.Logger;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class EventBus {
	
	private static final Logger LOG = Logger.getLogger(ClientPresenter.class);
	

	private List<AbstractPresenter<? extends View>> subscribers = new ArrayList<>();
	
	
	/**
	 * Convenience method that delegates to
	 * {@link View#fireViewEvent(ViewEvent)}.
	 * 
	 * @param event
	 *            the event to fire.
	 */
	public void fireViewEvent(ViewEvent event) {
		
		for(AbstractPresenter<? extends View> presenter : subscribers)
		{
			LOG.debug("Event" + event.getClass() + "is sent to "  + presenter.getClass());
		presenter.fireViewEvent(event);
		}
				
	}
	
	/**
	 * This method is used let presenters subscribe to the event bus
	 * 
	 * @param userPresenter
	 */
	public void subscribe( AbstractPresenter<? extends View> presenter)
	{
		subscribers.add(presenter);
	}
	
	/**
	 * This method is used let presenters unsubscribe to the event bus
	 * 
	 * @param userPresenter
	 */
	public void unSubscribe( AbstractPresenter<? extends View> presenter)
	{
		subscribers.remove(presenter);
	}

}
