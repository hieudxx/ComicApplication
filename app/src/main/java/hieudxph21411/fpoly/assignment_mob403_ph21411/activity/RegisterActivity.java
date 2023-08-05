package hieudxph21411.fpoly.assignment_mob403_ph21411.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.API;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ActivityRegisterBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.myStatusListener;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RequestQueue queue;
    private JsonObjectRequest request;
    private myStatusListener statusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(getApplicationContext());
        setSupportActionBar(binding.tbRegis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        validForm();
        registerUsers();
        statusListener = new myStatusListener(new myStatusListener.OnStatus() {
            @Override
            public void onStatus(String message) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validForm() {
        binding.edtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvFullName.setError("Vui lòng nhập tên");
                } else {
                    binding.tvFullName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvUserName.setError("Vui lòng nhập tài khoản");
                } else {
                    binding.tvUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvPass.setError("Vui lòng nhập mật khẩu");
                } else {
                    binding.tvPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvRePass.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    binding.tvRePass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvEmail.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    binding.tvEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtAvt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tvAvt.setError("Vui lòng nhập lại mật khẩu");
                } else {
                    binding.tvAvt.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void registerUsers() {
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher();
                String username = binding.tvUserName.getEditText().getText().toString();
                String pass = binding.tvPass.getEditText().getText().toString();
                String repass = binding.tvRePass.getEditText().getText().toString();
                String email = binding.tvEmail.getEditText().getText().toString();
                String fullname = binding.tvFullName.getEditText().getText().toString();
                String avt = binding.tvAvt.getEditText().getText().toString();
                if (username.isEmpty() || pass.isEmpty() || repass.isEmpty() || fullname.isEmpty() || email.isEmpty() || avt.isEmpty()) {

                    validField(username, binding.tvUserName);
                    validField(pass, binding.tvPass);
                    validField(repass, binding.tvRePass);
                    validField(email, binding.tvEmail);
                    validField(fullname, binding.tvFullName);
                    validField(avt, binding.tvAvt);

                    if (!pass.equals(repass)) {
                        binding.tvRePass.setError("Vui lòng nhập lại mật khẩu");
                    } else {
                        binding.tvRePass.setError(null);
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("username", username);
                        object.put("pass", pass);
                        object.put("email", email);
                        object.put("fullname", fullname);
                        object.put("avt",avt);
                        request = new JsonObjectRequest(
                                Request.Method.POST,
                                API.API_POST_REGISTER_USERS,
                                object,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }
                                }, statusListener);
                        queue.add(request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void validField(String value, TextInputLayout field) {
        if (value.isEmpty()) {
            field.setError("Trường này không được để trống");
        } else {
            field.setError(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}