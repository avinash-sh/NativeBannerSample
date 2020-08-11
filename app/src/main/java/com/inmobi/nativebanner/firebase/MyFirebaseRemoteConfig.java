package com.inmobi.nativebanner.firebase;

import com.inmobi.nativebanner.R;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.inmobi.blend.ads.firebase.InitFirebaseRemoteConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MyFirebaseRemoteConfig implements InitFirebaseRemoteConfig {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    @Inject
    InitFirebaseAnalytics initFirebaseAnalytics;

    @Inject
    MyFirebaseRemoteConfig() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings.Builder()
                .build());
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_remote_config);
        firebaseRemoteConfig.fetchAndActivate();
    }

    @Override
    public <T> T getValue(String key, Class<T> classType) {
        T value = null;
        if (Boolean.class.equals(classType)) {
            value = classType.cast(firebaseRemoteConfig.getBoolean(key));
        } else if (Long.class.equals(classType)) {
            value = classType.cast(firebaseRemoteConfig.getLong(key));
        } else if (String.class.equals(classType)) {
            value = classType.cast(firebaseRemoteConfig.getString(key));
        } else if (Double.class.equals(classType)) {
            value = classType.cast(firebaseRemoteConfig.getDouble(key));
        }
        return value;
    }
}
