package nl.mycompany.webapp.ui.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.repository.ClientRepository;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.repository.QuestionaireRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionGenerator {
	
	private static final Logger LOG = Logger
			.getLogger(QuestionGenerator.class);
	
	@Autowired
	private  QuestionRepository questionRepo;
	
	@Autowired
	private  QuestionaireRepository questionainreRepo;
	
	@Autowired
	private  ClientRepository clientRepo;
	
		
	public  void init()
	{
		LOG.debug("init");
		//tesing code
		
				generateAndSave(2, "tompoes");
				generateAndSave(2, "pannekoek");
				List<Question> panList = generate(2, "pannekoek");
				List<Question> flapList = generate(2, "appelflap");
				flapList.addAll(panList);
				Client client = generateClient("companyA");
				generateQuestionaire("smullijst", flapList, client);
	}
	
	public  void cleanUp()
	{
		LOG.debug("cleanup");
		questionainreRepo.deleteAll();
		clientRepo.deleteAll();
		questionRepo.deleteAll();
	}
	
	public  List<Question> generateAndSave(int amount, String domain)
	{
		List<Question> list = generate(amount, domain);
		
		for(Question q : list)
		{
			questionRepo.save(q);
		}
		return list;
		
	}
	
	
	public  void generateQuestionaire(String name, List<Question> list, Client client)
	{
		Questionaire questionaire = new Questionaire();
		questionaire.setName(name);
		questionaire.setCreationDate(LocalDateTime.now());
		questionaire.setClient(client);
		questionaire.setQuestion(list);
		questionainreRepo.save(questionaire);
		
		
		
		
	}
	
	
	public  Client generateClient(String name)
	{
		Client client = new Client();
		client.setName(name);
		clientRepo.save(client);
		return client;
	}
	
	
	public  List<Question> generate(int amount, String domain)
	{
		List<Question> list = new ArrayList<>();
		
		for(int i = 0; i < amount; i++)
		{
		Question question = new Question();
		question.setQuestionText("Wie lust er " + i + " " + domain + "?");
		question.setDomain(domain);
		
		list.add(question);
		}
		
		return list;
	}

}
