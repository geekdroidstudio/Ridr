package geekdroidstudio.ru.ridr.view.fragments.route_status;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.presenter.RouteStatusPresenter;

public class RouteStatusFragment extends Fragment implements RouteStatusView {

    @BindView(R.id.tv_info)
    TextView textViewInfo;

    @InjectPresenter
    RouteStatusPresenter presenter;

    private OnFragmentInteractionListener listener;

    private DualTextRoute dualTextRoute;

    private Unbinder unbinder;

    public RouteStatusFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_status, container, false);

        unbinder = ButterKnife.bind(this, view);

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

    @OnClick(R.id.btn_change)
    public void onClickChange(View view) {
        if (listener != null) {
            listener.goRouteChange(dualTextRoute);
        }
    }

    @Override
    public void setInfo() {
        if (dualTextRoute == null) {
            textViewInfo.setText(R.string.route_is_not_selected);
        } else {
            //TODO: в ресурсы
            String info = "От " + dualTextRoute.getStart() + " до " + dualTextRoute.getFinish();
            textViewInfo.setText(info);
        }
    }

    public void onRouteSelected(DualTextRoute dualTextRoute) {
        this.dualTextRoute = dualTextRoute;
        setInfo();
    }

    public interface OnFragmentInteractionListener {
        void goRouteChange(DualTextRoute dualTextRoute);
    }
}
