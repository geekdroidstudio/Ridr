package geekdroidstudio.ru.ridr.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerItemViewHolder> {

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
