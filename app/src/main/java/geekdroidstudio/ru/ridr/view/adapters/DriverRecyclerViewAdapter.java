package geekdroidstudio.ru.ridr.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class DriverRecyclerViewAdapter extends RecyclerView.Adapter<DriverRecyclerItemViewHolder> {

    @Override
    public DriverRecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DriverRecyclerItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(DriverRecyclerItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
