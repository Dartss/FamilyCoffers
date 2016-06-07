package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.CategoriesListAdapter;
import com.gorih.familycoffers.model.Category;
import com.gorih.familycoffers.presenter.dialog.dlgAddCategory;
import com.gorih.familycoffers.presenter.dialog.dlgAddExpanse;
import com.gorih.familycoffers.presenter.dialog.dlgEditCategory;

public class CategoriesFragment extends AbstractFragment {
    private static final String TAG = "--CategoriesFrag--";
    private DialogFragment dlgAddCategory;
    private static final int LAYOUT = R.layout.fragment_categories;
    RecyclerView listWithCategoriesRv;

    public static CategoriesFragment getInstance(Context context) {
        Bundle args = new Bundle();

        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_expanses));

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }


    private void initListener() {
        final DialogFragment dlgNewExpanse = new dlgAddExpanse();
        CategoriesListAdapter.OnItemClickListener onItemClickListener =
                new CategoriesListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Category item) {
//                        DialogFragment newExpanseFragment = dlgAddExpanse.newInstance(item.getId());
                        Bundle args = new Bundle();
                        args.putInt("CategoryId", item.getId());
                        dlgNewExpanse.setArguments(args);
                        android.support.v4.app.FragmentManager manager = getChildFragmentManager();
//                        dlgNewExpanse.show(manager, "add expanse");
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.add(dlgNewExpanse, null);
                        ft.commitAllowingStateLoss();
                    }
                };

        CategoriesListAdapter.OnItemLongClickListener onItemLongClickListener =
                new CategoriesListAdapter.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(Category item) {
                        DialogFragment categoryEditor = dlgEditCategory.newInstance(item.getId());
                        android.support.v4.app.FragmentManager manager = getChildFragmentManager();
                        categoryEditor.show(manager, "edit expanse");
                    }
                };
        CategoriesListAdapter.init(onItemClickListener, onItemLongClickListener);

        listWithCategoriesRv.setAdapter(CategoriesListAdapter.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        listWithCategoriesRv = (RecyclerView) view.findViewById(R.id.recycler_view);
        listWithCategoriesRv.setLayoutManager(new GridLayoutManager(context, 3));

        initFAB(view);

        return view;
    }

    private void initFAB(final View view) {
        dlgAddCategory = new dlgAddCategory();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before adding count = "+CategoriesListAdapter.getInstance().getItemCount());

                dlgAddCategory.show(getFragmentManager(), "new category");
            }
        });
    }

}
