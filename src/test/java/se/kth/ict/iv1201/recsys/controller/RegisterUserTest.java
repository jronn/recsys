/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.RoleDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.RecsysException;
import se.kth.ict.iv1201.recsys.model.entities.Person;
import se.kth.ict.iv1201.recsys.model.entities.UserGroup;

/**
 *
 * @author jronn
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterUserTest {
    
    RecSysBeanImpl recSysBean = new RecSysBeanImpl();
    PersonDao personDao;
    UserGroupDao userGroupDao;
    RoleDao roleDao;

    
    @Before
    public void setUp() {
        personDao = mock(PersonDao.class);
        userGroupDao = mock(UserGroupDao.class);
        roleDao = mock(RoleDao.class);
        
        recSysBean.personDao = personDao;
        recSysBean.userGroupDao = userGroupDao;
        recSysBean.roleDao = roleDao;
    }
    
    /**
     * Test that persist methods are called on a new person and usergroup
     */
    @Test
    public void testPersistence() throws IllegalArgumentException, ExistingUserException, RecsysException {
        recSysBean.registerUser("name", "name", "email@email.com", "username", "password");
        Mockito.verify(personDao).persist(any(Person.class));
        Mockito.verify(userGroupDao).persist(any(UserGroup.class));
    }
    
    /**
     * Tests user registration input validation
     */
    @Test
    public void testValidation() throws ExistingUserException, RecsysException {
        assertFailsUserValidation("name","surname","emailemail.com","username","password");
        assertFailsUserValidation("name","surname","email@email","username","password");
        assertFailsUserValidation("name","s1urname","email@email.com","username","password");
        assertFailsUserValidation("n1ame","surname","email@email.com","username","password");
        assertFailsUserValidation("name","s@rname","email@email.com","username","password");
        assertFailsUserValidation("na@me","surname","email@email.com","username","password");
        assertFailsUserValidation("name","surname","email@email.com","u","password");
        assertFailsUserValidation("name","surname","email@email.com","username","p");
        assertFailsUserValidation("name","s","email@email.com","username","password");
        assertFailsUserValidation("n","surname","email@email.com","username","password");
        assertFailsUserValidation("na me","surname","email@email.com","username","password");
        assertFailsUserValidation("name","sur name","email@email.com","username","password");
        assertFailsUserValidation("name","sur name","email@em ail.com","username","password");
        
        assertSucceedsUserValidation("name","surname","email@email.com","username","password");
        assertSucceedsUserValidation("Torbjörn","Alenklint","emaildeluxe@emaildelux.com","AsernBme","p24@€Wrd");
        assertSucceedsUserValidation("Bo","Stig","emairereuxe@emaildelux.com","eAEme","p24@€Wrd");
    }
    
    private void assertSucceedsUserValidation(String name, String surname, String email,
                                            String username, String password) throws ExistingUserException, RecsysException {
        try {
            recSysBean.registerUser(name, surname, email, username, password);
        } catch (IllegalArgumentException ex) {
                        fail(name + "," + surname + "," + email + "," + username + "," + password
            + " should NOT be a valid input");
        }
    }
    
    private void assertFailsUserValidation(String name, String surname, String email,
                                            String username, String password) throws ExistingUserException, RecsysException {
        try {
            recSysBean.registerUser(name, surname, email, username, password);
            fail(name + "," + surname + "," + email + "," + username + "," + password
            + " should be a valid input");
        } catch (IllegalArgumentException ex) {}
    }
}
