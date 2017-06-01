package com.kwetter.domain;

import javax.persistence.*;

/**
 * Created by hein on 3/11/17.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "Person.getAll",
                    query = "select p from Person p"))
public class Person {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String lastName;

    public Person() {
    }

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
