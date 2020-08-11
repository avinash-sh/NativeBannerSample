package com.inmobi.nativebanner.firebase;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InitFirebaseAnalytics {

    @Inject
    InitFirebaseAnalytics(Context context) {
        FirebaseAnalytics.getInstance(context);
    }

}