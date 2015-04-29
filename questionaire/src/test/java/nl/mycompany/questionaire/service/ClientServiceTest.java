package nl.mycompany.questionaire.service;


	import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.repository.ClientRepository;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.repository.QuestionaireRepository;
import nl.mycompany.questionaire.service.repository.QuestionaireService;

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
	public class ClientServiceTest {
		
		
		@Autowired
		QuestionRepository questionRepo;
		
		@Autowired
		QuestionaireRepository questionairerepo;
		
		@Autowired
		ClientRepository clientRepo;
		
		@Autowired 
		QuestionaireService service;
		
		@Test
		public void findQuestionaireTest()
		{
			Optional<Questionaire> qr = service.findQuestionaire(1l);
			assertTrue(qr.isPresent());
			
			assertEquals(qr.get().getQuestions().size(),2);
		}
		
		@Test
		public void saveQuestionaireTest()
		{
			Optional<Questionaire> qr = service.findQuestionaire(1l);
			assertTrue(qr.isPresent());
			
			assertEquals(qr.get().getQuestions().size(),2);
			Question question = new Question();
			qr.get().getQuestions().add(question);
			service.saveQuestionaire(qr.get());
			
			Optional<Questionaire> qr2 = service.findQuestionaire(1l);
			
			assertEquals(qr2.get().getQuestions().size(),3);
			
			
		}
		
		
		
		@Test
		public void deleteQuestionaireTest()
		{
			Optional<Questionaire> qr = service.findQuestionaire(1l);
			assertTrue(qr.isPresent());
			service.deleteQuestionaire(qr.get().getId());
			
			Optional<Questionaire> qr2 = service.findQuestionaire(1l);
			assertFalse(qr2.isPresent());
			
		}
		
		@After
		public void cleanDatabase()
		{
			questionairerepo.deleteAll();
			questionRepo.deleteAll();
			clientRepo.deleteAll();
		}

	}



