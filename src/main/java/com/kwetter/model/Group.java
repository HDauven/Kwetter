package com.kwetter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hein on 5/28/17.
 */
@Entity
@Table(name = "KwetterGroup")
public class Group implements Serializable {

    @Id
    private String groupName;

    @ManyToMany
    @JoinTable(name="USER_GROUP",
            joinColumns = @JoinColumn(name = "groupName",
                    referencedColumnName = "groupName"),
            inverseJoinColumns = @JoinColumn(name = "userName",
                    referencedColumnName = "username"))
    private List<Person> users;

    public Group() {
        this.users = new ArrayList<>();
    }

    public Group(String groupName) {
        this.users = new ArrayList<>();
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public void addUserToGroup(Person user) {
        this.users.add(user);
        user.getGroups().add(this);
    }
}
