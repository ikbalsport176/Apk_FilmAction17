
package com.example.aplikasi_reviewfilm.movieshow;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("moviedb")
    @Expose
    private List<Moviedb> moviedb = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -9222110105580588624L;

    protected Data(Parcel in) {
        in.readList(this.moviedb, (com.example.aplikasi_reviewfilm.movieshow.Moviedb.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param moviedb
     */
    public Data(List<Moviedb> moviedb) {
        super();
        this.moviedb = moviedb;
    }

    public List<Moviedb> getMoviedb() {
        return moviedb;
    }

    public void setMoviedb(List<Moviedb> moviedb) {
        this.moviedb = moviedb;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(moviedb);
    }

    public int describeContents() {
        return  0;
    }

}
