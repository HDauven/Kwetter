package com.kwetter.rest;

import com.kwetter.exception.DataNotFoundException;
import com.kwetter.exception.UserUnauthorizedException;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.model.mapper.TweetMapper;
import com.kwetter.model.mapper.UserMapper;
import com.kwetter.rest.dto.TweetDto;
import com.kwetter.rest.dto.UserDto;
import com.kwetter.rest.dto.UserDtoWithPassword;
import com.kwetter.service.UserService;
import com.kwetter.service.TweetService;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.registry.infomodel.User;

import java.security.MessageDigest;
import java.util.List;

/**
 * Created by hein on 5/19/17.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@DeclareRoles({"UserRole", "AdminRole"})
public class UserResource {

    @Inject
    TweetService tweetService;

    @Inject
    UserService userService;

    @GET
    @RolesAllowed("AdminRole")
    public Response getAllUsers() {
        List<UserDto> users = new UserMapper().to(userService.getAllUsers());
        GenericEntity<List<UserDto>> userList = new GenericEntity<List<UserDto>>(users){};
        return Response.ok(userList).build();
    }

    @POST
    public Response createUser(UserDtoWithPassword userDto, @Context UriInfo uriInfo) {
        Person user = CreatePersonWithUserDto(userDto);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(user.getId()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Long userId) {
        Person user = findUser(userId);
        UserDto userDto = new UserMapper().to(user);
        return Response.ok(userDto).build();
    }

    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") Long userId, UserDtoWithPassword userDto, @Context UriInfo uriInfo) {
        Person user = findUser(userId);
        user = updatePersonWithUserDto(userDto, user);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(user.getId()));
        return Response.ok().location(builder.build()).build();
    }

    @GET
    @Path("/{userId}/timeline")
    public Response getTimeline(@PathParam("userId") Long userId) {
        findUser(userId);
        List<TweetDto> tweets = new TweetMapper().to(tweetService.buildTimeline(userId));
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @GET
    @Path("/{userId}/tweets")
    public Response getTweets(@PathParam("userId") Long userId) {
        findUser(userId);
        List<TweetDto> tweets = new TweetMapper().to(userService.getTweets(userId));
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @GET
    @Path("/{userId}/followers")
    public Response getFollowers(@PathParam("userId") Long userId) {
        findUser(userId);
        List<UserDto> users = new UserMapper().to(userService.getFollowers(userId));
        GenericEntity<List<UserDto>> userList = new GenericEntity<List<UserDto>>(users){};
        return Response.ok(userList).build();
    }

    @GET
    @Path("/{userId}/following")
    public Response getFollowing(@PathParam("userId") Long userId) {
        findUser(userId);
        List<UserDto> users = new UserMapper().to(userService.getFollowing(userId));
        GenericEntity<List<UserDto>> userList = new GenericEntity<List<UserDto>>(users){};
        return Response.ok(userList).build();
    }

    @GET
    @Path("/{userId}/mentions")
    public Response getMentions(@PathParam("userId") Long userId) {
        findUser(userId);
        List<TweetDto> tweets = new TweetMapper().to(userService.getMentions(userId));
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @GET
    @Path("/{userId}/likes")
    public Response getLikes(@PathParam("userId") Long userId) {
        findUser(userId);
        List<TweetDto> tweets = new TweetMapper().to(userService.getLikes(userId));
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @POST
    @Path("/login")
    public Response login(UserDtoWithPassword requestedUser) {
        Person user = userService.getUser(requestedUser.getUsername());
        System.out.println("User is: " + user.getUsername());
        System.out.println("Password is: " + user.getPassword());
        System.out.println("Given password: " + toSha256(requestedUser.getPassword()));
        if (user == null || !user.getPassword().equals(toSha256(requestedUser.getPassword()))) {
            throw new UserUnauthorizedException("Wrong credentials!");
        }
        UserDto userDto = new UserMapper().to(user);
        return Response.ok(userDto).build();
    }

    private Person findUser(Long userId) {
        Person user = userService.getUser(userId);
        if (user == null) {
            throw new DataNotFoundException("User does not exist!");
        }
        return user;
    }

    private Person CreatePersonWithUserDto(UserDtoWithPassword userDto) {
        if (userDto.getFirstName() == null || userDto.getLastName() == null || userDto.getEmail() == null
                || userDto.getUsername() == null || userDto.getPassword() == null) {
            throw new DataNotFoundException("User is invalid, missing data.");
        }
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String email = userDto.getEmail();
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        Person user = new Person(firstName, lastName, username, email, password);
        user.setVerified(userDto.isVerified());
        if (userDto.getDescription() != null){
            user.setDescription(userDto.getDescription());
        }
        if (userDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(userDto.getProfileImageUrl());
        }
        userService.createUser(user);
        return user;
    }

    private Person updatePersonWithUserDto(UserDtoWithPassword userDto, Person user) {
        if (userDto.getFirstName() != null){
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null){
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPassword() != null){
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getEmail() != null){
            user.setEmail(userDto.getEmail());
        }
        if (userDto.isVerified()){
            user.setVerified(userDto.isVerified());
        }
        if (userDto.getDescription() != null){
            user.setDescription(userDto.getDescription());
        }
        if (userDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(userDto.getProfileImageUrl());
        }
        userService.updateUser(user);
        return user;
    }

    private String toSha256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            return null;
        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
