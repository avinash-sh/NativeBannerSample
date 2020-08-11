
#NativeBanner

BlendAd SDK allows for easily displaying ads with native and banner inventory from dfp. The SDK offers the ability to show a set of relevant ads which is basically using a dfp mediation stack to display higher cpm banners or native ads on a specific placement. 

It can internally takes care of refresh ad config from firebase, event reporting using flurry and provide callbacks to add custom targeting parameters.

This integration guide will go over what dependencies would be required before using the SDK, what customizations are available to the ads sdk and finally how to call the APIs offered by the SDK.
 
INTEGRATING THE SDK

package name=com.inmobi.blend.ads
minSdkVersion=19
targetSdkVersion=29
 
Android Manifest
The SDK requires two manifest permissions that must be provided in the manifest file of the calling application for the SDK to be fully functional.
 
Required Permissions:
Because the app is distributed as aar it automatically gets merged while manifest merging task.


Add to your app project

The SDK library is currently privately distributed, so the module must be obtained from an InMobi Github and imported as a module. 

Note: We also have a sample app created with the ads module that you can refer from the github link here 


implementation project(":blend_ads")

implementation 'androidx.appcompat:appcompat:1.1.0'
implementation 'com.google.android.material:material:1.1.0'
implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'

implementation 'androidx.multidex:multidex:2.0.0'

implementation deps.firebase.analytics
implementation deps.firebase.config
implementation 'com.google.firebase:firebase-crashlytics:17.0.1'

implementation deps.dagger.android
implementation deps.dagger.android_support
annotationProcessor deps.dagger.android_support_compiler
annotationProcessor deps.dagger.compiler



Dagger 2 

App Component or ActivitySubComponent :- It needs you to inject the app dependency graph with  BlendNativeBannerAdView as it’s tightly coupled with dagger to look for dependencies.


public interface BlendAdsComponent {

   void inject(BlendNativeBannerAdView adView);

   void inject(BlendNativeBannerAdViewInternal adView);

}




The Ad module is to provide a bridge between the native ad view and callbacks for adding any targeting information to your respective ad calls.


public interface BlendAdsModule {
   Context provideContext();
   BlendAdUtils providePrefUtils(Context context);
   BlendAdManager.CustomTargetingListener provideTargetListener();
}





 

UNDERSTANDING THE SDK API

The BlendNativeBannerAdView a custom view class that the search SDK uses to render the ads(Native+Banner) on any placement. You need to only work with creating the constructor using xml or dynamically create them for list or recyclerview.

com.inmobi.blend.ads;
class BlendNativeBannerAdView
 

static void init(Application application)
Initialize the SDK. To be called in onCreate() of your application.
  
void pause()
Needs to be paused when the view is not visible to the window. This internally disables any ad refresh calls for the ad views, pausing any adviews behaviour.
 
void resume()
Needs to be resumed when the view is visible to the window. This internally resumes any ad refresh calls for the ad views, and fetches the ad view from the cache and pre building the next cache.
 


void destroy()
 	Needs to be destroyed when the window is getting destroyed. This ensures the ad view hierarchy is cleaned up properly and destroyed.


It already supports 3 types of ad formats namely small, mrec and fullScreen ads. The default layouts are placed in the layout folders, you can replace them by creating similar layout files in your app.
 
infeed_ad_small_layout
This allows you to set the layout for small size native+banner ads. You can customize the layout by copying the layout file and making the respective changes.

infeed_ad_layout
This allows you to set the layout for mrec size native+banner ads. You can customize the layout by copying the layout file and making the respective changes.

infeed_fullscreen_ad_layout
This allows you to set the layout for fullScreen size native+banner ads. You can customize the layout by copying the layout file and making the respective changes.

Properties needs to be defined for each BlendNativeBannerAdView mentioned below:-

app:adPlacementName
Important key attribute required for reading from remote config and reading the ad data associated in the remote config json.

app:fallbackAdSize
Important key attribute required for fallback adSize in case the remoteConfig parameters are not fetched properly.
 







USING THE SDK

The Sdk requires you to initialize cache in the mainActivity of your application . Respective AdViews need to be created and added to the view hierarchy. Developers can choose to add it dynamically or you can create them in your xml files.  


Step One:
Initialize the SDK in your project’s Application onCreate() method. It internally initializes the dfp mediation with other ad network necessary libraries.


@Override
public void onCreate() {
   super.onCreate();
   blendAdsSdk.init(TestApplication.this);
}


Step Two:
Building the SDK’s Cache in your project’s MainActivity onCreate() method. 

@Inject
BlendAdsViewCacheImpl blendAdsCache;

@Override
protected void onCreate(Bundle savedInstanceState) {
   AndroidInjection.inject(this);
   blendAdsCache.buildCache();
}


@Override
protected void onPause() {
   blendAdsCache.pause();
   super.onPause();
}

@Override
protected void onDestroy() {
   blendAdsCache.destroy();
   super.onDestroy();
}

Note: Don’t try to move the initialization bit to Application OnCreate, some ad Networks specifically want an activity context. For eg - Unity.

Step Three:
Create an adview in your XML as seen below.

<com.inmobi.blend.ads.ui.BlendNativeBannerAdView
   android:id="@+id/native_banner"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:adPlacementName="today_banner_top"
   app:fallbackAdSize="small"
   android:layout_gravity="bottom"
   app:layout_constraintBottom_toBottomOf="parent" />



Create an adview dynamically, for example adding it to recyclerView

   BlendNativeBannerAdView adView = new BlendNativeBannerAdView(activity, AdConstants.ALERTS_BOTTOM_MREC, BlendAdManager.AdType.MEDIUM_BANNER);
   adViews.add(adView);
   mListData.add(adView)



Step Four:

Always ensure to call pause, resume and destroy api’s on adViews. It ensures the adViews are properly resumed, paused and destroyed to ensure the garbage collection happens properly.  These are states of ad view and need to be managed by devs properly, we do support recyclerview basic adapter.

Please refer for implementation details : 

Sticky Banner, RecyclerView, Fragment Banner and Mrec Implementations :

