package nl.mycompany.questionaire.exception;

public class NoClientException extends Exception {
	

	public NoClientException(String userName)
	{
		super("No client connected to user: " + userName);
	}

}
