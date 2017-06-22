package com.kwetter.bean;

import com.kwetter.dao.GroupDao;
import com.kwetter.dao.HashtagDao;
import com.kwetter.dao.PersonDao;
import com.kwetter.dao.TweetDao;
import com.kwetter.dao.jpa.JPA;
import com.kwetter.model.Group;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hein on 5/18/17.
 */
@Singleton
@Startup
public class StartUp implements Serializable {

    @Inject @JPA
    private HashtagDao hashtagDao;

    @Inject @JPA
    private PersonDao personDao;

    @Inject @JPA
    private TweetDao tweetDao;

    @Inject
    private GroupDao groupDao;

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @PostConstruct
    public void initialize() {
        populateDatabase();
    }

    @PreDestroy
    public void destroy() {
        // Clean up
    }

    private void populateDatabase() {
        Group userGroup = new Group("UserGroup");
        Group adminGroup = new Group("AdminGroup");
        groupDao.create(adminGroup);
        groupDao.create(userGroup);
        List<Person> newPeople = this.createPeople();
        for (Person p : newPeople) {
            userService.createUser(p);
        }
        // Add myself as administrator and verified user
        adminGroup.addUserToGroup(newPeople.get(0));
        newPeople.get(0).setVerified(true);
        newPeople.get(0).setDescription("I REALLY like programming");
        newPeople.get(0).setProfileImageUrl("http://scontent.cdninstagram.com/t51.2885-19/10483552_597018193747718_521109687_a.jpg");

        newPeople.get(10).setVerified(true);
        newPeople.get(10).setDescription("45th President of the United States of America");
        newPeople.get(10).setProfileImageUrl("https://qph.ec.quoracdn.net/main-thumb-t-28717-200-qylrwevlxgcnoddancubpsnfajpuqoba.jpeg");

        List<Tweet> newTweets = this.createTweets(newPeople);
        Instant createdAt = Instant.now();
        for (Tweet t : newTweets) {
            t.setCreatedAt( Date.from(createdAt.minus(ThreadLocalRandom.current().nextLong(0, 48+1), ChronoUnit.HOURS)));
            tweetService.sendTweet(t);
        }
        followPeople(newPeople);
        tweetService.likeTweet(newTweets.get(10).getId(), newPeople.get(0).getId());
        tweetService.likeTweet(newTweets.get(13).getId(), newPeople.get(0).getId());
        tweetService.likeTweet(newTweets.get(14).getId(), newPeople.get(0).getId());
        tweetService.likeTweet(newTweets.get(13).getId(), newPeople.get(2).getId());
        tweetService.likeTweet(newTweets.get(13).getId(), newPeople.get(3).getId());
        tweetService.likeTweet(newTweets.get(15).getId(), newPeople.get(1).getId());
        tweetService.likeTweet(newTweets.get(15).getId(), newPeople.get(10).getId());
        for (Person person : newPeople) {
            for (Group group : person.getGroups()) {
                System.out.println("Role for " + person.getUsername() + ": " + group.getGroupName());
            }
        }
    }

    private List<Person> createPeople() {
        List<Person> newPeople = new ArrayList<>();
        newPeople.add(new Person("Hein", "Dauven", "HDauven", "heindauven@gmail.com", "test"));
        newPeople.add(new Person("Regi", "Cookies", "Regi", "regicookies@gmail.com", "test"));
        newPeople.add(new Person("Dwight", "Sells", "Reaunged", "DwightMSells@dayrep.com", "test"));
        newPeople.add(new Person("Joann", "Williams", "Thisione", "JoannGWilliams@armyspy.com", "test"));
        newPeople.add(new Person("Rose", "Miller", "Drehanstered", "drehandstered@dayrep.com", "test"));
        newPeople.add(new Person("Michael", "Vega", "Licand", "licand@gmail.com", "test"));
        newPeople.add(new Person("Michelle", "Watt", "Malactind", "MichelleJWatt@dayrep.com", "test"));
        newPeople.add(new Person("Shirley", "Frazier", "Oppithatione", "shirley@teleworm.us", "test"));
        newPeople.add(new Person("Daniel", "Payne", "Eftees", "DanielJPayne@teleworm.us", "test"));
        newPeople.add(new Person("Susan", "Carrillo", "Coneve", "SusanSCarrillo@jourrapide.com", "test"));
        newPeople.add(new Person("Donald", "Trump", "therealdonald", "president@gov.us", "putin"));
        return newPeople;
    }

    private List<Tweet> createTweets(List<Person> people) {
        List<Tweet> newTweets = new ArrayList<>();
        newTweets.add(new Tweet("This is a #test", people.get(0)));
        newTweets.add(new Tweet("another #test", people.get(0)));
        newTweets.add(new Tweet("Hello, world!", people.get(1)));
        newTweets.add(new Tweet("This is a reply", people.get(1), newTweets.get(0)));
        newTweets.add(new Tweet("This is another reply", people.get(2), newTweets.get(0)));
        newTweets.add(new Tweet("This is a reply to a different tweet by myself", people.get(0), newTweets.get(1)));
        newTweets.add(new Tweet("My #first #tweet", people.get(2)));
        newTweets.add(new Tweet("WHY?", people.get(4)));
        newTweets.add(new Tweet("Lock her up! @Reaunged", people.get(8), newTweets.get(6)));
        newTweets.add(new Tweet("This app is pretty awesome!", people.get(5)));
        newTweets.add(new Tweet("Regipls @regi", people.get(0)));
        newTweets.add(new Tweet("Heinpls @hdauven", people.get(1), newTweets.get(10)));
        newTweets.add(new Tweet("well dAmN", people.get(9)));
        newTweets.add(new Tweet("Whenever you see the words 'sources say' in the fake news media, and they don't mention names..", people.get(10)));
        newTweets.add(new Tweet("..it is very possible that those sources don't exist but are made up by fake news writers. #FakeNews is the enemy!", people.get(10), newTweets.get(13)));
        newTweets.add(new Tweet("This tweet is #fakenews", people.get(0)));
        newTweets.add(new Tweet("#fakenews is trending", people.get(2)));
        return newTweets;
    }

    private void followPeople(List<Person> people) {
        userService.followUser(people.get(0).getId(), people.get(1).getId());
        userService.followUser(people.get(0).getId(), people.get(2).getId());
        userService.followUser(people.get(0).getId(), people.get(5).getId());
        userService.followUser(people.get(0).getId(), people.get(10).getId());
        userService.followUser(people.get(1).getId(), people.get(0).getId());
        userService.followUser(people.get(1).getId(), people.get(7).getId());
        userService.followUser(people.get(4).getId(), people.get(3).getId());
        userService.followUser(people.get(10).getId(), people.get(0).getId());
        userService.followUser(people.get(10).getId(), people.get(1).getId());
    }
}
