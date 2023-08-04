package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.LoginActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentUsersInfoBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersInfoFragment extends Fragment {
    private FragmentUsersInfoBinding binding;
    private Users users;

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
        users = new Users();
        getData();

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutView.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.GONE);
                binding.layoutEdit.setVisibility(View.VISIBLE);
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnCancel.setVisibility(View.VISIBLE);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutView.setVisibility(View.VISIBLE);
                binding.btnEdit.setVisibility(View.VISIBLE);
                binding.layoutEdit.setVisibility(View.GONE);
                binding.btnSave.setVisibility(View.GONE);
                binding.btnCancel.setVisibility(View.GONE);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = binding.edFullName.getText().toString();
                String pass = binding.edPass.getText().toString();
                String email = binding.edEmail.getText().toString();
                String avt = binding.edAvt.getText().toString();
                if (fullname.isEmpty() || pass.isEmpty() || email.isEmpty() || avt.isEmpty()) {
                    Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Users u = new Users();
                    u.setPass(pass);
                    u.setEmail(email);
                    u.setFullname(fullname);
                    u.setAvt(avt);
                    u.setRole(LoginActivity.shared.getInt("role", 1));
                    APIUsers.apiUsers.editById(LoginActivity.shared.getString("_id", ""), u).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            binding.layoutView.setVisibility(View.VISIBLE);
                            binding.btnEdit.setVisibility(View.VISIBLE);
                            binding.layoutEdit.setVisibility(View.GONE);
                            binding.btnSave.setVisibility(View.GONE);
                            binding.btnCancel.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    private void getData() {
        APIUsers.apiUsers.getOneUsers(LoginActivity.shared.getString("_id", "")).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    users = response.body();

                    Glide.with(getContext()).load(users.getAvt()).into(binding.imgAvt);
                    binding.tvName.setText(users.getFullname());
                    binding.tvSetName.setText(users.getFullname());
                    binding.tvUserName.setText(users.getUsername());
                    binding.tvSetUserName.setText(users.getUsername());
                    binding.tvFullName.setText(users.getFullname());
                    binding.tvEmail.setText(users.getEmail());
                    binding.edAvt.setText(users.getAvt());
                    if (users.getRole() == 1) {
                        binding.tvRole.setText("Người dùng");
                        binding.tvSetRole.setText("Người dùng");
                    } else {
                        binding.tvRole.setText("Admin");
                        binding.tvSetRole.setText("Admin");
                    }
                }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}