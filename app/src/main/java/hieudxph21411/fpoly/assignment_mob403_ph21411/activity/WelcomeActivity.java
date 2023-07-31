package hieudxph21411.fpoly.assignment_mob403_ph21411.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView imgBook = findViewById(R.id.imgLogo);
        Glide.with(this).load(R.drawable.book).into(imgBook);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}