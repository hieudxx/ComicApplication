package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import hieudxph21411.fpoly.assignment_mob403_ph21411.MainActivity;
import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.ComicItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicDetailFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.fragment.ComicListFragment;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;

public class Comic_Item_Adapter extends RecyclerView.Adapter<Comic_Item_Adapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Comic> list;
    private ArrayList<Comic> mlist;

    public Comic_Item_Adapter(Context context, ArrayList<Comic> list) {
        this.context = context;
        this.list = list;
        this.mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComicItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.comic_item_rcv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvNameComic.setText(list.get(position).getName());
        holder.binding.chapter.setText("Chapter " + list.get(position).getChapter());
        Glide.with(this.context).load(list.get(position).getCover()).into(holder.binding.imgCover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loadFragment(ComicDetailFragment.newInstance(list.get(position).get_id()));
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<Comic> listFilter = new ArrayList<>();
                    for (Comic c : mlist) {
                        if (c.getName().toLowerCase().contains(search)) {
                            listFilter.add(c);
                        }
                    }
                    list = listFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Comic>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ComicItemRcvBinding binding;

        public ViewHolder(@NonNull ComicItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
