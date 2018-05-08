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

    private String id;
    private int numTimesCompleted;

    public RelationshipInvestment(String title, RelationshipInvestmentType type) {
        this.title = title;
        this.type = type;

    }

    public RelationshipInvestment(String title, RelationshipInvestmentType type, String id) {
        this.title = title;
        this.type = type;
        this.id = id;
    }

    public RelationshipInvestment(String title, RelationshipInvestmentType type, String id,
                                  int numTimesCompleted) {
        this.title = title;
        this.type = type;
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
