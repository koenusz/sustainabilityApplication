package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.repository.QuestionaireRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionaireServiceImpl implements QuestionaireService {
	
	@Autowired
	QuestionaireRepository questionaireRepository;
	
	@Override
	public Optional<Questionaire> findQuestionaire(Long id) {
		
		return questionaireRepository.findOne(id);
	}
	@Override
	public Questionaire findLatestQuestionaireByClient(Client client) {
		
		List<Questionaire> list = questionaireRepository.findAll();
		
		Questionaire latest = null;
		
		for(Questionaire q : list)
		{
			if(q.getClient().getName().equals(client.getName()))
			{
				if(latest == null)
				{
					latest = q;
				}else
				{
				latest = latest.getCreationDate().isAfter(q.getCreationDate()) ? latest : q;
			}}
		}
		
		return latest;
	}
	@Override
	public Questionaire createNewQuestionaire(Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteQuestionaire(Long questionaireId) {
		questionaireRepository.delete(questionaireRepository.findOne(questionaireId).get());
	}
	@Override
	public void saveQuestionaire(Questionaire questionaire) {
		questionaireRepository.save(questionaire);
		
	}
	@Override
	public List<Questionaire> findAllQuestionaires() {
		return questionaireRepository.findAll();
	}
	
	
	

}
