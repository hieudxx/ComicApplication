package hieudxph21411.fpoly.assignment_mob403_ph21411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.LoginActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ActivityMainBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.HeaderLayoutBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicFavorFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicListFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.UsersInfoFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.UsersListFragment;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;
    public static ActionBar actionBar;
    static FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        loadFragment(ComicListFragment.newInstance());

//        vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(binding.tbMain);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

//        dùng để click vào icon menu bật ra nav
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.tbMain, R.string.navigation_open, R.string.navigation_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences shared = getSharedPreferences("PROFILE", MODE_PRIVATE);

        View headerLayout = binding.nav.getHeaderView(0);
        HeaderLayoutBinding headerBinding = HeaderLayoutBinding.bind(headerLayout);

        headerBinding.tvFullName.setText(shared.getString("fullname", ""));
        headerBinding.tvUserName.setText(shared.getString("username", ""));
        Glide.with(this).load(shared.getString("avt", "")).into(headerBinding.imgAvt);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(UsersInfoFragment.newInstance());
                binding.drawerLayout.closeDrawer(binding.nav);
                binding.tbMain.setTitle("Thông tin cá nhân");
            }
        });


        if ((shared.getInt("role", 1)) == 1) {
            Menu menu = binding.nav.getMenu();
            menu.findItem(R.id.nav_admin).setVisible(false);
        }
        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_logOut) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else if (item.getItemId() == R.id.nav_home) {
                    loadFragment(ComicListFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_users) {
                    loadFragment(UsersListFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_favor) {
                    loadFragment(ComicFavorFragment.newInstance());
                } else if (item.getItemId() == R.id.nav_info) {
                    loadFragment(UsersInfoFragment.newInstance());
                }
//                else if (item.getItemId() == R.id.nav_author){
//                    loadFragment(AuthorListFragment.newInstance());
//                }
                binding.drawerLayout.closeDrawer(binding.nav);
                binding.tbMain.setTitle(item.getTitle());
                return true;
            }
        });
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

    public static void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}