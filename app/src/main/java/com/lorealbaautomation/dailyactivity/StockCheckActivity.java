package com.lorealbaautomation.dailyactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.R;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gsonGetterSetter.ProductMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockCheckActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayAdapter<CharSequence> axe_name_adapter, sub_axe_name_adapter, brand_name_adapter, product_name_adapter;
    Spinner sku_spin, brand_spin, sub_axe_spin, axe_spin;
    ArrayList<ProductMaster> BrandList = new ArrayList<>();
    ArrayList<ProductMaster> subAxeList = new ArrayList<>();
    ArrayList<ProductMaster> AxeList = new ArrayList<>();

    ExpandableListView lvExp_stock_check;
    FloatingActionButton btn_save;
    Lorealba_Database db;
    Context context;
    AlertDialog alert;
    private SharedPreferences preferences;
    String counter_id, visit_date, username, user_type, _pathforcheck, _path;
    ArrayList<ProductMaster> listDataHeader = new ArrayList<>();
    ArrayList<ProductMaster> questionList = new ArrayList<>();
    HashMap<ProductMaster, List<ProductMaster>> listDataChild;
    boolean checkflag = true;
    ArrayList<Integer> checkHeaderArray = new ArrayList<>();
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);
        context = this;
        db = new Lorealba_Database(context);
        db.open();
        validateuidata();
        prepareListData();
        validate_spin_ui_data();
        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);
        lvExp_stock_check.setAdapter(listAdapter);
    }

    private void validateuidata() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        counter_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Stock Check");
        sku_spin = (Spinner) findViewById(R.id.sku_spin);
        brand_spin = (Spinner) findViewById(R.id.stock_brand_spin);
        sub_axe_spin = (Spinner) findViewById(R.id.sub_axe_spin);
        axe_spin = (Spinner) findViewById(R.id.axe_spin);
        lvExp_stock_check = (ExpandableListView) findViewById(R.id.lvExp_stock_check);
        btn_save = (FloatingActionButton) findViewById(R.id.save_fab);
        db.open();
        BrandList = db.getsub_brand_name("1", "BrandName");
        subAxeList = db.getsub_brand_name("1", "SubAxeName");
        AxeList = db.getsub_brand_name("1", "AxeName");
    }

    private void prepareListData() {
        db.open();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader = db.getstock_check_axeandsub_axe("1");
        if (listDataHeader.size() > 0) {
            for (int i = 0; i < listDataHeader.size(); i++) {
                // questionList = db.getStockInsertedData(store_cd, listDataHeader.get(i).getSubAxeName(), sigature_id);
                if (questionList.size() > 0) {
                    btn_save.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                } else {
                    questionList = db.getbrand_wise_sku_fromStockData(listDataHeader.get(i).getSubAxeId().toString(), listDataHeader.get(i).getAxeId().toString(), "1");
                }

                listDataChild.put(listDataHeader.get(i), questionList); // Header, Child data
            }
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.stock_brand_spin:
                if (position != 0) {
                    listAdapter = new ExpandableListAdapter(context, filterList(listDataHeader, adapterView.getSelectedItem().toString()), listDataChild);
                    lvExp_stock_check.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                } else {
                    listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);
                    lvExp_stock_check.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.axe_spin:
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public ArrayList<ProductMaster> filterList(List<ProductMaster> originalList, String text) {
        ArrayList<ProductMaster> filterList = new ArrayList<>();
        for (ProductMaster object : originalList) {
            if (object.getBrandName().equalsIgnoreCase(text)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<ProductMaster> _listDataHeader;
        private HashMap<ProductMaster, List<ProductMaster>> _listDataChild;

        public ExpandableListAdapter(Context context, List<ProductMaster> listDataHeader, HashMap<ProductMaster, List<ProductMaster>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final ProductMaster childText = (ProductMaster) getChild(groupPosition, childPosition);
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_stock_entory, null);
                holder = new ViewHolder();
                holder.cardView = convertView.findViewById(R.id.card_view);
                holder.ed_Stock = convertView.findViewById(R.id.ed_Stock);
                holder.mrp = convertView.findViewById(R.id.mrp);
                holder.stock_img_plus = convertView.findViewById(R.id.stock_img_plus);
                holder.stock_img_minus = convertView.findViewById(R.id.stock_img_minus);
                convertView.setTag(holder);


            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            TextView txtListChild = convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(childText.getProductName());

            holder.stock_img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listAdapter.notifyDataSetChanged();
                    int minteger = childText.getStock();
                    ++minteger;

                    if (minteger <= 10) {
                        childText.setStock(minteger);
                    } else {
                        Snackbar.make(lvExp_stock_check, "Stock cannot be greater than 10.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

            holder.stock_img_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listAdapter.notifyDataSetChanged();
                    int minteger = childText.getStock();
                    --minteger;

                    if (minteger >= 0) {
                        childText.setStock(minteger);
                    } else {
                        Snackbar.make(lvExp_stock_check, "Stock cannot be less than 0.", Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
            holder.ed_Stock.setText(childText.getStock() + "");
            holder.ed_Stock.setId(childPosition);

            holder.mrp.setText(" MRP = " + childText.getMrp() + "");
            holder.mrp.setId(childPosition);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final ProductMaster headerTitle = (ProductMaster) getGroup(groupPosition);
            if (convertView == null) {

                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_stock_entry, null);
            }
            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setText(headerTitle.getAxeName() + " - " + headerTitle.getSubAxeName() + " - " + headerTitle.getBrandName());
            if (!checkflag) {
                if (checkHeaderArray.contains(groupPosition)) {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.dashboard_ach));
                }

            } else {
                lblListHeader.setBackgroundColor(getResources().getColor(R.color.dashboard_ach));
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class ViewHolder {
        TextView ed_Stock, mrp;
        Button stock_img_plus, stock_img_minus;
        CardView cardView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void validate_spin_ui_data() {
        brand_name_adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_item);
        brand_name_adapter.add("- Brand -");

        for (int i = 0; i < BrandList.size(); i++) {
            brand_name_adapter.add(BrandList.get(i).getBrandName());
        }

        brand_spin.setAdapter(brand_name_adapter);
        brand_name_adapter.setDropDownViewResource(R.layout.spinner_custom_item);
        brand_spin.setOnItemSelectedListener(this);

        axe_name_adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_item);
        axe_name_adapter.add("- Axe Name -");

        for (int i = 0; i < AxeList.size(); i++) {
            axe_name_adapter.add(AxeList.get(i).getBrandName());
        }

        axe_spin.setAdapter(axe_name_adapter);
        axe_name_adapter.setDropDownViewResource(R.layout.spinner_custom_item);
        axe_spin.setOnItemSelectedListener(this);

        sub_axe_name_adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_item);
        sub_axe_name_adapter.add("- Sub Axe Name -");


        for (int i = 0; i < subAxeList.size(); i++) {
            sub_axe_name_adapter.add(subAxeList.get(i).getBrandName());
        }

        sub_axe_spin.setAdapter(sub_axe_name_adapter);
        sub_axe_name_adapter.setDropDownViewResource(R.layout.spinner_custom_item);
        sub_axe_spin.setOnItemSelectedListener(this);


        product_name_adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_item);
        product_name_adapter.add("- Product -");

        axe_spin.setOnItemSelectedListener(this);
        sub_axe_spin.setOnItemSelectedListener(this);
        brand_spin.setOnItemSelectedListener(this);

    }

}
