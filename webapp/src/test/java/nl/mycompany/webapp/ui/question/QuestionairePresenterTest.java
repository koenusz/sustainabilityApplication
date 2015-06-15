/*package nl.mycompany.webapp.ui.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.repository.ClientRepository;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.repository.QuestionaireRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ServletContextAware;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfiguration.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/questionaire_question_client.xml")
public class QuestionairePresenterTest implements ApplicationContextAware, ServletContextAware {
	
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private QuestionaireRepository questionaireRepo;
	
	@Autowired
	private ClientRepository clientRepo;
	
	
	private QuestionaireBuilderPresenter userPresenter;
	
	private ApplicationContext applicationContext;
	
	private ServletContext servletContext;
	
	
	
	@Test
	public void retrieveDataTest()
	{
		Questionaire qr = questionaireRepo.findOne(1l).get();
		assertNotNull(qr);
		
		assertEquals(qr.getQuestions().size(),2);
	}
	
	@Test
	public void initTest()
	{
		//check if theQuestionaire object is initialized
		assertEquals("testQuestionaire", userPresenter.getQuestionaire().getName());
		
	}
	
	@Before
    public void setUp() {
        
		
		MockHttpServletRequest request;
		request = Mockito.mock(MockHttpServletRequest.class);
        Mockito.when(request.getParameter("v-loc")).thenReturn(
                        "http://localhost/unittest/");
        Mockito.when(request.getParameter("v-cw")).thenReturn("1024");
        Mockito.when(request.getParameter("v-ch")).thenReturn("1024");
		
		MockHttpServletResponse response;
		response = Mockito.mock(MockHttpServletResponse.class);
		
		  RequestDispatcher dispatcher =
				  servletContext.getRequestDispatcher("/*");
			    try {
					dispatcher.include(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
		applicationContext.getBean("questionairePresenter");
    }

    @After
    public void tearDown() {
       // VaadinScopes.tearDown();
    	cleanDatabase();
    }

    @Test
    public void testAutowiring() {
        assertNotNull(questionRepo);
        assertNotNull(questionaireRepo);
        assertNotNull(clientRepo);
        assertNotNull(userPresenter);
    }
		
	
	public void cleanDatabase()
	{
		questionaireRepo.deleteAll();
		questionRepo.deleteAll();
		clientRepo.deleteAll();
	}
	


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

}
*/