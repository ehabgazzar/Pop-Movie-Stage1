package example.nano.pop_movie_stage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ehab- on 4/16/2017.
 */

public class MovieAdapter extends BaseAdapter {


    private final Context moiveContext;
    private final LayoutInflater movieInflater;

    private final MovieItem movie = new MovieItem();

    private List<MovieItem> movieObjects;

    public MovieAdapter(Context context, List<MovieItem> objects) {
        moiveContext = context;
        movieInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        movieObjects = objects;
    }

    public Context getContext() {
        return moiveContext;
    }

    public void add(MovieItem object) {
            movieObjects.add(object);
      }

    public void setData(List<MovieItem> data) {
        clear();
        for (MovieItem movie : data) {
            add(movie);
        }
        notifyDataSetChanged();
    }

    public void clear() {

            movieObjects.clear();

        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return movieObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return movieObjects.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = movieInflater.inflate(R.layout.movie_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final MovieItem movie = (MovieItem) getItem(position);

        String image_url = "http://image.tmdb.org/t/p/w185" + movie.getPoster();
        viewHolder = (ViewHolder) view.getTag();
        Picasso.with(getContext()).load(image_url).into(viewHolder.imageView);
        return view;
    }


    public static class ViewHolder {
        public final ImageView imageView;


        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_image);

        }
    }
}
