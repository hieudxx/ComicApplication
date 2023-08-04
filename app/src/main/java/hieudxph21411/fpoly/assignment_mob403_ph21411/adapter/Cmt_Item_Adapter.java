package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APICmt;
import hieudxph21411.fpoly.assignment_mob403_ph21411.api.APIUsers;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.CmtItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.DialogEditCmtBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.DialogEditUsersBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cmt_Item_Adapter extends RecyclerView.Adapter<Cmt_Item_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Cmt> list;
    private Users users;
    private DialogEditCmtBinding editBinding;

    public Cmt_Item_Adapter(Context context, ArrayList<Cmt> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CmtItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.cmt_item_rcv, parent, false);
        users = new Users();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(this.context).load(list.get(position).getUsers().getAvt()).into(holder.binding.imgAvt);
        holder.binding.tvFullName.setText(list.get(position).getUsers().getFullname());
        holder.binding.tvContent.setText(list.get(position).getContent());
        holder.binding.tvTime.setText(list.get(position).getTime());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.getApplicationContext().reM registerForContextMenu(view);
//                openContextMenu(view);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private final CmtItemRcvBinding binding;

        public ViewHolder(@NonNull CmtItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.imgOpMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu_cmt_rcv);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.cmt_delete) {
                Log.e("tag_kiemTra", "onMenuItemClick: xóa" + list.get(getAdapterPosition()));
            } else if (item.getItemId() == R.id.cmt_edit) {
                String cmtId = list.get(getAdapterPosition()).get_id();
                String usersId = list.get(getAdapterPosition()).getUsers().get_id();

                AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
                builder.setCancelable(false);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_edit_users, null);
                editBinding = DialogEditCmtBinding.inflate(inflater, view, false);

                builder.setView(editBinding.getRoot());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                editBinding.edContent.setText(list.get(getAdapterPosition()).getContent());
                editBinding.edTime.setText(list.get(getAdapterPosition()).getTime());
                editBinding.tvFullName.setText(list.get(getAdapterPosition()).getUsers().getFullname());
                Glide.with(context).load(list.get(getAdapterPosition()).getUsers().getAvt()).into(editBinding.imgAvt);

                editBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        String time = df.format(c.getTime());

                        String content = editBinding.edContent.getText().toString();
                        if (time.isEmpty() || content.isEmpty()) {
                            Toast.makeText(context, "Hãy điền đủ dữ liệu", Toast.LENGTH_SHORT).show();
                        } else {
                            Cmt bl = new Cmt();
                            bl.setTime(time);
                            bl.setContent(content);
                            APICmt.apiCmt.updateCmt(bl, cmtId, usersId).enqueue(new Callback<Cmt>() {
                                @Override
                                public void onResponse(Call<Cmt> call, Response<Cmt> response) {
                                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<Cmt> call, Throwable t) {
                                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();

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

                String _id = list.get(getAdapterPosition()).get_id();
//                APICmt.apiCmt.updateCmt().enqueue(new Callback<Cmt>() {
//                    @Override
//                    public void onResponse(Call<Cmt> call, Response<Cmt> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Cmt> call, Throwable t) {
//
//                    }
//                });
            } else if (item.getItemId() == R.id.cmt_report) {
                Log.e("tag_kiemTra", "onMenuItemClick: báo cáo");
            }
            return false;
        }
    }
}
