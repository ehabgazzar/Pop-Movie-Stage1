package example.nano.pop_movie_stage1.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ehab- on 4/16/2017.
 */

public class MovieItem implements Parcelable {
   private String title;
    private String PosterW;
    private String poster;
    private String overview;
    private String rate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;


    protected MovieItem(Parcel in) {
        title = in.readString();
        poster = in.readString();
        PosterW = in.readString();
        overview = in.readString();
        rate = in.readString();
        date = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterW() {
        return PosterW;
    }

    public void setPosterW(String url) {

        this.PosterW = url;
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


        public MovieItem()
        {

        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(PosterW);
        dest.writeString(overview);
        dest.writeString(rate);
        dest.writeString(date);
    }
}
