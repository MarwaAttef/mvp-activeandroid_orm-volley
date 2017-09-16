package com.example.marwa.enozomtask.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.marwa.enozomtask.BaseFragment;
import com.example.marwa.enozomtask.R;
import com.example.marwa.enozomtask.data.store.Store;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainFragment extends BaseFragment implements MainContract.View {
    private final String TAG = "MainFragment";
    private MainContract.Presenter presenter;
    MainAdapter adapter;
    private ProgressBar progress;
    private RecyclerView recyclerView;
  private   RelativeLayout relativeLayout;
    private List<Store> stores;
    public static MainFragment newInstance() {
        return new MainFragment();
    }


    private void getData() {
        Boolean networkCheck= isNetworkAvailable();
        if (networkCheck) {
            if (presenter != null && stores == null) {
                presenter.getStores();
            }
        } else {

            showNotConnectedAlert();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progress = (ProgressBar) root.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) root.findViewById(R.id.stores_recycle);
         relativeLayout=(RelativeLayout)root.findViewById(R.id.main_frag);



        return root;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setStores(ArrayList<Store> stores) {
        if(getActivity()!=null) {
            this.stores = stores;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new MainAdapter(getActivity(), stores);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyStoreError() {
        //  showMessage
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "No stores to show", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.show();
    }

    public void setfilter(String newText) {
        if (adapter != null) {
            adapter.filter(newText);

        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);

    }

    @Override
    public void showServerErrorMessage() {
        showServerError();
    }

    @Override
    public void showAuthenticationErrorMessage() {
        showAuthenticationError();
    }

    @Override
    public void showConnectionErrorMessage() {
        showConnectionError();
    }

    @Override
    public void showMessage(String message) {
        showMessageOf(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }
}