package com.kwetter.dao;

import com.kwetter.dao.jpa.GenericDaoJPA;
import com.kwetter.dao.jpa.PersonDaoJPA;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by hein on 3/11/17.
 */
@RunWith(Arquillian.class)
public class PersonDaoTest {

    @EJB
    private PersonDao personDao;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "kwetter.war")
                .addClass(Person.class)
                .addClass(PersonDao.class)
                .addClass(PersonDaoJPA.class)
                .addClass(GenericDao.class)
                .addClass(GenericDaoJPA.class)
                .addClass(Tweet.class)
                .addClass(Hashtag.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/personEmpty.yml")
    public void shouldCreateNewPerson() throws Exception {
        List<Person> personList = personDao.findAll();
        assertNotNull(personList);
        assertThat(personList.size(), is(0));

        personList.add(createTestPerson());
        assertThat(personList.size(), is(1));
        assertThat(personList.get(0).getFirstName(), is("Regi"));
        assertThat(personList.get(0).getLastName(), is("Cookies"));
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldUpdatePerson() throws Exception {
        List<Person> personList = personDao.findAll();
        Person p = personList.get(1);
        p.setFirstName("Regisha");
        p.setLastName("Vuijk");
        personDao.update(p);

        assertThat(personList.size(), is(2));
        assertThat(personList.get(1).getFirstName(), is("Regisha"));
        assertThat(personList.get(1).getLastName(), is("Vuijk"));
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldRemovePerson() throws Exception {
        List<Person> personListBeforeRemoval = personDao.findAll();
        assertThat(personListBeforeRemoval.size(), is(2));
        personDao.remove(personListBeforeRemoval.get(0));

        List<Person> personListAfterRemoval = personDao.findAll();
        assertThat(personListAfterRemoval.size(), is(1));
    }

    @Test
    @UsingDataSet("datasets/personEmpty.yml")
    public void shouldFindPersonById() throws Exception {
        Person p = createTestPerson();
        personDao.create(p);
        assertNotNull(personDao.findById(1L));
    }

    @Test
    @UsingDataSet("datasets/personEmpty.yml")
    public void shouldNotFindPersonById() throws Exception {
        assertNull(personDao.findById(999L));
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldReturnAllPersons() throws Exception {
        List<Person> personList = personDao.findAll();

        assertNotNull(personList);
        assertThat(personList.size(), is(2));
        assertThat(personList.get(0).getFirstName(), is("Hein"));
        assertThat(personList.get(0).getLastName(), is("Dauven"));
        assertThat(personList.get(1).getFirstName(), is("Regi"));
        assertThat(personList.get(1).getLastName(), is("Cookies"));
    }

    @Test
    @UsingDataSet("datasets/personEmpty.yml")
    public void shouldReturnNoPersons() throws Exception {
        List<Person> emptyPersonList = personDao.findAll();

        assertNotNull(emptyPersonList);
        assertThat(emptyPersonList.size(), is(0));
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldAlreadyExist() {
        assertThat(personDao.alreadyExists(1L), is(true));
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldNotExist() {
        assertThat(personDao.alreadyExists(999999L), is(false));
    }

    private Person createTestPerson() {
        return new Person("Regi", "Cookies", "Regi", "regicookies@gmail.com", "test");
    }
}
