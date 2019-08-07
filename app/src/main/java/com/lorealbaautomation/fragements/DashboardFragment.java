package com.lorealbaautomation.fragements;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lorealbaautomation.R;
import com.lorealbaautomation.gsonGetterSetter.DashboardAchivementDetail;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p>
 * interface.
 */
public class DashboardFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    ArrayList<DashboardAchivementDetail> dashboardList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashboardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DashboardFragment newInstance(int columnCount) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            DashboardAchivementDetail object = new DashboardAchivementDetail();
            object.setMrp(12.2);
            dashboardList.add(object);

            object = new DashboardAchivementDetail();
            object.setMrp(12.2);
            dashboardList.add(object);
            object = new DashboardAchivementDetail();
            object.setMrp(12.2);
            dashboardList.add(object);


            recyclerView.setAdapter(new DashboardAdapter(context, dashboardList));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }

        return view;
    }

    private class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Viewholder> {
        ArrayList<DashboardAchivementDetail> dashboardList;
        Context context;

        DashboardAdapter(Context context, ArrayList<DashboardAchivementDetail> dashboardList) {
            this.dashboardList = dashboardList;
            this.context = context;

        }


        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dashboard_item_adapter, viewGroup, false);
            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {

           // pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
            DashboardAchivementDetail current = dashboardList.get(position);
            List pieData = new ArrayList<>();
            pieData.add(new SliceValue(45, getResources().getColor(R.color.dashboard_ach)));
            pieData.add(new SliceValue(55, getResources().getColor(R.color.dashboard_less_ach)));

            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("45%").setCenterText1FontSize(15).setCenterText1Color(getResources().getColor(R.color.black));
            viewholder.pieChartView.setPieChartData(pieChartData);
        }

        @Override
        public int getItemCount() {
            return dashboardList.size();
        }

        class Viewholder extends RecyclerView.ViewHolder {
            PieChartView pieChartView;

            public Viewholder(@NonNull View itemView) {
                super(itemView);
                pieChartView = itemView.findViewById(R.id.chart);
            }
        }
    }

}
