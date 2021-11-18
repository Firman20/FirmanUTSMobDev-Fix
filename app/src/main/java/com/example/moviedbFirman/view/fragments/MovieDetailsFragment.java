package com.example.moviedbFirman.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedbFirman.R;
import com.example.moviedbFirman.helper.Const;
import com.example.moviedbFirman.model.Movies;
import com.example.moviedbFirman.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView lbl_movie_title, lbl_movie_releaseDate, lbl_movie_synopsis, lbl_movie_genre, lbl_movie_duration,
            lbl_movie_tagline ,lbl_movie_rating,lbl_popularity;
    private MovieViewModel movieViewModel;
    private ImageView img_details, backdrop_detail_fragment;
    private ImageView backdrop_path;
    private String movie_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        String movieId = getArguments().getString("movieId");

        lbl_movie_tagline = view.findViewById(R.id.lbl_movie_tagline);
        img_details = view.findViewById(R.id.img_details);
        backdrop_detail_fragment = view.findViewById(R.id.backdrop_detail_fragment);
        lbl_movie_title = view.findViewById(R.id.lbl_movie_title);
        lbl_movie_releaseDate = view.findViewById(R.id.lbl_movie_releaseDate);
        lbl_movie_synopsis = view.findViewById(R.id.lbl_movie_synopsis);
        lbl_movie_genre = view.findViewById(R.id.lbl_movie_genre);
        lbl_movie_duration = view.findViewById(R.id.lbl_movie_duration);
        lbl_movie_rating = view.findViewById(R.id.lbl_movie_rating);
        lbl_popularity = view.findViewById(R.id.lbl_popularity);

       // backdrop_path = view.findViewById(R.id.lbl_movie_rating);
        movieViewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        movieViewModel.getMovieById(movieId);
        movieViewModel.getResultGetMovieById().observe(getActivity(), showMovieDetails);
        return view;

}

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String releaseDate = movies.getRelease_date();
            String synopsis = movies.getOverview();
            String duration = String.valueOf(movies.getRuntime());
            String rating = String.valueOf(movies.getVote_average());
            String genre = "";
            String tagline = String.valueOf(movies.getTagline());
            String popularity = String.valueOf(movies.getPopularity());
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();

            Glide.with(MovieDetailsFragment.this).load(backdrop_path)
                    .into(backdrop_detail_fragment);
            Glide.with(MovieDetailsFragment.this).load(img_path).into(img_details);
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    genre += movies.getGenres().get(i).getName();
                } else {
                    genre += movies.getGenres().get(i).getName() + ", ";
                }
            }

            lbl_movie_title.setText(title);
            lbl_movie_releaseDate.setText(releaseDate);
            lbl_movie_synopsis.setText(synopsis);
            lbl_movie_duration.setText(duration);
            lbl_movie_rating.setText(rating);
            lbl_movie_genre.setText(genre);
            lbl_movie_tagline.setText(tagline);
            lbl_popularity.setText(popularity);
        }
    };


}