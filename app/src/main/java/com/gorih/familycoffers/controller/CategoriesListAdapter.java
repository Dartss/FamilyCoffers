package com.gorih.familycoffers.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;

import java.util.ArrayList;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.categoriesViewHolder> {

    private ArrayList<Category> categories = Categories.instance.getAllCategoriesList();
    private OnItemClickListener listener ;
    private OnItemLongClickListener longListener ;

    private static CategoriesListAdapter adapter;

    public interface OnItemClickListener {
        void onItemClick(Category item);
    }

    public interface OnItemLongClickListener {
        void onLongClick(Category item);
    }

    public static CategoriesListAdapter getInstance() {
        return adapter;
    }

    public static void init(OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        if (adapter != null ) {
            return;
        }
        adapter = new CategoriesListAdapter(listener, longClickListener);
    }

    public void setNewList() {
        categories = Categories.instance.getAllCategoriesList();
    }

    private CategoriesListAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        this.listener = listener;
        this.longListener = longClickListener;
    }

    @Override
    public categoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new categoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(categoriesViewHolder holder, int position) {
        holder.bind(categories.get(position), listener, longListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class categoriesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        ImageView categoryIcon;
        LinearLayout coloredLine;

        public categoriesViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.title);
            categoryIcon = (ImageView) itemView.findViewById(R.id.img_btn_category_item_icon);
            coloredLine = (LinearLayout) itemView.findViewById(R.id.top_line_colored_by_category);
        }

        public void bind(final Category category, final OnItemClickListener listener,
                         final OnItemLongClickListener longClickListener ) {
            title.setText(category.getName());
            categoryIcon.setImageResource(category.getIcon());
            coloredLine.setBackgroundColor(category.getColor());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(category);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onLongClick(category);
                    return true;
                }
            });
        }

    }

}
