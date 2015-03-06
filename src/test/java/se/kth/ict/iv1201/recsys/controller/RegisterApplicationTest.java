/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.kth.ict.iv1201.recsys.integration.CompetenceDao;
import se.kth.ict.iv1201.recsys.integration.PersonDao;
import se.kth.ict.iv1201.recsys.integration.RoleDao;
import se.kth.ict.iv1201.recsys.integration.UserGroupDao;
import se.kth.ict.iv1201.recsys.model.ApplicationDTO;
import se.kth.ict.iv1201.recsys.model.AvailabilityListing;
import se.kth.ict.iv1201.recsys.model.BadInputException;
import se.kth.ict.iv1201.recsys.model.CompetenceListing;
import se.kth.ict.iv1201.recsys.model.ExistingUserException;
import se.kth.ict.iv1201.recsys.model.NotLoggedInException;
import se.kth.ict.iv1201.recsys.model.RecsysException;
import se.kth.ict.iv1201.recsys.model.entities.Competence;
import se.kth.ict.iv1201.recsys.model.entities.Person;

/**
 *
 * @author jronn
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterApplicationTest {
    
    RecSysBeanImpl recSysBean = new RecSysBeanImpl();
    PersonDao personDao;
    RoleDao roleDao;
    CompetenceDao competenceDao;
    
    @Before
    public void setUp() {
        personDao = mock(PersonDao.class);
        roleDao = mock(RoleDao.class);
        competenceDao = mock(CompetenceDao.class);
        
        recSysBean.personDao = personDao;
        recSysBean.roleDao = roleDao;
        recSysBean.competenceDao = competenceDao;
        
        doReturn(new Person()).when(personDao).findById(any(String.class));
        doReturn(new Competence("test")).when(competenceDao).findById(any(String.class));
    }
    
    /**
     * Test that persist methods are called on the person entity
     */
    @Test
    public void testPersistence() throws NotLoggedInException, RecsysException, BadInputException, ParseException {
        ApplicationDTO testDto = new ApplicationDTO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        testDto.addAvailability(new AvailabilityListing(formatter.parse("2000-01-01"), formatter.parse("2001-02-02")));
        testDto.addCompetence(new CompetenceListing("competence",2));
        recSysBean.registerApplication("username", testDto);
        Mockito.verify(personDao).persist(any(Person.class));
    }
    
    /**
     * Tests application registration input validation
     */
    @Test
    public void testValidation() throws ExistingUserException, RecsysException, ParseException {
        ApplicationDTO testDto = new ApplicationDTO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String[] validDates = {"2010-01-01", "2011-01-01", 
                               "1911-01-01", "2010-01-02",
                               "1990-04-04", "1990-05-03",
                               "1990-05-05", "1991-04-04",
                               "1920-12-12", "1921-01-01",
                               "1801-10-10", "2001-09-09"};
        
        for(int i = 0; i < validDates.length; i+=2) {
            Date from = formatter.parse(validDates[i]);
            Date to = formatter.parse(validDates[i+1]);
            testDto = new ApplicationDTO();
            testDto.addAvailability(new AvailabilityListing(from, to));
            assertSucceedsValidation("name",testDto);
        }
        
       String[] invalidDates = {"2010-01-01", "2009-01-01", 
                               "2010-01-01", "2009-01-01",
                               "1990-04-04", "1990-03-05",
                               "1993-05-05", "1993-04-04",
                               "1922-12-12", "1921-01-01",
                               "2010-10-10", "2009-11-11"};
        
        for(int i = 0; i < invalidDates.length; i+=2) {
            Date from = formatter.parse(invalidDates[i]);
            Date to = formatter.parse(invalidDates[i+1]);
            testDto = new ApplicationDTO();
            testDto.addAvailability(new AvailabilityListing(from, to));
            assertFailsValidation("name",testDto);
        }
        
        Date from = formatter.parse("2010-01-01");
        Date to = formatter.parse("2011-02-02");
        testDto = new ApplicationDTO();
        testDto.addAvailability(new AvailabilityListing(from, to));
        assertFailsValidation(null, testDto);
    }
    
    private void assertSucceedsValidation(String username, ApplicationDTO a) throws RecsysException {
        try {
            recSysBean.registerApplication(username, a);
        } catch (BadInputException | NotLoggedInException ex) {
            StringBuilder sb = new StringBuilder();
            for(AvailabilityListing al : a.getAvailabilities()) {
                sb.append("\nFrom: " + al.fromDate + "to" + al.toDate);
            }
            
            fail("Failed validation for username:" + username + " with "
                                + " applicationDTO dates:" + sb.toString());
        }
    }
    
    private void assertFailsValidation(String username, ApplicationDTO a) throws RecsysException {
        try {
            recSysBean.registerApplication(username, a);
            
            StringBuilder sb = new StringBuilder();
            for(AvailabilityListing al : a.getAvailabilities()) {
                sb.append("\nFrom: " + al.fromDate + "to" + al.toDate);
            }
            
            fail("Validation for username:" + username + " with "
                                + " applicationDTO dates:" + sb.toString() + 
                    " should not be valid");
        }
        catch (BadInputException | NotLoggedInException ex) {}
    }
}
