package hieudxph21411.fpoly.assignment_mob403_ph21411.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hieudxph21411.fpoly.assignment_mob403_ph21411.adapter.Author_Item_Adapter;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIAuthor;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.FragmentAuthorListBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Author;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AuthorListFragment extends Fragment {
    private static FragmentAuthorListBinding binding;
    private static List<Author> listAuthor;
    private static ArrayList<Comic> listComic;
    private static Author_Item_Adapter adapter;
    public static Context context;

    public AuthorListFragment() {
    }

    public static Fragment newInstance() {
        AuthorListFragment fragment = new AuthorListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthorListBinding.inflate(inflater, container, false);
        loadData();
        getData();

        binding.fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();
        super.onViewCreated(view, savedInstanceState);
    }

    public static void getData() {
        APIAuthor.apiAuthor.getAllAuthor().enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                if (response.isSuccessful()) {
                    listAuthor = (List<Author>) response.body();
//                    for (Author a : listAuthor) {
//                        listComic = new ArrayList<>(Arrays.asList(a.getComic()));
//                    }
                    adapter = new Author_Item_Adapter(context, listAuthor);
                    binding.rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                if (!call.isExecuted()) {
                    Log.d("Error", "Lỗi kết nối mạng");
                }
                int statusCode = -1;
                if (t instanceof HttpException) {
                    HttpException httpException = (HttpException) t;
                    statusCode = httpException.code();

                    if (statusCode == 401) {
                        Log.d("Error", "Lỗi Authorization");
                    }
                }
                // Đóng Call
                call.cancel();
            }
        });
    }

    private void loadData() {
        listAuthor = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rcv.addItemDecoration(itemDecoration);
        adapter = new Author_Item_Adapter(getContext(), listAuthor);
        binding.rcv.setAdapter(adapter);
    }
}