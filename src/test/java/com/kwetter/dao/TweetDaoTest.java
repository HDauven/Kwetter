package com.kwetter.dao;

import com.kwetter.dao.jpa.GenericDaoJPA;
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

/**
 * Created by hein on 5/18/17.
 */
@RunWith(Arquillian.class)
public class TweetDaoTest {

    @EJB
    private TweetDao tweetDao;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "kwetter.war")
                .addClass(Tweet.class)
                .addClass(TweetDao.class)
                .addClass(GenericDao.class)
                .addClass(GenericDaoJPA.class)
                .addClass(Person.class)
                .addClass(Hashtag.class)
                .addAsResource("test-persistence.xml", "META-INFc/persistence.xml");
    }

    @Test
    @UsingDataSet()
    public void shouldCreateNewTweet() throws Exception {

    }
}
