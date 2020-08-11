package com.inmobi.nativebanner.ui.main;

import com.inmobi.blend.ads.model.AdItem;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdView;

public class CustomAdItem extends AdItem<BlendNativeBannerAdView> implements DummyDataType {

    public CustomAdItem(BlendNativeBannerAdView adView) {
        super(adView);
    }

    @Override
    public int getType() {
        return MyItemRecyclerViewAdapter.DummyDataTypes.TYPE_AD;
    }
}
