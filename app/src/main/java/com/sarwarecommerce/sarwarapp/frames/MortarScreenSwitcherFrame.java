package com.sarwarecommerce.sarwarapp.frames;

import android.content.Context;
import android.util.AttributeSet;

import com.sarwarecommerce.sarwarapp.R;
import com.sarwarecommerce.sarwarapp.factory.MortarContextFactory;
import com.sarwarecommerce.sarwarapp.paths.FramePathContainerView;
import com.sarwarecommerce.sarwarapp.paths.SimplePathContainer;

import flow.path.Path;

/**
 * Created by mkodekar on 02/02/2017.
 */

public class MortarScreenSwitcherFrame extends FramePathContainerView {
    public MortarScreenSwitcherFrame(Context context, AttributeSet attrs) {
        super(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag,
                Path.contextFactory(new MortarContextFactory())));
    }
}
