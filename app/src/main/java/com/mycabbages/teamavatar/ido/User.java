package com.mycabbages.teamavatar.ido;

/**
 * Created by adlythebaud on 3/13/18.
 */

public class User {

    enum RelationshipType {
        Single,
        Engaged,
        Married,
        Pre_Divorce,
        Divorced

        // an enum can also have methods. If that's the case, add a semicolon to end of divorced.
    }

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String userId;
    private RelationshipInvestment[] allRelationshipInvestments;
    private RelationshipInvestment[] currentRelationshipInvestments;
    private RelationshipType relationshipType;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }



    public User(String firstName, String lastName, String email,
                String phoneNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public User(String firstName, String lastName, String email,
                String phoneNumber, String gender, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RelationshipInvestment[] getAllRelationshipInvestments() {
        return allRelationshipInvestments;
    }

    public void setAllRelationshipInvestments(RelationshipInvestment[] allRelationshipInvestments) {
        this.allRelationshipInvestments = allRelationshipInvestments;
    }

    public RelationshipInvestment[] getCurrentRelationshipInvestments() {
        return currentRelationshipInvestments;
    }

    public void setCurrentRelationshipInvestments(RelationshipInvestment[] currentRelationshipInvestments) {
        this.currentRelationshipInvestments = currentRelationshipInvestments;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }



}
