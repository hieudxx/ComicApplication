package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicDetailBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicReadBinding;

public class ComicReadFragment extends Fragment {
    private FragmentComicReadBinding binding;
    public ComicReadFragment() {

    }
    public static Fragment newInstance() {
        ComicReadFragment fragment = new ComicReadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComicReadBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}