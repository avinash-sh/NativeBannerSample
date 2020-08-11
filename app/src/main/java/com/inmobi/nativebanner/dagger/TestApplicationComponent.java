package com.inmobi.nativebanner.dagger;

import com.inmobi.nativebanner.TestApplication;
import com.inmobi.blend.ads.di.BlendAdsComponent;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdView;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdViewInternal;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {TestAppModule.class, TestApplicationModule.class, AndroidSupportInjectionModule.class})
public interface TestApplicationComponent extends BlendAdsComponent {

    void inject(TestApplication application);

    void inject(BlendNativeBannerAdView nativeBannerAdView);

    void inject(BlendNativeBannerAdViewInternal nativeBannerAdView);

}
