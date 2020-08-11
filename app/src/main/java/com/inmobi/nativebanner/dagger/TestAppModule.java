package com.inmobi.nativebanner.dagger;

import android.content.Context;

import com.inmobi.nativebanner.TestApplication;
import com.inmobi.nativebanner.firebase.MyFirebaseRemoteConfig;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.inmobi.blend.ads.BlendAdManager;
import com.inmobi.blend.ads.di.BlendAdsModule;
import com.inmobi.blend.ads.firebase.InitFirebaseRemoteConfig;
import com.inmobi.blend.ads.utils.BlendAdUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule implements BlendAdsModule {

    private final TestApplication appContext;

    public TestAppModule(TestApplication testApplication) {
        this.appContext = testApplication;
    }

    @Override
    @Provides
    @Singleton
    public Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    public InitFirebaseRemoteConfig provideFirebaseConfig(MyFirebaseRemoteConfig remoteConfig) {
        return remoteConfig;
    }

    @Override
    @Provides
    @Singleton
    public BlendAdUtils providePrefUtils(Context context) {
        return new BlendAdUtils() {
            @Override
            public boolean isAdsEnable() {
                return true;
            }

            @Override
            public boolean isCCPAFlagOPTOUT() {
                return false;
            }

            @Override
            public boolean isAdsEnable(Context context) {
                return true;
            }
        };
    }

    @Override
    @Provides
    @Singleton
    public BlendAdManager.CustomTargetingListener provideTargetListener() {
        return new BlendAdManager.CustomTargetingListener() {
            @Override
            public void addCustomTargetingParams(PublisherAdRequest.Builder adRequestBuilder, @BlendAdManager.AdType String adType) {

            }
        };
    }
}
