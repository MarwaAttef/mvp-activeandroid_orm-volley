package com.example.marwa.enozomtask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onResume() {
        super.onResume();
        setToolBarSearchView();
        setToolbar();
        startFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboardIfShown();
//        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    protected void hideKeyboardIfShown() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // Hide The keyboard if showing
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected abstract void startFragment();
    protected abstract void setToolBarSearchView();
    protected abstract void setToolbar();


}