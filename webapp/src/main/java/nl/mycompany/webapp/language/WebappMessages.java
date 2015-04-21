package nl.mycompany.webapp.language;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class WebappMessages extends ListResourceBundle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return null;
	}

	// LoginScreen
	public static final String Username = generateId();
	public static final String Password = generateId();
	public static final String Login = generateId();
	public static final String LoginButton = generateId();
	public static final String RegisterNewUser = generateId();
	public static final String ForgotPassword = generateId();
	public static final String InvalidUserOrPassword = generateId();
	public static final String InvalidUserOrPasswordLong = generateId();
	public static final String DemoUsernameHint = generateId();

	// questionaire view

	// SingleQuestionComponent
	public static final String DefaultQuestionStatus = generateId();


	public static String generateId() {
		return new Integer(ids++).toString();
	}

	static int ids = 0;

}
