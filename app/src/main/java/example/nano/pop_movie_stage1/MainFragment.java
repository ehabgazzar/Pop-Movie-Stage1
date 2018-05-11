package example.nano.pop_movie_stage1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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

import example.nano.pop_movie_stage1.models.MovieItem;
import example.nano.pop_movie_stage1.utilities.JsonParser;
import example.nano.pop_movie_stage1.utilities.NetHelper;
import example.nano.pop_movie_stage1.utilities.Urls;


public class MainFragment extends Fragment  implements AdapterView.OnItemClickListener{

    private String movieSortBy;
    private NetHelper mNetHelper;
    Context mContext;
    ArrayList<MovieItem> allItems;
    MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

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
         recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        allItems=new ArrayList<>();
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
                movieAdapter = new MovieAdapter(getActivity(),allItems , new ClickListener() {
                    @Override
                    public void onPositionClicked(int position) {
                        MovieItem movie = (MovieItem) allItems.get(position);
                        ((Callback) getActivity()).onItemSelected(movie);
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());


//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                        linearLayoutManager.getOrientation());
//                recyclerView.addItemDecoration(dividerItemDecoration);

                recyclerView.setAdapter(movieAdapter);


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
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
