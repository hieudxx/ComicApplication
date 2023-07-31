package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import android.os.Bundle;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        loadData();
        list.add(new Comic("tác giả", new String[]{"nội dung"},"tên","des",1,"https://nettruyen.live/public/images/comics/onepunch-man.jpg",200));
        list.add(new Comic("tác giả", new String[]{"nội dung"},"tên","des",1,"https://nettruyen.live/public/images/comics/onepunch-man.jpg",200));
        list.add(new Comic("tác giả", new String[]{"nội dung"},"tên","des",1,"https://nettruyen.live/public/images/comics/onepunch-man.jpg",200));

        getData();

        return binding.getRoot();
    }

    private void getData(){
        APIComic.apiComic.getAllComic().enqueue(new Callback<ArrayList<Comic>>() {
            @Override
            public void onResponse(Call<ArrayList<Comic>> call, Response<ArrayList<Comic>> response) {
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    list = response.body();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Comic>> call, Throwable t) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadData() {
        list = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);  // dạng grid và chia thành 2 cột, ở đây ta phải set vào dòng bên dưới nữa mới chạy đc
        layoutManager.setOrientation(RecyclerView.VERTICAL); // dạng vuốt ngang
        binding.rcv.setLayoutManager(layoutManager);
        adapter = new Comic_Item_Adapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
    }
}