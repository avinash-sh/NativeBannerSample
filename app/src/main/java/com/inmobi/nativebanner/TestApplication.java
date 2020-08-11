package com.inmobi.nativebanner;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.inmobi.blend.ads.BlendAdsSdk;
import com.inmobi.blend.ads.di.BlendAdsComponent;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdView;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdViewInternal;
import com.inmobi.nativebanner.dagger.DaggerTestApplicationComponent;
import com.inmobi.nativebanner.dagger.TestAppModule;
import com.inmobi.nativebanner.dagger.TestApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class TestApplication extends Application implements HasAndroidInjector, BlendAdsComponent {


    @Inject
    DispatchingAndroidInjector<Object> appDispatchingAndroidInjector;

    @Inject
    BlendAdsSdk blendAdsSdk;
    private TestApplicationComponent daggerAppComponent;

    /**
     * Lazily injects the {@link DaggerTestApplicationComponent}'s members. Injection cannot be performed in {@link
     * Application#onCreate()} since {@link android.content.ContentProvider}s' {@link
     * android.content.ContentProvider#onCreate() onCreate()} method will be called first and might
     * need injected members on the application. Injection is not performed in the constructor, as
     * that may result in members-injection methods being called before the constructor has completed,
     * allowing for a partially-constructed instance to escape.
     */
    public void injectIfNecessary() {
        if (appDispatchingAndroidInjector == null) {
            synchronized (this) {
                if (appDispatchingAndroidInjector == null) {
                    initApplicationComponents();
                    if (appDispatchingAndroidInjector == null) {
                        throw new IllegalStateException(
                                "The AndroidInjector returned from applicationInjector() did not inject the "
                                        + "DaggerApplication");
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        injectIfNecessary();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        injectIfNecessary();
        return appDispatchingAndroidInjector;
    }
    private void initApplicationComponents() {
        final long time = System.currentTimeMillis();

        daggerAppComponent = DaggerTestApplicationComponent.builder()
                .testAppModule(new TestAppModule(TestApplication.this)).build();

        daggerAppComponent.inject(this);

        blendAdsSdk.init(TestApplication.this);
    }

    @Override
    public void inject(BlendNativeBannerAdView adView) {
        injectIfNecessary();
        if (null != daggerAppComponent)
            daggerAppComponent.inject(adView);
    }

    @Override
    public void inject(BlendNativeBannerAdViewInternal adView) {
        injectIfNecessary();
        if (null != daggerAppComponent)
            daggerAppComponent.inject(adView);
    }
}
