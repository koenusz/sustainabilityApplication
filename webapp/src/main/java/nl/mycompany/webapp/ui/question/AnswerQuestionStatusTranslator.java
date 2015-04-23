package nl.mycompany.webapp.ui.question;

public class AnswerQuestionStatusTranslator implements AnswerQuestionStatus{
	
	
	public static String translate(String status)
	{
		String translated = bundle.getMessage(status, null);		
		return translated;
	}

}
