package com.kostylevv.yama;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.graphics.Palette;

/**
 * Created by vkostylev on 14/02/2017.
 */

public class Movie implements Parcelable {

        public Movie () {}

        public Movie (String title) {
            this.title = title;
            this.poster = null;
        }


        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Movie obj data: ");
            if (getTitle() != null) builder.append(getTitle());
            if (getTitle() != null) builder.append(getPoster());
            return builder.toString();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getVote_average() {
            return Double.toString(vote_average);
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getBackgroundColor() {return this.backgroundColor;}

        public int getTextColor() {return this.textColor;}

        public void setBackgroundColor(int color) {
            this.backgroundColor = color;
        }

        public void setTextColor(int color) {
            this.textColor = color;
        }

        private String title;
        private String poster;
        private String overview;
        private double vote_average;
        private String releaseDate;
        private int backgroundColor;
        private int textColor;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.releaseDate = in.readString();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
