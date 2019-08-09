package com.lorealbaautomation.fragements;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lorealbaautomation.R;
import com.lorealbaautomation.dailyactivity.ConsumerReturnActivity;
import com.lorealbaautomation.dailyactivity.CounterImageActivity;
import com.lorealbaautomation.dailyactivity.GroomedActivity;
import com.lorealbaautomation.dailyactivity.StockCheckActivity;
import com.lorealbaautomation.delegates.NavMenuItemGetterSetter;


import java.util.Collections;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAdaperForIcon extends RecyclerView.Adapter<MyAdaperForIcon.ViewHolder> {
    List<NavMenuItemGetterSetter> data = Collections.emptyList();
    private LayoutInflater inflator;
    Context context;

    public MyAdaperForIcon(Context context, List<NavMenuItemGetterSetter> data) {
        inflator = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NavMenuItemGetterSetter current = data.get(position);
        holder.list_icon.setImageResource(current.getIconImg());
        holder.icon_txtname.setText(current.getIconName());

        holder.list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.getIconImg() == R.mipmap.counter_image) {
                    context.startActivity(new Intent(context, CounterImageActivity.class));
                } else if (current.getIconImg() == R.mipmap.groomed) {
                    context.startActivity(new Intent(context, GroomedActivity.class));
                } else if (current.getIconImg() == R.mipmap.consumer_return) {
                    context.startActivity(new Intent(context, ConsumerReturnActivity.class));
                }else if (current.getIconImg() == R.mipmap.stock_check) {
                    context.startActivity(new Intent(context, StockCheckActivity.class));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView list_icon;
        public final TextView icon_txtname;

        public ViewHolder(View view) {
            super(view);
            list_icon = (ImageView) view.findViewById(R.id.list_icon);
            icon_txtname = (TextView) view.findViewById(R.id.icon_txtname);
        }
    }
}
