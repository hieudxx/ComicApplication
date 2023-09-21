package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Comic_Item_Read_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIComic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicReadBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicReadFragment extends Fragment {
    private FragmentComicReadBinding binding;
    private static String _id;
    private Comic comic;
    private ArrayList<String> list;
    private Comic_Item_Read_Adapter adapter;

    public ComicReadFragment() {

    }

    public static Fragment newInstance(String _idComic) {
        ComicReadFragment fragment = new ComicReadFragment();
        _id = _idComic;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComicReadBinding.inflate(inflater, container, false);
        comic = new Comic();
        getData();


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(MainActivity.binding.tbMain);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        return binding.getRoot();
    }

    private void getData() {
        APIComic.apiComic.getReadComic(_id).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()) {
                    comic = response.body();
                    list = new ArrayList<>(Arrays.asList(comic.getContent()));
                }
                adapter = new Comic_Item_Read_Adapter(getContext(), list);
                binding.rcv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}