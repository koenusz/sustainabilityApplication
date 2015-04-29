package nl.mycompany.webapp.ui.question;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class QuestionGeneratorTest {
	
	@Autowired
	private transient QuestionGenerator generator;
	
	@Test
	public void generatorTest()
	{
		generator.init();
		generator.cleanUp();
	}

}
