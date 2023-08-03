package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.LoginActivity;
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

        Glide.with(this).load(LoginActivity.shared.getString("avt", "")).into(binding.imgAvt);
        binding.tvName.setText(LoginActivity.shared.getString("fullname",""));
        binding.tvSetName.setText(LoginActivity.shared.getString("fullname",""));
        binding.tvUserName.setText(LoginActivity.shared.getString("username",""));
        binding.tvSetUserName.setText(LoginActivity.shared.getString("username",""));
        binding.tvFullName.setText(LoginActivity.shared.getString("fullname",""));
        binding.tvEmail.setText(LoginActivity.shared.getString("email",""));
        if (LoginActivity.shared.getInt("role",1) == 1){
            binding.tvRole.setText("Người dùng");
            binding.tvSetRole.setText("Người dùng");
        } else {
            binding.tvRole.setText("Admin");
            binding.tvSetRole.setText("Admin");

        }

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.layoutEdit.getVisibility() == View.VISIBLE){
                    binding.layoutView.setVisibility(View.VISIBLE);
                    binding.layoutEdit.setVisibility(View.GONE);
                    binding.btnEdit.setText("Edit");
                } else {
                    binding.layoutView.setVisibility(View.GONE);
                    binding.layoutEdit.setVisibility(View.VISIBLE);
                    binding.btnEdit.setText("Save");
                }

            }
        });

        return binding.getRoot();

    }
}