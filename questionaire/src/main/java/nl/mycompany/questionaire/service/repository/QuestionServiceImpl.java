package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	QuestionRepository questionRepository;

	@Override
	public Question saveQuestion(Question question) {
		
		return questionRepository.save(question);
	}

	@Override
	public Optional<Question> findQuestion(Long id) {
		
		return questionRepository.findOne(id);
	}

	@Override
	public void deleteQuestion(Long id) {
		questionRepository.delete(questionRepository.findOne(id).get());
		
	}

	@Override
	public List<Question> findAllQuestions() {
		return questionRepository.findAll();
	}

}
