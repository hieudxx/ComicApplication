package hieudxph21411.fpoly.assignment_mob403_ph21411.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.API;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ActivityLoginBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.myStatusListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private RequestQueue queue;
    private JsonObjectRequest request;
    private String msg = "";
    private myStatusListener statusListener;
    public static SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        shared = getSharedPreferences("PROFILE", MODE_PRIVATE);
        boolean isCheck = shared.getBoolean("isCheck", false);
        if (isCheck) {
            binding.edUserName.setText(shared.getString("username", ""));
            binding.edPass.setText(shared.getString("pass", ""));
            binding.chkLogin.setChecked(isCheck);
        }
        statusListener = new myStatusListener(new myStatusListener.OnStatus() {
            @Override
            public void onStatus(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        binding.tvDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Tính năng chưa phát triển", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {
        try {
            JSONObject object = new JSONObject();
            object.put("username", binding.edUserName.getText().toString());
            object.put("pass", binding.edPass.getText().toString());
            request = new JsonObjectRequest(
                    Request.Method.POST,
                    API.API_POST_LOGIN,
                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            try {
                                JSONObject users = response.getJSONObject("users");
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("username", users.getString("username"));
                                editor.putString("_id", users.getString("_id"));
                                editor.putString("pass", binding.edPass.getText().toString());
                                editor.putString("email", users.getString("email"));
                                editor.putString("fullname", users.getString("fullname"));
                                editor.putInt("role", users.getInt("role"));
                                editor.putString("avt", users.getString("avt"));
                                editor.putBoolean("isCheck", binding.chkLogin.isChecked());
                                editor.apply();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, statusListener
            );
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}