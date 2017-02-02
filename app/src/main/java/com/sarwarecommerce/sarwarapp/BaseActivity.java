package com.sarwarecommerce.sarwarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.sarwarecommerce.sarwarapp.callbacks.ActionBarModifier;
import com.sarwarecommerce.sarwarapp.callbacks.HandlesBack;
import com.sarwarecommerce.sarwarapp.gson.GsonParceler;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

import static mortar.MortarScope.buildChild;
import static mortar.MortarScope.findChild;

public class BaseActivity extends AppCompatActivity implements Flow.Dispatcher {

    private FlowDelegate flowDelegate;
    private PathContainerView container;
    private HandlesBack containerAsBackTarget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        GsonParceler parceler = new GsonParceler(new Gson());
        @SuppressWarnings("deprecation") FlowDelegate.NonConfigurationInstance nonConfig =
                (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();
        container = (PathContainerView) findViewById(R.id.container);
        containerAsBackTarget = (HandlesBack) container;
        flowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler,
                History.single(null), this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return flowDelegate.onRetainNonConfigurationInstance();
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowDelegate.onNewIntent(intent);
    }

    @Override protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override protected void onPause() {
        flowDelegate.onPause();
        super.onPause();
    }

    @Override protected void onDestroy() {
        if (isFinishing()) {
            // activityScope may be null in case isWrongInstance() returned true in onCreate()
            MortarScope activityScope = findChild(getApplicationContext(), getScopeName());
            if (activityScope != null) {
                activityScope.destroy();
            }
        }

        super.onDestroy();
    }


    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowDelegate.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override public Object getSystemService(String name) {
        if (flowDelegate != null) {
            Object flowService = flowDelegate.getSystemService(name);
            if (flowService != null) {
                return flowService;
            }
        }

        MortarScope activityScope = findChild(getApplicationContext(), getScopeName());
        if (activityScope == null) {
            activityScope =
                    buildChild(getApplicationContext()).withService(BundleServiceRunner.SERVICE_NAME,
                            new BundleServiceRunner()).build(getScopeName());
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }


    @Override public void onBackPressed() {
        if (containerAsBackTarget.onBackPressed()) return;
        if (flowDelegate.onBackPressed()) return;
        super.onBackPressed();
    }

    @Override public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        container.dispatch(traversal, new Flow.TraversalCallback() {
            @Override public void onTraversalCompleted() {
                callback.onTraversalCompleted();
                invalidateOptionsMenu();
                if (container.getCurrentChild() instanceof ActionBarModifier) {
                    setTitle(((ActionBarModifier) container.getCurrentChild()).getTitle());
                }
            }
        });
    }

    private String getScopeName() {
        return getClass().getName();
    }
}
