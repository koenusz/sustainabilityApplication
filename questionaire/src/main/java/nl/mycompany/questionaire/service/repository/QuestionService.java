package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;

public interface QuestionService {
	
    public Question saveQuestion(Question question);
	
	public Optional<Question> findQuestion(Long id);
	
	public List<Question> findAllQuestions();
	
	public void deleteQuestion(Long id);
	
	public List<Question> findNotInQuestionaire(Questionaire questionaire);

}
