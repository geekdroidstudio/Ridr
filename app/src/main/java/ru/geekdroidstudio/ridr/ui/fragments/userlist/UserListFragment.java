package ru.geekdroidstudio.ridr.ui.fragments.userlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekdroidstudio.ridr.R;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.communication.entity.UserAndRoute;
import ru.geekdroidstudio.ridr.ui.fragments.userlist.adapter.UserListRecyclerViewAdapter;

import static android.widget.LinearLayout.VERTICAL;

public class UserListFragment extends Fragment {

    @BindView(R.id.holder_recycler_view_objects_around)
    RecyclerView recyclerView;

    private OnFragmentInteractionListener listener;
    private Unbinder unbinder;

    private UserListRecyclerViewAdapter adapter;

    public UserListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new UserListRecyclerViewAdapter(listener);

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

    public void setUsersAndRoutes(List<UserAndRoute<? extends User>> usersAndRoutes) {
        adapter.setUserAndRouteList(usersAndRoutes);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(UserAndRoute<? extends User> userAndRoute);
    }
}
