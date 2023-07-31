package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.CmtItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;

public class Cmt_Item_Adapter extends RecyclerView.Adapter<Cmt_Item_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Cmt> list;

    public Cmt_Item_Adapter(Context context, ArrayList<Cmt> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CmtItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.cmt_item_rcv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(this.context).load(list.get(position).getUsers().getAvt()).into(holder.binding.imgAvt);
//        holder.binding.tvFullName.setText(list.get(position).getUsers().getFullname());
        holder.binding.tvContent.setText(list.get(position).getContent());
        holder.binding.tvContent.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CmtItemRcvBinding binding;

        public ViewHolder(@NonNull CmtItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
