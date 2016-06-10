package com.gorih.familycoffers.controller;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.gorih.familycoffers.R;


public class HistoryMultiChoiceImpl implements AbsListView.MultiChoiceModeListener {
    private AbsListView listView;


    public HistoryMultiChoiceImpl(AbsListView listView) {
        this.listView = listView;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
        int selectedCount = listView.getCheckedItemCount();
        setSubtitle(actionMode, selectedCount);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.history_context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        DBWorker.dbWorker.delFromDB(listView.getCheckedItemIds());
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
