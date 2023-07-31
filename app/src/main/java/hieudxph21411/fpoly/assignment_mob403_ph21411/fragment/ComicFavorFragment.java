package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;

public class ComicFavorFragment extends Fragment {

    public ComicFavorFragment() {
    }

    public static Fragment newInstance() {
        ComicFavorFragment fragment = new ComicFavorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_favor, container, false);
    }
}