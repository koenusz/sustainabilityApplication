package nl.mycompany.questionaire.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/questionaire_question_client.xml")
public class CustomRepositoryImplTest {
	
	@Autowired
	QuestionRepository repo;
	
	@Autowired
	QuestionaireRepository questionaireRepository;
	
	@Autowired
	QuestionaireRepository clientRepository;
	
	
	@Test
	public void retrieveNotInQuestionaireTest()
	{
		Questionaire q = questionaireRepository.findOne(1L).get();
		
		List<Question> list = repo.findNotInQuestionaire(q);
		
		assertTrue(list.size() == 2);
	}
	
	@After
    public void cleanDatabase()
    {
    	repo.deleteAll();
    	questionaireRepository.deleteAll();
    	clientRepository.deleteAll();
    	
    }

}
