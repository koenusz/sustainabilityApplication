package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.domain.Questionaire;

public interface QuestionaireService {
	
	public Questionaire createNewQuestionaire(Client client);
	
	public void deleteQuestionaire(Long questionaireId);
	
	public Optional<Questionaire> findQuestionaire(Long id);
	
	public List<Questionaire> findAllQuestionaires();
	
	public Questionaire findLatestQuestionaireByClient(Client client);
	
	public void saveQuestionaire(Questionaire questionaire);

}
