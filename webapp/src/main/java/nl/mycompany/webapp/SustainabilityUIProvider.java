package nl.mycompany.webapp;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class SustainabilityUIProvider extends UIProvider {
	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return SustainabilityApplicationUI.class;
	}
}