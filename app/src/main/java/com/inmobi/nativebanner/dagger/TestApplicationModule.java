package com.inmobi.nativebanner.dagger;

import com.inmobi.nativebanner.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class TestApplicationModule {

    @ContributesAndroidInjector
    abstract MainActivity testMainActivity();
}


