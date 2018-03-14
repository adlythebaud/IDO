package com.mycabbages.teamavatar.ido;

import android.app.Notification;

import java.util.Date;

/**
 * Created by adlythebaud on 3/13/18.
 */

class PushNotification {

    private String title;
    private Date dateToSend;
    private Notification notification;

    public PushNotification(String title, Date dateToSend, Notification notification) {
        this.title = title;
        this.dateToSend = dateToSend;
        this.notification = notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateToSend() {
        return dateToSend;
    }

    public void setDateToSend(Date dateToSend) {
        this.dateToSend = dateToSend;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


}
