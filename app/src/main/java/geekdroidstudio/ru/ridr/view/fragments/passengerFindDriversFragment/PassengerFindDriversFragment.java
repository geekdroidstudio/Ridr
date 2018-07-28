package geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.PassengerFindDriversPresenter;
import geekdroidstudio.ru.ridr.view.adapters.RecyclerViewAdapter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;

import static android.widget.LinearLayout.VERTICAL;

public class PassengerFindDriversFragment extends MvpAppCompatFragment implements PassengerFindDriversView {
    @InjectPresenter
    PassengerFindDriversPresenter passengerFindDriversPresenter;

    @BindView(R.id.map_container)
    FrameLayout flMapContainer;
    @BindView(R.id.holder_recycler_view_objects_around)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public PassengerFindDriversFragment(){}

    public static geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment newInstance() {
        return new geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_fragment_interaction_listener));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_find_drivers, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter());
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMapFragment() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_container, MapFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

//    @Override
//    public void viewDriverRoute(int id) {
//
//    }

    public interface OnFragmentInteractionListener {
        void openDriverRouteData(int id);
    }


}