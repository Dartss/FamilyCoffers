package com.gorih.familycoffers.controller;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import com.gorih.familycoffers.R;


public class HistoryMultiChoiceImpl implements AbsListView.MultiChoiceModeListener {
    private static final String TAG = "--HMCI--";
    private AbsListView listView;


    public HistoryMultiChoiceImpl(AbsListView listView) {
        this.listView = listView;
    }

    @Override
    //Метод вызывается при любом изменении состояния выделения рядов
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
        Log.d(TAG, "onItemCheckedStateChanged");
        int selectedCount = listView.getCheckedItemCount();
        //Добавим количество выделенных рядов в Context Action Bar
        setSubtitle(actionMode, selectedCount);
    }

    @Override
    //Здесь надуваем CAB из xml
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        Log.d(TAG, "onCreateActionMode");
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.history_context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        Log.d(TAG, "onPrepareActionMode");
        return false;
    }

    @Override
    //Вызывается при клике на любой Item из СAB
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.cab_delete) {
            DBWorker.dbWorker.delFromDB(listView.getCheckedItemIds());
        } else {
            Toast.makeText(listView.getContext(), "Option coming soon=)", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
    }

    private void setSubtitle(ActionMode mode, int selectedCount) {
        switch (selectedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            default:
                mode.setTitle(String.valueOf(selectedCount)+ " selected");
                break;
        }
    }

}
