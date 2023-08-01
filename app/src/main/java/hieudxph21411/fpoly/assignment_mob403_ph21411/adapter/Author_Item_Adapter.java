package hieudxph21411.fpoly.assignment_mob403_ph21411.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hieudxph21411.fpoly.assignment_mob403_ph21411.R;
import hieudxph21411.fpoly.assignment_mob403_ph21411.databinding.AuthorItemRcvBinding;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Author;

public class Author_Item_Adapter extends RecyclerView.Adapter<Author_Item_Adapter.ViewHolder> {
    private Context context;
    private List<Author> list;

    public Author_Item_Adapter(Context context, List<Author> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AuthorItemRcvBinding binding = DataBindingUtil.inflate(inflater, R.layout.author_item_rcv, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        holder.binding.tvDate.setText(list.get(position).getDate());
//        holder.binding.tvComic.setText("Số lượng tác phẩm: "+list.get(position).getComic().length);

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final AuthorItemRcvBinding binding;

        public ViewHolder(@NonNull AuthorItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
