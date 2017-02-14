package com.kostylevv.yama;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vkostylev on 14/02/2017.
 */

public class Movie {

        public Movie () {}

        public Movie (String title) {
            this.title = title;
            this.poster = null;
        }


        private int mData;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public static final Parcelable.Creator<Movie> CREATOR
                = new Parcelable.Creator<Movie>() {
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        private Movie(Parcel in) {
            mData = in.readInt();
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

        private String title;
        private String poster;
        private String overview;
        private double vote_average;
        private String releaseDate;


}
