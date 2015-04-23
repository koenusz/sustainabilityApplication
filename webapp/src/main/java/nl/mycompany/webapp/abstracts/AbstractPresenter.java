package nl.mycompany.webapp.abstracts;



public abstract class AbstractPresenter<V extends View>  {
	
	private V view;
	
	/**
	 * Creates a new <code>AbstractPresenter</code> for the specified view. Any
	 * initialization code should go in the {@link #init()} method. When this
	 * constructor is invoked, the view might not yet have been initialized.
	 * 
	 * @see AbstractView#createPresenter()
	 * 
	 * @param view
	 *            the view that uses the presenter (must not be
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
	 * Sets the view for this presenter.
	 * 
	 * @param view
	 *            the view to set.
	 */
	public void setView(V view) {
		this.view = view;
	}

	/**
	 * Gets the view that uses this presenter. If no view has been set, an
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
	 * This method is called to initialize the presenter. When this happens, the
	 * view will already be initialized, i.e. invoking any methods on the view
	 * will not throw any exceptions.
	 * <p>
	 * This implementation does nothing, subclasses may override.
	 */
	public void init() {
		// NOP
	}

}
