package fr.delcey.github_mvvm_repository_java.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.delcey.github_mvvm_repository_java.R;
import fr.delcey.github_mvvm_repository_java.view.model.ItemModelUi;

public class MainRecyclerViewAdapter extends ListAdapter<ItemModelUi, MainRecyclerViewAdapter.MainViewHolder> {

    private OnItemClickedListener listener;

    protected MainRecyclerViewAdapter(OnItemClickedListener listener) {
        super(new MainDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                R.layout.main_itemview,
                parent,
                false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView author;
        private final Button button;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.main_item_tv_repo_name);
            author = itemView.findViewById(R.id.main_item_tv_repo_author);
            button = itemView.findViewById(R.id.main_item_btn);
        }

        public void bind(final ItemModelUi item, final OnItemClickedListener listener) {
            name.setText(item.getRepositoryName());
            author.setText(item.getAuthor());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(item);
                }
            });
        }
    }

    interface OnItemClickedListener {
        void onItemClicked(ItemModelUi itemModelUi);
    }

    private static class MainDiffCallback extends DiffUtil.ItemCallback<ItemModelUi> {

        @Override
        public boolean areItemsTheSame(@NonNull ItemModelUi oldItem, @NonNull ItemModelUi newItem) {
            return false;
        }
        @Override
        public boolean areContentsTheSame(@NonNull ItemModelUi oldItem, @NonNull ItemModelUi newItem) {
            return false;
        }
    }
}
