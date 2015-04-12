package nl.mycompany.questionaire.identity;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
    }

    public AccessDeniedException(String string) {
        super(string);
    }
}
