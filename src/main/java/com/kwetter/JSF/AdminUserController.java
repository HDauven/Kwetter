package com.kwetter.JSF;

import com.kwetter.model.Person;
import com.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by hein on 5/29/17.
 */
@Named
@RequestScoped
public class AdminUserController {

    @Inject
    private UserService userService;
    private List<Person> users;
    private Long userId;
    private Person user;
    private String username, password, email, firstName, lastName, description, profileImageUrl;
    private boolean verified;

    @PostConstruct
    public void init() {
        users = userService.getAllUsers();
    }

    public String doCreateUser() {
        user = new Person(firstName, lastName, username, email, password);
        if (description != null) {
            user.setDescription(description);
        }
        user.setProfileImageUrl(profileImageUrl);
        user.setVerified(verified);
        userService.createUser(user);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User created",
                        "The user " + user.getUsername() + " has been created with id=" + user.getId()));

        return "user.xhtml";
    }

    public void doFindUserById() {
        user = userService.getUser(userId);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Person> getUsers() {
        return userService.getAllUsers();
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
