package example.nano.pop_movie_stage1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import example.nano.pop_movie_stage1.utilities.JsonParser;
import example.nano.pop_movie_stage1.utilities.NetHelper;
import example.nano.pop_movie_stage1.utilities.Urls;


public class MainFragment extends Fragment  implements AdapterView.OnItemClickListener{

    private String movieSortBy;
    private NetHelper mNetHelper;
    Context mContext;
    List<MovieItem> allItems;
    MovieAdapter movieAdapter;
    public MainFragment() {
        // Required empty public constructor
    }

    public interface Callback {
        void onItemSelected(MovieItem movie);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);
        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        action_sort_by_popularity.setChecked(true);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                movieSortBy = Urls.SORT_BY_POPULAR;
                updateMovies(movieSortBy);
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                movieSortBy = Urls.SORT_BY_TOP_RATED;
                updateMovies(movieSortBy);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNetHelper= NetHelper.getInstance(this.getActivity());
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.fragment_grid);
        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieItem>());
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem movie = (MovieItem) movieAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movie);
            }
        });
        mContext=this.getActivity();

        return view ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        updateMovies(Urls.SORT_BY_POPULAR);
        super.onViewCreated(view, savedInstanceState);
    }

    void updateMovies(String url)
    {
        mNetHelper.fetchMovies(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Request Response", response);
                allItems= JsonParser.getInstnace(mContext).fetchMovies(response);
                if(allItems.size()>0) {
                    movieAdapter.setData(allItems);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hello", error.toString() + " " + error.getMessage());
                Toast.makeText(getActivity(),  R.string.check_internet_connection,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
