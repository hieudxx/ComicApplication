package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import android.os.Bundle;

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
import java.util.Arrays;

import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Users_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.serviceUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.DialogAddUsersBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentUsersListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UsersListFragment extends Fragment {

    private FragmentUsersListBinding binding;
    private DialogAddUsersBinding dialogBinding;
    private AlertDialog.Builder builder;
    private Retrofit retrofit;
    private serviceUsers serviceUsers;
    private ArrayList<Users> list;
    private Users_Item_Adapter adapter;

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
        dialogBinding = dialogBinding.inflate(inflater);

        list = new ArrayList<>();

        loadData();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceUsers = retrofit.create(hieudxph21411.fpoly.assignment_mob403_ph21411.api.serviceUsers.class);

        Call<ArrayList<Users>> call = serviceUsers.getAllUsers();
        call.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    for (Users users : list){
                        Log.e("tag_kiemTra",  users.get_id().toString());

                    }
                    adapter = new Users_Item_Adapter(getActivity(), list);
                    binding.rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();

            }
        });

        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(inflater, container);
            }
        });
        return binding.getRoot();
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
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
                if (username.isEmpty() || pass.isEmpty() || repass.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
                    validField(username, dialogBinding.tvUserName);
                    validField(pass, dialogBinding.tvPass);
                    validField(repass, dialogBinding.tvRePass);
                    validField(email, dialogBinding.tvEmail);
                    validField(fullname, dialogBinding.tvFullName);
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
                    users.setAvt("");

                    Call<Users> call = serviceUsers.postUsers(users);
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
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

    private void validField(String value, TextInputLayout field) {
        if (value.equals("")) {
            field.setError("Trường này không được để trống");
        } else {
            field.setError(null);
        }
    }

    private void loadData() {
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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        View view = null;
//        if (view != null) {
//            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
//            if (parentViewGroup != null) {
//                parentViewGroup.removeAllViews();
//            }
//        }
//    }
}