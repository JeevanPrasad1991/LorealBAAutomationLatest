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
public class OffersFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OffersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OffersFragment newInstance(int columnCount) {
        OffersFragment fragment = new OffersFragment();
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
        View view = inflater.inflate(R.layout.fragment_item_list3, container, false);
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
        int counter_img = 0, stock_check = 0, tester_stock, pwp_gwp_stock = 0, sample_stock, dameged_stock;
        counter_img = R.mipmap.counter_image;
        stock_check = R.mipmap.stock_check;
        tester_stock = R.mipmap.tester_stock;
        pwp_gwp_stock = R.mipmap.tester_stock;
        sample_stock = R.mipmap.tester_stock;
        dameged_stock = R.mipmap.damage_stock;


        int img[] = {counter_img, stock_check, tester_stock, pwp_gwp_stock, sample_stock, dameged_stock};
        String name[] = {"Counter Image", "Stock Check", "Tester", "PWP/GWP", "Sample", "Damage"};
        for (int i = 0; i < img.length; i++) {
            NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
            recData.setIconImg(img[i]);
            recData.setIconName(name[i]);
            data.add(recData);
        }

        return data;
    }

}
