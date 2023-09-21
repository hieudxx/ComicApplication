package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import androidx.fragment.app.FragmentManager;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.LoginActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Cmt_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APICmt;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIComic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentComicDetailBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailFragment extends Fragment {
    private static FragmentComicDetailBinding binding;
    private static ArrayList<Cmt> list;
    private static Cmt_Item_Adapter adapter;
    private static Comic comic;
    static String idm;
    static String _idComic;
    private OnBackPressedDispatcher backPressedDispatcher;

    public static Context context;


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

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(binding.tbFrag);
//        ActionBar actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        setUpOnBackPress();

//        backPressedDispatcher = new OnBackPressedDispatcher();
//        backPressedDispatcher.addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                getActivity().getSupportFragmentManager().popBackStack();
//
//            }
//        });
//        OnBackPressedDispatcher actionBar = new OnBackPressedDispatcher();
//                getActivity().getOnBackPressedDispatcher();


        binding.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.btnAddCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                String time = df.format(c.getTime());

                String comicId = idm;
                String usersId = LoginActivity.shared.getString("_id", "");

                Cmt cmt = new Cmt();
                cmt.setTime(time);
                cmt.setContent(binding.edContent.getText().toString());

                APICmt.apiCmt.postCmt(cmt, comicId, usersId).enqueue(new Callback<Cmt>() {
                    @Override
                    public void onResponse(Call<Cmt> call, Response<Cmt> response) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        getData();
                        adapter.notifyDataSetChanged();
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<Cmt> call, Throwable t) {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loadFragment(ComicReadFragment.newInstance(_idComic));

            }
        });
        final int[] isExpanded = {1};

        binding.tvDes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isExpanded[0] == 1) {
                    binding.tvDes.setMaxLines(Integer.MAX_VALUE);
                    isExpanded[0] = 2;
                } else {
                    binding.tvDes.setMaxLines(3);
                    isExpanded[0] = 1;
                }
            }
        });
        return binding.getRoot();
    }

//    private void setUpOnBackPress() {
//        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                if (isEnabled()) {
//                    setEnabled(false);
//                    requireActivity().onBackPressed();
//                }
//            }
//        });
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();
        super.onViewCreated(view, savedInstanceState);
    }

    public static void getData() {
        APIComic.apiComic.getOneComic(idm).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (response.isSuccessful()) {
                    comic = response.body();

                    binding.tvName.setText(comic.getName());
                    binding.tvTime.setText("Năm xuất bản: " + comic.getDate());
                    Glide.with(context).load(comic.getCover()).into(binding.imgCover);
                    binding.tvAuthor.setText("Tác giả: " + comic.getAuthor());
                    binding.tvChapter.setText("Chapter: " + comic.getChapter());
                    binding.tvDes.setText(comic.getDes());
                    _idComic = comic.get_id();
                    list = new ArrayList<>(Arrays.asList(comic.getCmt()));

                    adapter = new Cmt_Item_Adapter(context, list);
                    binding.rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rcv.addItemDecoration(itemDecoration);
        binding.rcv.setAdapter(new Cmt_Item_Adapter(getContext(), list));
    }
}