package com.sarwarecommerce.sarwarapp.support;

import android.view.View;

import com.sarwarecommerce.sarwarapp.callbacks.HandlesBack;

import flow.Flow;

/**
 * Created by mkodekar on 02/02/2017.
 */

public class BackSupport {

    public static boolean onBackPressed(View childView) {
        if (childView instanceof HandlesBack) {
            if (((HandlesBack) childView).onBackPressed()) {
                return true;
            }
        }
        return Flow.get(childView).goBack();
    }

    private BackSupport() {
    }
}
