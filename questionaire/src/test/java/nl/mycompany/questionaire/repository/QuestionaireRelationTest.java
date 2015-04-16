package nl.mycompany.questionaire.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.mycompany.questionaire.conf.ApplicationConfiguration;
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
public class QuestionaireRelationTest {
	
	
	@Autowired
	QuestionRepository questionRepo;
	
	@Autowired
	QuestionaireRepository questionairerepo;
	
	@Autowired
	ClientRepository clientRepo;
	
	@Test
	public void testRetrieveData()
	{
		Questionaire qr = questionairerepo.findOne(1l).get();
		assertNotNull(qr);
		
		assertEquals(qr.getQuestions().size(),2);
	}
	
	@After
	public void cleanDatabase()
	{
		questionairerepo.deleteAll();
		questionRepo.deleteAll();
		clientRepo.deleteAll();
	}

}
