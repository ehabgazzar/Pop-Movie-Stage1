package example.nano.pop_movie_stage1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.nano.pop_movie_stage1.models.MovieItem;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment;
        fragment = new MainFragment();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(MovieItem movie) {

            Intent intent = new Intent(this, DetailsActivity.class)
                    .putExtra(DetailsFragment.DETAIL_MOVIE, movie);
            startActivity(intent);

    }
}
