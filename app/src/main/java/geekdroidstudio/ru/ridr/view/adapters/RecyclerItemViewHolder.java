package geekdroidstudio.ru.ridr.view.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.R;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_item_text_view_name)
    AppCompatTextView nameTextView;

    RecyclerItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.recycler_objects_item_around, parent, false));
        ButterKnife.bind(this, itemView);
    }

    void bind(int position) {
        nameTextView.setText("Driver " + position);
    }

    @OnClick(R.id.list_item_text_view_name)
    public void onClick(View view) {
        Toast.makeText(itemView.getContext(),"Вы выбрали маршрут " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }

}