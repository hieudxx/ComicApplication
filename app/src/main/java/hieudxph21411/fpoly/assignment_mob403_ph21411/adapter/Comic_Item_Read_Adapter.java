package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ComicItemReadRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;

public class Comic_Item_Read_Adapter extends RecyclerView.Adapter<Comic_Item_Read_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<String> list;

    public Comic_Item_Read_Adapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComicItemReadRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.comic_item_read_rcv,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(this.context).load(list.get(position)).into(holder.binding.imgContent);
    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ComicItemReadRcvBinding binding;

        public ViewHolder(@NonNull ComicItemReadRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
