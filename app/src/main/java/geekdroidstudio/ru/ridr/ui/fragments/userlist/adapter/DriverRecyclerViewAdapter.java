package geekdroidstudio.ru.ridr.ui.fragments.userlist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.entity.User;
import geekdroidstudio.ru.ridr.model.communication.entity.UserAndRoute;
import geekdroidstudio.ru.ridr.model.communication.location.entity.DualRoute;
import geekdroidstudio.ru.ridr.model.communication.location.entity.DualTextRoute;
import geekdroidstudio.ru.ridr.ui.fragments.userlist.UserListFragment;

public class DriverRecyclerViewAdapter extends RecyclerView.Adapter<DriverRecyclerItemViewHolder> {

    private List<UserAndRoute<? extends User>> userAndRouteList = new ArrayList<>();
    private UserListFragment.OnFragmentInteractionListener onItemClickListener;

    public DriverRecyclerViewAdapter(UserListFragment.OnFragmentInteractionListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setUserAndRouteList(List<UserAndRoute<? extends User>> userAndRouteList) {
        this.userAndRouteList = userAndRouteList;
    }

    @NonNull
    @Override
    public DriverRecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DriverRecyclerItemViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverRecyclerItemViewHolder holder, int position) {
        UserAndRoute userAndRoute = userAndRouteList.get(position);

        holder.nameTextView.setText(userAndRoute.getUser().getName());
        DualRoute dualRoute = userAndRoute.getDualRoute();
        String start = "";
        String finish = "";
        if (dualRoute != null) {
            DualTextRoute dualTextRoute = dualRoute.getTextRoute();
            if (dualTextRoute != null) {
                start = userAndRoute.getDualRoute().getTextRoute().getStart();
                finish = userAndRoute.getDualRoute().getTextRoute().getFinish();
            }
        }
        holder.startTextView.setText(start);
        holder.finishTextView.setText(finish);
        holder.mainView.setOnClickListener(
                v -> onItemClickListener.onItemClick(userAndRouteList.get(position)));
    }

    @Override
    public int getItemCount() {
        return userAndRouteList.size();
    }
}
