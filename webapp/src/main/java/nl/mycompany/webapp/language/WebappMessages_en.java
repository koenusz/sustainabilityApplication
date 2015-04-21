package nl.mycompany.webapp.language;

public class WebappMessages_en extends WebappMessages {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4276826362573167658L;

	@Override
	public Object[][] getContents() {
		return contents_en;
	}

	static final Object[][] contents_en = {

			// LoginScreen
			{ Username, "Username" },
			{ Password, "Password" },
			{ Login, "Login" },
			{ LoginButton, "Login" },
			{ RegisterNewUser, "Register a new user" },
			{ ForgotPassword, "Forgot your password?" },
			{ InvalidUserOrPassword, "Invalid user or password" },
			{ InvalidUserOrPasswordLong,
					"A user with the given user name does not exist or the password is incorrect" },
			{ DemoUsernameHint, "Use demo/demo for a demonstration" },

			// SingleQuestionComponent
			{ DefaultQuestionStatus, "pending" } };
}
