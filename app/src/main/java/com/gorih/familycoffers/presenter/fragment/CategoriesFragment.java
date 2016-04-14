package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.CategoriesListAdapter;
import com.gorih.familycoffers.presenter.dialog.dlgAddCategory;
import com.gorih.familycoffers.model.Category;
import com.gorih.familycoffers.presenter.dialog.dlgAddExpanse;

public class CategoriesFragment extends AbstractFragment {
    private static final int LAYOUT = R.layout.fragment_categories;
//    private DialogFragment dlgAddCategory;

    public static CategoriesFragment getInstance(Context context) {
        Bundle args = new Bundle();

        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_expanses));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView listWithCategoriesRv = (RecyclerView) view.findViewById(R.id.recycler_view);
        listWithCategoriesRv.setLayoutManager(new GridLayoutManager(context, 3));

        CategoriesListAdapter.OnItemClickListener onItemClickListener =
                new CategoriesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category item) {
                DialogFragment newExpanseFragment = dlgAddExpanse.newInstance(item.getName());
                newExpanseFragment.show(getFragmentManager(), "add expanse");
            }
        };
        CategoriesListAdapter.init(onItemClickListener);

        listWithCategoriesRv.setAdapter(CategoriesListAdapter.getInstance());

        initFAB(view);

        return view;
    }

    private void initFAB(final View view) {
//        dlgAddCategory = new dlgAddCategory();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlgAddCategory.show(getFragmentManager(), "new category");
//            }
//        });
    }

}
