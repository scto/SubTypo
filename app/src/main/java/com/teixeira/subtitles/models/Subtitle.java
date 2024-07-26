package com.teixeira.subtitles.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Subtitle implements Parcelable {
  private String startTime;
  private String endTime;
  private String text;

  public Subtitle(String startTime, String endTime, String text) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.text = text;
  }

  public Subtitle(Parcel parcel) {
    this(parcel.readString(), parcel.readString(), parcel.readString());
  }

  public String getStartTime() {
    return this.startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public Subtitle clone() {
    return new Subtitle(startTime, endTime, text);
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(startTime);
    parcel.writeString(endTime);
    parcel.writeString(text);
  }

  public static final Parcelable.Creator<Subtitle> CREATOR =
      new Parcelable.Creator<Subtitle>() {

        @Override
        public Subtitle createFromParcel(Parcel parcel) {
          return new Subtitle(parcel);
        }

        @Override
        public Subtitle[] newArray(int size) {
          return new Subtitle[size];
        }
      };
}
