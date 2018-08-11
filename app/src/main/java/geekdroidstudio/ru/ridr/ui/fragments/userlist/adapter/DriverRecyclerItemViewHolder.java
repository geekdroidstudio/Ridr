package geekdroidstudio.ru.ridr.ui.fragments.userlist.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.R;

class DriverRecyclerItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cl_container)
    View mainView;

    @BindView(R.id.list_item_text_view_name)
    AppCompatTextView nameTextView;

    @BindView(R.id.list_item_text_view_point_start)
    AppCompatTextView startTextView;

    @BindView(R.id.list_item_text_view_point_finish)
    AppCompatTextView finishTextView;

    DriverRecyclerItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.recycler_objects_item_around, parent, false));

        ButterKnife.bind(this, itemView);
    }
}