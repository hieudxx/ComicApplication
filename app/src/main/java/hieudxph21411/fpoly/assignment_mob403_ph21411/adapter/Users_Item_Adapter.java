package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.serviceUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ComicItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.UsersItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Users_Item_Adapter extends RecyclerView.Adapter<Users_Item_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Users> list;
    private serviceUsers serviceUsers;
    private Retrofit retrofit;

    public Users_Item_Adapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UsersItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.users_item_rcv, parent, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceUsers = retrofit.create(hieudxph21411.fpoly.assignment_mob403_ph21411.api.serviceUsers.class);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvUserName.setText(list.get(position).getUsername());
        holder.binding.tvEmail.setText(list.get(position).getEmail());
        holder.binding.tvFullName.setText(list.get(position).getFullname());
        if (list.get(position).getRole() == 1) {
            holder.binding.tvRole.setText("Chức vụ: Người dùng");
        } else {
            holder.binding.tvRole.setText("Chức vụ: Admin");
        }
        Glide.with(this.context).load(list.get(position).getAvt()).into(holder.binding.imgAvt);

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usersId = list.get(position).get_id();
                Log.e("tag_kiemTraIDDDDDDD", usersId);
                Call<Users> call = serviceUsers.deleteById(usersId);
                call.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Xoá người dùng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Toast.makeText(context, "Có lỗi khi gửi yêu cầu xoá người dùng", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final UsersItemRcvBinding binding;

        public ViewHolder(@NonNull UsersItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
