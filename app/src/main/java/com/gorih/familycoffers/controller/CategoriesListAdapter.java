package com.gorih.familycoffers.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.Category;
import com.gorih.familycoffers.presenter.dialog.dlgAddCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.categoriesViewHolder> {

    private static List<Category> categories;
    private OnItemClickListener listener ;
    private static CategoriesListAdapter adapter;


    public interface OnItemClickListener {
        void onItemClick(Category item);
    }

    public static CategoriesListAdapter getInstance(OnItemClickListener listener) {
        if (adapter == null) {
            adapter = new CategoriesListAdapter(listener);
        }

        return adapter;
    }

    private CategoriesListAdapter(OnItemClickListener listener) {
        categories = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public categoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new categoriesViewHolder(view);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public static void addCategory(Category newCategory) {
        categories.add(newCategory);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(categoriesViewHolder holder, int position) {
        holder.bind(categories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class categoriesViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        public categoriesViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bind(final Category category, final OnItemClickListener listener ) {
            title.setText(category.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(category);
                }
            });
        }

    }

}
