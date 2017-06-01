package com.kwetter.model.mapper;

import com.kwetter.dao.PersonDao;
import com.kwetter.dao.jpa.JPA;
import com.kwetter.model.Person;
import com.kwetter.rest.dto.UserDto;
import com.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by hein on 5/19/17.
 */
@Stateless
public class UserMapper extends GenericMapper<Person, UserDto>  {

    @Override
    public UserDto to(Person person) {
        UserDto userDto = new UserDto();
        Long id = person.getId();
        userDto.setId(id);
        userDto.setUsername(person.getUsername());
        userDto.setEmail(person.getEmail());
        userDto.setFirstName(person.getFirstName());
        userDto.setLastName(person.getLastName());
        userDto.setDescription(person.getDescription());
        userDto.setTweets(person.getTweetCount());
        userDto.setFollowers(person.getFollowersCount());
        userDto.setFollowing(person.getFollowingCount());
        userDto.setLikes(person.getLikesCount());
        userDto.setMentions(person.getMentionsCount());
        userDto.setCreatedAt(person.getCreatedAt());
        return userDto;
    }
}
