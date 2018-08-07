package geekdroidstudio.ru.ridr.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;

public class DriverRecyclerViewAdapter extends RecyclerView.Adapter<DriverRecyclerItemViewHolder> {

    private List<UserAndRoute<? extends User>> userAndRouteMap;

    public DriverRecyclerViewAdapter() {
    }

    public void setUserAndRouteMap(List<UserAndRoute<? extends User>> userAndRouteMap) {
        this.userAndRouteMap = userAndRouteMap;
    }

    @NonNull
    @Override
    public DriverRecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DriverRecyclerItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverRecyclerItemViewHolder holder, int position) {
        UserAndRoute userAndRoute = userAndRouteMap.get(position);

        holder.nameTextView.setText(userAndRoute.getUser().getName());
        holder.startTextView.setText(userAndRoute.getSimpleRoute().getStart().toString());
        holder.finishTextView.setText(userAndRoute.getSimpleRoute().getFinish().toString());
    }

    @Override
    public int getItemCount() {
        return userAndRouteMap.size();
    }

}
