package hieudxph21411.fpoly.assignment_mob403_ph21411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.LoginActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Comic_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ActivityMainBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicListFragment;

public class MainActivity extends AppCompatActivity implements loadFragment {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        vì ta đã bỏ actionbar trong themes nên giờ phải set lại actionbar vào toolbar để có thẻ kéo nav từ bên trái
        setSupportActionBar(binding.tbMain);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

//        dùng để click vào icon menu bật ra nav
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.tbMain, R.string.navigation_open, R.string.navigation_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerLayout = binding.nav.getHeaderView(0);
        TextView tvName = headerLayout.findViewById(R.id.tvName);
        SharedPreferences shared = getSharedPreferences("PROFILE", MODE_PRIVATE);

        int role = shared.getInt("role", 1);

        String username = shared.getString("username", "");
        tvName.setText(username);

        if (role == 1) {
            Menu menu = binding.nav.getMenu();
            menu.findItem(R.id.nav_admin).setVisible(false);
        }
        Comic_Item_Adapter adapter = new Comic_Item_Adapter(this);
        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_logOut) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else if (item.getItemId() == R.id.nav_home) {
                    loadFragment(ComicListFragment.newInstance());
                }
                binding.drawerLayout.closeDrawer(binding.nav);
                binding.tbMain.setTitle(item.getTitle());

                return true;
            }
        });
    }

//    public void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frameMain, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}