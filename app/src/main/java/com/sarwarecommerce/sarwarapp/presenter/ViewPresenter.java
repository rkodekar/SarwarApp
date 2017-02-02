package com.sarwarecommerce.sarwarapp.presenter;

import android.content.Context;

import com.sarwarecommerce.sarwarapp.callbacks.ContextHolder;

import mortar.Presenter;
import mortar.bundler.BundleService;

/**
 * Created by mkodekar on 02/02/2017.
 */

public class ViewPresenter<V extends ContextHolder> extends Presenter<V> {
    @Override protected final BundleService extractBundleService(V view) {
        return BundleService.getBundleService(view.getContext());
    }

    public final Context getContext() {
        return getView().getContext();
    }
}
