package com.inmobi.nativebanner;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.inmobi.blend.ads.cache.BlendAdsViewCacheImpl;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdView;
import com.inmobi.nativebanner.ui.main.ItemFragment;
import com.inmobi.nativebanner.ui.main.SectionsPagerAdapter;
import com.inmobi.nativebanner.ui.main.dummy.DummyContent;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector, ItemFragment.OnListFragmentInteractionListener {

    @Inject
    BlendAdsViewCacheImpl blendAdsCache;

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    private BlendNativeBannerAdView adsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        blendAdsCache.buildCache();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        adsView = findViewById(R.id.native_banner);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPause() {
        adsView.pause();
        blendAdsCache.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adsView.resume();
    }

    @Override
    protected void onDestroy() {
        adsView.destroy();
        blendAdsCache.destroy();
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }
}