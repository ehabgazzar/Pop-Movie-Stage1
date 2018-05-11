package example.nano.pop_movie_stage1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.nano.pop_movie_stage1.models.MovieItem;

import static example.nano.pop_movie_stage1.utilities.Urls.POSTER;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    public static final String TAG = DetailsFragment.class.getSimpleName();

    static final String DETAIL_MOVIE = "DETAIL_MOVIE";

    private MovieItem mMovie;

    @BindView(R.id.detail_title) TextView mTitleView;
    @BindView(R.id.detail_overview) TextView mOverviewView;

    @BindView(R.id.detail_date) TextView mDateView;
    @BindView(R.id.detail_vote_average) TextView mVoteAverageView;
    @BindView(R.id.detail_image) ImageView mImageView;
    @BindView(R.id.detail_layout) ScrollView mDetailLayout;



    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovie = arguments.getParcelable(DetailsFragment.DETAIL_MOVIE);
        }

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(rootView);
        mDetailLayout = (ScrollView) rootView.findViewById(R.id.detail_layout);

        if (mMovie != null) {
            mDetailLayout.setVisibility(View.VISIBLE);
        } else {
            mDetailLayout.setVisibility(View.INVISIBLE);
        }

        mImageView = (ImageView) rootView.findViewById(R.id.detail_image);

        mTitleView = (TextView) rootView.findViewById(R.id.detail_title);
        mOverviewView = (TextView) rootView.findViewById(R.id.detail_overview);
        mDateView = (TextView) rootView.findViewById(R.id.detail_date);
        mVoteAverageView = (TextView) rootView.findViewById(R.id.detail_vote_average);
        if (mMovie != null) {
            String image_url = POSTER+"342"+mMovie.getBackdrop_path() ;

            Picasso.get().load(image_url).into(mImageView);

            mTitleView.setText(mMovie.getTitle());
            mOverviewView.setText(mMovie.getOverview());

            Log.d("Relase Date",mMovie.getRelease_date());
            mDateView.setText(mMovie.getRelease_date());
            mVoteAverageView.setText(mMovie.getVote_average());
        }

        return rootView;
    }

}
