package nl.mycompany.webapp.abstracts;

import java.util.EventObject;


/**
 * Base class for events that are fired by a {@link View}. Views can use these
 * events to inform other objects of significant events, e.g. events that affect
 * the flow of the application itself (login, logout, move to another view,
 * etc.).
 * 
 * @see View#fireViewEvent(ViewEvent)
 * 
 * @author Petter HolmstrÃ¶m
 * @since 1.0
 */
public abstract class ViewEvent extends EventObject {

	private static final long serialVersionUID = -1085851042904330047L;

	/**
	 * Creates a new <code>ViewEvent</code>.
	 * 
	 * @param source
	 *            the view in which the event originally occurred (must not be
	 *            <code>null</code>).
	 */
	public ViewEvent(View source) {
		super(source);
	}

	@Override
	public View getSource() {
		return (View) super.getSource();
	}

}