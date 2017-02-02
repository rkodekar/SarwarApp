package com.sarwarecommerce.sarwarapp.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sarwarecommerce.sarwarapp.service.DaggerService;

import javax.inject.Singleton;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by mkodekar on 02/02/2017.
 */

public class SarwarApplication extends Application {

    @dagger.Module public static class Module {

        private final Context context;

        Module(Context context) {
            this.context = context;
        }

        @Provides
        @Singleton
        SharedPreferences providePreferences() {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    @Singleton @dagger.Component(modules = Module.class) public interface Component {
        SharedPreferences provideSharedPreferences();
    }

    private MortarScope rootScope;

    @Override public Object getSystemService(String name) {
        if (rootScope == null) {
            Component component = DaggerService.createComponent(Component.class, new Module(this));
            rootScope = MortarScope.buildRootScope()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build("Root");
        }
        return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
    }

    public void setRootScope(MortarScope scope) {
        if (rootScope != null) {
            rootScope.destroy();
        }
        rootScope = scope;
    }

}
