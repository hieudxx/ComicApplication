package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Comic_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;


public class ComicListFragment extends Fragment {
    private FragmentComicListBinding binding;
    private ArrayList<Comic> list;

    public ComicListFragment() {
    }

    public static Fragment newInstance() {
        ComicListFragment fragment = new ComicListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComicListBinding.inflate(inflater, container, false);

        loadData();

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(binding.tbListComic);
//        ActionBar actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        return binding.getRoot();
    }

    private void loadData() {
        Comic_Item_Adapter adapter = new Comic_Item_Adapter(getContext(), list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);  // dạng grid và chia thành 2 cột, ở đây ta phải set vào dòng bên dưới nữa mới chạy đc
        layoutManager.setOrientation(RecyclerView.VERTICAL); // dạng vuốt ngang
        binding.rcv.setLayoutManager(layoutManager);
        binding.rcv.setAdapter(adapter);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getActivity().onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}