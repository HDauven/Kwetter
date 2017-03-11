package com.kwetter.dao;

import com.kwetter.domain.Person;
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
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/person.yml")
    public void shouldReturnAllPerson() throws Exception {
        List<Person> personList = personDao.getAll();

        assertNotNull(personList);
        assertThat(personList.size(), is(2));
        assertThat(personList.get(0).getName(), is("Hein"));
        assertThat(personList.get(0).getLastName(), is("Dauven"));
        assertThat(personList.get(1).getName(), is("Regi"));
        assertThat(personList.get(1).getLastName(), is("Cookies"));
    }

    @Test
    @UsingDataSet("datasets/personEmpty.yml")
    public void shouldReturnAllPersonEmpty() throws Exception {
        List<Person> emptyPersonList = personDao.getAll();

        assertNotNull(emptyPersonList);
        assertThat(emptyPersonList.size(), is(0));
    }
}
