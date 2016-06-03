package com.gorih.familycoffers.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.CategIdAndValue;
import com.gorih.familycoffers.model.Categories;

import java.util.ArrayList;
import java.util.Formatter;

public class StatisticsListAdapter extends BaseAdapter {
    private ArrayList<CategIdAndValue> itemsList;
    private Context context;
    private float totalValue;

    public StatisticsListAdapter(Context context, ArrayList<CategIdAndValue> list, float totalValue) {
        itemsList = list;
        this.totalValue = totalValue;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public CategIdAndValue getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView value;
        TextView percents;
        View categoryColor;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int color = Categories.instance.findCategoryById(getItem(position).getCategoyrId()).getColor();
        float valueSum = getItem(position).getValueSum();
        Formatter formatter = new Formatter();
        String categoryName = Categories.instance.findCategoryById(getItem(position).getCategoyrId()).getName();
        View rowView = convertView;

        //Небольшая оптимизация, которая позволяет повторно использовать объекты
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.statistics_list_row, parent, false);
            ViewHolder h = new ViewHolder();
            h.value = (TextView) rowView.findViewById(R.id.statistics_list_value);
            h.categoryColor = rowView.findViewById(R.id.statistics_list_category_color);
            h.percents = (TextView) rowView.findViewById(R.id.statistics_list_percents);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        formatter.format("%.2f", (100 * valueSum) / totalValue);
        h.categoryColor.setBackgroundColor(color);
        h.value.setText(categoryName+": "+valueSum);
        h.percents.setText(" ("+formatter.toString()+"%)");

        return rowView;
    }
}
