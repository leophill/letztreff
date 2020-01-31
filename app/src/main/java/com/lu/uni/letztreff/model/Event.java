package com.lu.uni.letztreff.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

import android.app.DatePickerDialog;

/**
 * Event POJO.
 */
@IgnoreExtraProperties
public class Event {

    public static final String FIELD_NAME = "Name";
    public static final String FIELD_TYPE = "Type";
    public static final String FIELD_PLACE = "Place";
    public static final String FIELD_PHOTO = "Photo";
    public static final String FIELD_STARTTME = "StartTime";
    public static final String FIELD_ENDTIME = "EndTime";
    public static final String FIELD_NUMRATINGS = "NumRatings";
    public static final String FIELD_AVGRATINGS = "AvgRating";

    private String Id;
    private String Uid;
    private String Name;
    private String Type;
    private String Place;
    private String Photo;
    private String StartTime;
    private String EndTime;
    private int NumRatings;
    private double AvgRating;

    public void Event(String Id, String Uid, String Name, String Type, String Category, String Photo,
                      String StartTime, String EndTime, int NumRatings, double AvgRating) {
        this.Id = Id;
        this.Id = Uid;
        this.Name = Name;
        this.Type = Type;
        this.Place = Place;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.NumRatings = NumRatings;
        this.AvgRating = AvgRating;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {this.Id = Id;  }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {this.Uid = Uid;  }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = Name;
    }

    public String getType() {return Type; }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String Place) {
        this.Place = Place;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public int getNumRatings() {
        return NumRatings;
    }

    public void setNumRatings(int NumRatings) {
        this.NumRatings = NumRatings;
    }

    public double getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(double AvgRating) {
        this.AvgRating = AvgRating;
    }
}
