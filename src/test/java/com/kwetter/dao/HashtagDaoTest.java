package com.kwetter.dao;

import com.kwetter.dao.jpa.GenericDaoJPA;
import com.kwetter.dao.jpa.HashtagDaoJPA;
import com.kwetter.model.Hashtag;
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
public class HashtagDaoTest {

    @EJB
    private HashtagDao hashtagDao;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "kwetter.war")
                .addClass(Hashtag.class)
                .addClass(HashtagDao.class)
                .addClass(HashtagDaoJPA.class)
                .addClass(GenericDao.class)
                .addClass(GenericDaoJPA.class)
                .addClass(Tweet.class)
                .addClass(Hashtag.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet()
    public void shouldCreateNewHashtag() throws Exception {

    }


}
