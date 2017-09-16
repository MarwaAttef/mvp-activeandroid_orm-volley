package com.example.marwa.enozomtask.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/10/17.
 */

public class ActivityUtils {
    public static Boolean addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
//        boolean fragmentPopped = fragmentManager.popBackStackImmediate(fragment.getClass().getName(), 0);
//        if (!fragmentPopped) { // if not exists in the stack
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
//            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
        return true;
//        }
//        return false;
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    //TODO: use boolean to addToBackStack
    public static boolean replaceFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                     @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
//        boolean fragmentPopped = fragmentManager.popBackStackImmediate(fragment.getClass().getName(), 0);
//        if (!fragmentPopped) { // if not exists in the stack
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
//            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
        return true;
//        }
//        return false;
    }

}
