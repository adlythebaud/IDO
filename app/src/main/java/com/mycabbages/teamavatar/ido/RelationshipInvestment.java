package com.mycabbages.teamavatar.ido;

/**
 * Created by adlythebaud on 3/13/18.
 */

class RelationshipInvestment {

    enum RelationshipInvestmentType {
        daily,
        weekly,
        monthly
    }

    enum RelationshipInvestmentPriority {
        low,
        high
    }

    private String title;
    private RelationshipInvestmentType type;
    private PushNotification pushNotification;
    private String id;
    private int numTimesCompleted;

    public RelationshipInvestment(String title, RelationshipInvestmentType type,
                                  PushNotification pushNotification) {
        this.title = title;
        this.type = type;
        this.pushNotification = pushNotification;
    }

    public RelationshipInvestment(String title, RelationshipInvestmentType type,
                                  PushNotification pushNotification, String id) {
        this.title = title;
        this.type = type;
        this.pushNotification = pushNotification;
        this.id = id;
    }

    public RelationshipInvestment(String title, RelationshipInvestmentType type,
                                  PushNotification pushNotification, String id,
                                  int numTimesCompleted) {
        this.title = title;
        this.type = type;
        this.pushNotification = pushNotification;
        this.id = id;
        this.numTimesCompleted = numTimesCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RelationshipInvestmentType getType() {
        return type;
    }

    public void setType(RelationshipInvestmentType type) {
        this.type = type;
    }

    public PushNotification getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(PushNotification pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumTimesCompleted() {
        return numTimesCompleted;
    }

    public void setNumTimesCompleted(int numTimesCompleted) {
        this.numTimesCompleted = numTimesCompleted;
    }
}
