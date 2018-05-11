package example.nano.pop_movie_stage1.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.nano.pop_movie_stage1.models.MovieItem;

/**
 * Created by ehab- on 4/15/2017.
 */

public class JsonParser {

    private static JsonParser mInstance;
    private static Context mContext;


    public static JsonParser getInstnace(Context context) {
        mContext = context;

        if (mInstance == null)
            mInstance = new JsonParser();

        return mInstance;
    }

    public ArrayList<MovieItem> fetchMovies(String response) {
        try {
            JSONObject jsonObject= new JSONObject(response);
            JSONArray resultsArr = jsonObject.getJSONArray("results");

            ArrayList<MovieItem> itemList = new ArrayList<>();

            for (int i = 0; i <resultsArr.length(); i++) {
                JSONObject item = resultsArr.getJSONObject(i);
                MovieItem newItem = new MovieItem();
                newItem.setPoster_path(item.getString("poster_path"));
                newItem.setOverview(item.getString("overview"));
                newItem.setVote_average(item.getString("vote_average"));
              //  Log.d("From Json release date",item.getString("release_date"));
                newItem.setBackdrop_path(item.getString("backdrop_path"));
                newItem.setTitle(item.getString("original_title"));
                newItem.setRelease_date(item.getString("release_date"));


                itemList.add(newItem);
            }
            return itemList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
