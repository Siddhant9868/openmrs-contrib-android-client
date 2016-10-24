package org.openmrs.mobile.activities.activevisits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.openmrs.mobile.R;
import org.openmrs.mobile.activities.fragments.ACBaseFragment;
import org.openmrs.mobile.models.retrofit.Visit;
import org.openmrs.mobile.utilities.FontsUtil;

import java.util.ArrayList;
import java.util.List;

public class ActiveVisitsFragment extends ACBaseFragment implements ActiveVisitsContract.View{

    private ActiveVisitsContract.Presenter mPresenter;

    private RecyclerView visitsRecyclerView;
    private TextView emptyList;

    public static ActiveVisitsFragment newInstance(){
        return new ActiveVisitsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_active_visits, container, false);

        visitsRecyclerView = (RecyclerView) root.findViewById(R.id.visitsRecyclerView);
        visitsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        visitsRecyclerView.setLayoutManager(linearLayoutManager);
        visitsRecyclerView.setAdapter(new ActiveVisitsRecyclerViewAdapter(this.getActivity(),
                new ArrayList<Visit>()));

        emptyList = (TextView) root.findViewById(R.id.emptyVisitsListViewLabel);
        emptyList.setText(getString(R.string.search_visits_no_results));
        emptyList.setVisibility(View.INVISIBLE);

        mPresenter.updateVisitsInDatabaseList();

        FontsUtil.setFont((ViewGroup) this.getActivity().findViewById(android.R.id.content));

        return root;
    }

    @Override
    public void updateListVisibility(List<Visit> visits) {
        if (visits.isEmpty()) {
            visitsRecyclerView.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        } else {
            visitsRecyclerView.setAdapter(new ActiveVisitsRecyclerViewAdapter(this.getActivity(), visits));
            visitsRecyclerView.setVisibility(View.VISIBLE);
            emptyList.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAdapterFiltering(boolean filtering) {
        ((ActiveVisitsRecyclerViewAdapter) visitsRecyclerView.getAdapter()).setIsFiltering(true);
    }

    @Override
    public void setEmptyListText(int stringId) {
        emptyList.setText(getString(stringId));
    }

    @Override
    public void setEmptyListText(int stringId, String query) {
        emptyList.setText(getString(stringId, query));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ActiveVisitsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
