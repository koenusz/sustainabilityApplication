package nl.mycompany.questionaire.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Question;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
@DatabaseSetup("/datasets/questions.xml")
public class DBUnitQuestionRepoTest {

	ApplicationContext context;
	
	@Autowired
	QuestionRepository repo;
	
	private static final Logger LOG = Logger.getLogger(DBUnitQuestionRepoTest.class);

	
    @Test
    public void searchShouldFindToItems() {
        List<Question> questions = repo.findAll();
        assertEquals(questions.size(), 2);
    }
    
    @After
    public void cleanDatabase()
    {
    	repo.deleteAll();
    }
	
}
