package nl.mycompany.webapp.abstracts;

import nl.mycompany.webapp.SustainabilityApplicationUI;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


public abstract class AbstractPresenter<V extends View>  {
	
	
	private V view;
	
	private String previousView;
	
	/**
	 * Creates a new <code>AbstractPresenter</code> for the specified view. Any
	 * initialization code should go in the {@link #init()} method. When this
	 * constructor is invoked, the view might not yet have been initialized.
	 * 
	 * 
	 * @param view
	 *            the view that uses the userPresenter (must not be
	 *            <code>null</code>).
	 */
	public AbstractPresenter(V view) {
		if (view == null) {
			throw new IllegalArgumentException("null view");
		}
		this.view = view;
	}
	
	/**
	 * Creates a new <code>AbstractPresenter</code> without a view. A view must be
	 * specified using the {@link #setView(View)} method.
	 */
	public AbstractPresenter() {
	}

	/**
	 * Sets the view for this userPresenter.
	 * 
	 * @param view
	 *            the view to set.
	 */
	public void setView(V view) {
		this.view = view;
	}

	/**
	 * Gets the view that uses this userPresenter. If no view has been set, an
	 * exception is thrown.
	 * 
	 * @return the view instance (never <code>null</code>).
	 */
	public V getView() {
		if (view == null) {
			throw new IllegalStateException("View has not been set yet");
		}
		return view;
	}
	
	public void navigateBack()
	{
		if (previousView == null) {
			throw new IllegalStateException("PreviousView has not been set yet");
		}
		SustainabilityApplicationUI.navigateTo(previousView);
	}
	
	public String getPreviousView() {
		return previousView;
	}

	public void setPreviousView(String previousView) {
		this.previousView = previousView;
	}

	public void openSubWindow(String caption, Component component )
	{
        final Window sub = new Window(caption);
        sub.setModal(true);
        sub.center();
        UI.getCurrent().addWindow(sub);
        sub.setContent(component);
	}
	
	/**
	 * Convenience method that delegates to
	 * {@link View#fireViewEvent(ViewEvent)}.
	 * 
	 * @param event
	 *            the event to fire.
	 */
	public void fireViewEvent(ViewEvent event) {
		getView().fireViewEvent(event);
				
	}

	/**
	 * This method is called to initialize the userPresenter. When this happens, the
	 * view will already be initialized, i.e. invoking any methods on the view
	 * will not throw any exceptions.
	 * <p>
	 * This implementation does nothing, subclasses may override.
	 */
	public void init() {
		// NOP
	}
	
	

}
