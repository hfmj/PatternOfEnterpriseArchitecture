package com.majun.privateapp.domain;

/**
 * Created by Ma Jun on 2015/10/3 0003.
 */
public class Person extends DomainObject {
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    public Person(Long id, String lastNameArg, String firstNameArg, int numDependentsArg) {
        super();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }
}
