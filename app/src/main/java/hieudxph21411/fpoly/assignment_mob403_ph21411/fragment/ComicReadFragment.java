package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;

public class ComicReadFragment extends Fragment {

    public ComicReadFragment() {

    }
    public static Fragment newInstance() {
        ComicReadFragment fragment = new ComicReadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_read, container, false);
    }
}