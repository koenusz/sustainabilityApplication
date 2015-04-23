package nl.mycompany.webapp.event;

import org.activiti.engine.delegate.event.ActivitiEvent;

public interface ActivitiEventSubscriber {
	
	public void onEvent(ActivitiEvent event);

}
