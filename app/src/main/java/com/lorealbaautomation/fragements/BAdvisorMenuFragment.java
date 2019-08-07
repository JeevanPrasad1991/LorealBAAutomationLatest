package com.lorealbaautomation.fragements;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lorealbaautomation.R;
import com.lorealbaautomation.delegates.NavMenuItemGetterSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class BAdvisorMenuFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BAdvisorMenuFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BAdvisorMenuFragment newInstance(int columnCount) {
        BAdvisorMenuFragment fragment = new BAdvisorMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_item_list4, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new MyAdaperForIcon(context, getdata()));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }

        return view;
    }

    public List<NavMenuItemGetterSetter> getdata() {
        List<NavMenuItemGetterSetter> data = new ArrayList<>();
        int groomed_img = 0, consumer_interaction = 0, consumer_return, break_mangment = 0, ba_survey, leave_mangment;
        groomed_img = R.mipmap.groomed;
        consumer_interaction = R.mipmap.consumer_intraction;
        consumer_return = R.mipmap.consumer_return;
        break_mangment = R.mipmap.b_mangement;
        ba_survey = R.mipmap.ba_servay;
        leave_mangment = R.mipmap.leave_managment;


        int img[] = {groomed_img, consumer_interaction, consumer_return, break_mangment, ba_survey, leave_mangment};
        String name[] = {"Grooming Image", "Consumer Interaction", "Consumer Return", "Break Management", "BA Survey", "Leave Management"};
        for (int i = 0; i < img.length; i++) {
            NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
            recData.setIconImg(img[i]);
            recData.setIconName(name[i]);
            data.add(recData);
        }

        return data;
    }

}
