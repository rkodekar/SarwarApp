package com.sarwarecommerce.sarwarapp.callbacks;

import android.view.Menu;

/**
 * Created by mkodekar on 02/02/2017.
 */

public interface ActionBarModifier {
    boolean onPrepareOptionsMenu(Menu menu);

    String getTitle();
}
