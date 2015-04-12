package nl.mycompany.questionaire.identity;

import static org.junit.Assert.*;
import nl.mycompany.questionaire.conf.ApplicationConfiguration;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class IdentityTest {

	@Autowired
	IdentityService idService;
	
	@Test 
	public void createDeleteUserTest() {
		User user = idService.newUser("testuser");
		idService.saveUser(user);
		
		//check if the user is there
		assertNotNull(idService.createUserQuery().userId(user.getId()).singleResult());
		
		//delete the user and check it is not there
		idService.deleteUser(user.getId());
		assertNull(idService.createUserQuery().userId(user.getId()).singleResult());
	}

	
	@Test
	public void loginTest()
	{
		//login one of the pre setup users
		assertTrue(idService.checkPassword("client", "p"));
	}
}
