package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentUsersInfoBinding;

public class UsersInfoFragment extends Fragment {
    private FragmentUsersInfoBinding binding;

    public UsersInfoFragment() {
    }

    public static Fragment newInstance() {
        UsersInfoFragment fragment = new UsersInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}