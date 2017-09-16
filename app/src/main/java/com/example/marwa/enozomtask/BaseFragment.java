package com.example.marwa.enozomtask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class BaseFragment extends Fragment {

    protected final int ACTION_WIFI_SETTINGS_FLAG = 0;

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: check if onActivityResult on fragment works
        onFragmentResult(requestCode, resultCode, data);
    }

    protected void hideKeyboardIfShown() {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            // Hide The keyboard if showing
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    protected boolean isNetworkAvailable() {
        if (getActivity() != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    protected void showNotConnectedAlert() {
        if (getActivity() != null) {
//            Toast.makeText(getActivity(),"No connection",Toast.LENGTH_LONG);
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setTitle("NO Connection")
                    .setMessage(R.string.offline_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), ACTION_WIFI_SETTINGS_FLAG);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    protected void showServerError() {
        showMessageOf(getString(R.string.serverError));
    }

    protected void showAuthenticationError() {
        showMessageOf(getString(R.string.authenticationError));
    }

    protected void showConnectionError() {
        showMessageOf(getString(R.string.connectionError));
    }

    protected void showMessageOf(String message) {
        checkNotNull(message);
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    protected abstract void onFragmentResult(int requestCode, int resultCode, Intent data);
}
