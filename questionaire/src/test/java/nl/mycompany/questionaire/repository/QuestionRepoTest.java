package nl.mycompany.questionaire.repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Question;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class QuestionRepoTest implements ApplicationContextAware{

	ApplicationContext context;
	
	@Autowired
	QuestionRepository repo;
	
	private static final Logger LOG = Logger.getLogger(QuestionRepoTest.class);

	
	@Test(expected = NoSuchElementException.class)
	public void saveAndRetrieveQuestionTest()
	{
		
		Question question = new Question();
		question.setDomain("chemical");
		question.setQuestionText("Hoeveel is 1+1?");
		repo.save(question);
		
		Optional<Question> retQuestion = repo.findOne(question.getId());
		
		 Assert.assertNotNull(retQuestion.get());
		 LOG.debug("returned Question " + retQuestion.get().getId());
		 
		 //delete the question
		 
		 repo.delete(question);
		 
		 Optional<Question> delQuestion = repo.findOne(question.getId());
			
		 //this line will throw NoSuchElementException when empty
		 delQuestion.get();
		 
		
		
	}
	
	
	    public void setApplicationContext(ApplicationContext context)
	            throws BeansException
	    {
	        this.context = context;
	    }
}
