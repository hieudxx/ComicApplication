package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Cmt_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIComic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicDetailBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailFragment extends Fragment {
    private FragmentComicDetailBinding binding;
    private ArrayList<Cmt> list;
    private Cmt_Item_Adapter adapter;
    private Comic comic;
    static String idm;

    public ComicDetailFragment() {
    }

    public static Fragment newInstance(String id) {
        ComicDetailFragment fragment = new ComicDetailFragment();
        idm = id;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComicDetailBinding.inflate(inflater, container, false);
        comic = new Comic();
        getData();
        loadData();

        binding.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loadFragment(ComicListFragment.newInstance());
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        APIComic.apiComic.getOneComic(idm).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()) {
                    comic = response.body();

                    binding.tvName.setText(comic.getName());
                    binding.tvTime.setText("Năm xuất bản: " + comic.getDate());
                    Glide.with(getContext()).load(comic.getCover()).into(binding.imgCover);
                    binding.tvAuthor.setText("Tác giả: " + comic.getAuthor());
                    binding.tvChapter.setText("Chapter: " + comic.getChapter());
                    binding.tvDes.setText(comic.getDes());

                    list = new ArrayList<>(Arrays.asList(comic.getCmt()));

                    Log.e("tag_kiemTra", list.toString());

                    adapter = new Cmt_Item_Adapter(getContext(), list);
                    binding.rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rcv.addItemDecoration(itemDecoration);
        binding.rcv.setAdapter(new Cmt_Item_Adapter(getContext(), list));
    }
}