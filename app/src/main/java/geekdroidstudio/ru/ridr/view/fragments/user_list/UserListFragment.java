package geekdroidstudio.ru.ridr.view.fragments.user_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.view.adapters.DriverRecyclerViewAdapter;

import static android.widget.LinearLayout.VERTICAL;

public class UserListFragment extends Fragment {

    @BindView(R.id.holder_recycler_view_objects_around)
    RecyclerView recyclerView;

    private OnFragmentInteractionListener listener;
    private Unbinder unbinder;

    private Map<String, UserAndRoute<? extends User>> userAndRouteMap = new HashMap<>();
    private DriverRecyclerViewAdapter adapter;

    public UserListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new DriverRecyclerViewAdapter(listener);
        adapter.setUserAndRouteList(new ArrayList<>(userAndRouteMap.values()));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement " + this.getClass().getSimpleName()
                    + ".OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void addUserAndRoute(UserAndRoute<? extends User> userAndRoute) {
        userAndRouteMap.put(userAndRoute.getUser().getId(), userAndRoute);
        adapter.setUserAndRouteList(new ArrayList<>(userAndRouteMap.values()));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(UserAndRoute<? extends User> userAndRoute);
    }
}
