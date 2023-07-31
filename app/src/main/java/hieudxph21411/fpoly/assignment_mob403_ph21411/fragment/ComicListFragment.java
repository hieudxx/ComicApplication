package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private Retrofit retrofit;
    private APIComic serviceComic;
//    public static final Users
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

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceComic = retrofit.create(APIComic.class);
        try {
            Call<Comic> call = serviceComic.getAllComic();
            call.enqueue(new Callback<Comic>() {
                @Override
                public void onResponse(Call<Comic> call, Response<Comic> response) {

                }

                @Override
                public void onFailure(Call<Comic> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadData();
//        JsonObjectRequest objectRequest = new JsonObjectRequest()
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
//        adapter = new Comic_Item_Adapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
    }
}