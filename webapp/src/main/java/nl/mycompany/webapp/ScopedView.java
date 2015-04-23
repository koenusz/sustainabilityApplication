package nl.mycompany.webapp;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = ScopedView.VIEW_NAME)
public class ScopedView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "scoped";

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);
        addComponent(new Label("This is a view scoped view"));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}