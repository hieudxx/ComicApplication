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
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ComicItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicListFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.loadFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;

public class Comic_Item_Adapter extends RecyclerView.Adapter<Comic_Item_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Comic> list;
    private loadFragment loadFragment;

    public Comic_Item_Adapter(hieudxph21411.fpoly.assignment_mob403_ph21411.loadFragment loadFragment) {
        this.loadFragment = loadFragment;
    }

    public Comic_Item_Adapter(Context context, ArrayList<Comic> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComicItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.comic_item_rcv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvNameComic.setText(list.get(position).getName());
        holder.binding.chapter.setText("Chapter " + list.get(position).getChapter());
        Glide.with(this.context).load(list.get(position).getCover()).into(holder.binding.imgCover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment.loadFragment(ComicListFragment.newInstance());


//               context.startActivity(new Intent(context, ComicListActivity.class));
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
        private final ComicItemRcvBinding binding;

        public ViewHolder(@NonNull ComicItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
