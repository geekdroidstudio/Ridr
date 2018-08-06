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
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.RouteStatusPresenter;

public class RouteStatusFragment extends Fragment implements RouteStatusView {

    @BindView(R.id.tv_info)
    TextView textViewInfo;
    @InjectPresenter
    RouteStatusPresenter presenter;
    private OnFragmentInteractionListener listener;
    private String start = "";

    private String finish = "";

    public RouteStatusFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_status, container, false);

        ButterKnife.bind(this, view);

        return view;
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
            listener.goRouteChange(start, finish);

            if (start.isEmpty() && finish.isEmpty()) {
                textViewInfo.setText(R.string.route_is_not_selected);
            } else {
                String info = "От " + start + " до " + finish;//TODO: в ресурсы
                textViewInfo.setText(info);
            }
        }
    }

    @Override
    public void setInfo(String string) {

    }

    public void onRouteSelected(String start, String finish) {
        this.start = start;
        this.finish = finish;
    }

    public interface OnFragmentInteractionListener {
        void goRouteChange(String start, String finish);
    }
}
