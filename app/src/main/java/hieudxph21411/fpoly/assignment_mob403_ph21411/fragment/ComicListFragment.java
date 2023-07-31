package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Comic_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIComic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicListFragment extends Fragment {
    private FragmentComicListBinding binding;
    private ArrayList<Comic> list;
    private Comic_Item_Adapter adapter;

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

        list = new ArrayList<>();
        getData();
        loadData();

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        APIComic.apiComic.getAllComic().enqueue(new Callback<ArrayList<Comic>>() {
            @Override
            public void onResponse(Call<ArrayList<Comic>> call, Response<ArrayList<Comic>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comic>> call, Throwable t) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);  // dạng grid và chia thành 2 cột, ở đây ta phải set vào dòng bên dưới nữa mới chạy đc
        layoutManager.setOrientation(RecyclerView.VERTICAL); // dạng vuốt ngang
        binding.rcv.setLayoutManager(layoutManager);
        adapter = new Comic_Item_Adapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}