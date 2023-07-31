package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.activity.RegisterActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.DialogEditUsersBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.UsersItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.UsersListFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Users_Item_Adapter extends RecyclerView.Adapter<Users_Item_Adapter.ViewHolder> {
    private AlertDialog.Builder builder;
    private DialogEditUsersBinding editBinding;
    private Context context;
    private ArrayList<Users> list;

    public Users_Item_Adapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UsersItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.users_item_rcv, parent, false);
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

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    APIUsers.apiUsers.deleteById(list.get(position).get_id()).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                UsersListFragment.getData();
                                Toast.makeText(context, "Xoá người dùng thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Toast.makeText(context, "Có lỗi khi gửi yêu cầu xoá người dùng", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                builder.setPositiveButton("Không", null);
                builder.show();
            }
        });

        holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit(list.get(holder.getAdapterPosition()));
            }
        });
    }

    private void showEdit(Users users) {
        builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_users, null);
        editBinding = DialogEditUsersBinding.inflate(inflater, view, false);

        builder.setView(editBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (users.getRole() == 1) {
            editBinding.tvRole.setText("Chức vụ: Người dùng");
        }
        editBinding.edtFullName.setText(users.getFullname());
        editBinding.edtUserName.setText(users.getUsername());
        editBinding.edtEmail.setText(users.getEmail());
        SharedPreferences shared = ((Activity) context).getSharedPreferences("PROFILE", MODE_PRIVATE);
        editBinding.edtPass.setText(shared.getString("pass", ""));
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("pass", editBinding.edtPass.getText().toString());
        editBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = editBinding.tvPass.getEditText().getText().toString();
                String repass = editBinding.tvRePass.getEditText().getText().toString();
                String email = editBinding.tvEmail.getEditText().getText().toString();
                String fullname = editBinding.tvFullName.getEditText().getText().toString();
                String avt = editBinding.tvAvt.getEditText().getText().toString();
                if (pass.isEmpty() || fullname.isEmpty() || email.isEmpty() || avt.isEmpty()) {
                    Toast.makeText(context, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    RegisterActivity.validField(pass, editBinding.tvPass);
                    RegisterActivity.validField(repass, editBinding.tvRePass);
                    RegisterActivity.validField(email, editBinding.tvEmail);
                    RegisterActivity.validField(fullname, editBinding.tvFullName);
                    RegisterActivity.validField(avt, editBinding.tvAvt);
                    if (!pass.equals(repass)) {
                        editBinding.tvRePass.setError("Vui lòng nhập lại mật khẩu");
                    } else {
                        editBinding.tvRePass.setError(null);
                    }
                } else {
                    Users user = new Users();
                    user.setPass(pass);
                    user.setEmail(email);
                    user.setFullname(fullname);
                    user.setAvt(avt);
                    APIUsers.apiUsers.editById(users.get_id(),user).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            UsersListFragment.getData();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                        }
                    });
                }
            }
        });
        editBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
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
