package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.RegisterActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Users_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.serviceUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.DialogAddUsersBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentUsersListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class UsersListFragment extends Fragment {
    private static FragmentUsersListBinding binding;
    private DialogAddUsersBinding dialogBinding;
    private AlertDialog.Builder builder;
    private static ArrayList<Users> list;
    private static Users_Item_Adapter adapter;
    public static Context context;

    public UsersListFragment() {
    }

    public static Fragment newInstance() {
        UsersListFragment fragment = new UsersListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersListBinding.inflate(inflater, container, false);

        loadData();
        getData();

        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(inflater, container);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();
        super.onViewCreated(view, savedInstanceState);
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
        dialogBinding = dialogBinding.inflate(inflater, container, false);
        builder.setCancelable(false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        validForm();

        dialogBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = dialogBinding.tvUserName.getEditText().getText().toString();
                String pass = dialogBinding.tvPass.getEditText().getText().toString();
                String repass = dialogBinding.tvRePass.getEditText().getText().toString();
                String email = dialogBinding.tvEmail.getEditText().getText().toString();
                String fullname = dialogBinding.tvFullName.getEditText().getText().toString();
                String avt = dialogBinding.tvAvt.getEditText().getText().toString();
                if (username.isEmpty() || pass.isEmpty() || repass.isEmpty() || fullname.isEmpty() || email.isEmpty() || avt.isEmpty()) {
                    RegisterActivity.validField(username,dialogBinding.tvUserName);
                    RegisterActivity.validField(username, dialogBinding.tvUserName);
                    RegisterActivity.validField(pass, dialogBinding.tvPass);
                    RegisterActivity.validField(repass, dialogBinding.tvRePass);
                    RegisterActivity.validField(email, dialogBinding.tvEmail);
                    RegisterActivity.validField(fullname, dialogBinding.tvFullName);
                    RegisterActivity.validField(avt, dialogBinding.tvAvt);
                    if (!pass.equals(repass)) {
                        dialogBinding.tvRePass.setError("Vui lòng nhập lại mật khẩu");
                    } else {
                        dialogBinding.tvRePass.setError(null);
                    }
                } else {
                    Users users = new Users();
                    users.setUsername(username);
                    users.setPass(pass);
                    users.setEmail(email);
                    users.setFullname(fullname);
                    users.setAvt(avt);
                    serviceUsers.apiUsers.postUsers(users).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                adapter.notifyDataSetChanged();
                                loadData();
                                alertDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            t.printStackTrace();
                            if (!call.isExecuted()) {
                                Log.d("Error", "Lỗi kết nối mạng");
                            }
                            int statusCode = -1;
                            if (t instanceof HttpException) {
                                HttpException httpException = (HttpException) t;
                                statusCode = httpException.code();

                                if (statusCode == 401) {
                                    Log.d("Error", "Lỗi Authorization");
                                }
                            }
                            // Đóng Call
                            call.cancel();
                        }
                    });
                }
            }
        });

        dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public static void getData() {
        serviceUsers.apiUsers.getAllUsers().enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                }
                adapter = new Users_Item_Adapter(context, list);
                binding.rcv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {
                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void loadData() {
        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rcv.addItemDecoration(itemDecoration);
        adapter = new Users_Item_Adapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
    }

    private void validForm() {
        dialogBinding.edtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    dialogBinding.tvFullName.setError("Vui lòng nhập tên");
                } else {
                    dialogBinding.tvFullName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        dialogBinding.edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    dialogBinding.tvUserName.setError("Vui lòng nhập tài khoản");
                } else {
                    dialogBinding.tvUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        dialogBinding.edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    dialogBinding.tvPass.setError("Vui lòng nhập mật khẩu");
                } else {
                    dialogBinding.tvPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        dialogBinding.edtRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    dialogBinding.tvRePass.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    dialogBinding.tvRePass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dialogBinding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    dialogBinding.tvEmail.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    dialogBinding.tvEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

}