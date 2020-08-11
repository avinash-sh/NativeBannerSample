package com.inmobi.nativebanner.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inmobi.nativebanner.R;
import com.inmobi.nativebanner.ui.main.ItemFragment.OnListFragmentInteractionListener;
import com.inmobi.nativebanner.ui.main.dummy.DummyContent.DummyItem;
import com.inmobi.blend.ads.AdConstants;
import com.inmobi.blend.ads.BlendAdManager;
import com.inmobi.blend.ads.ui.BaseAdEnabledAdapter;
import com.inmobi.blend.ads.ui.BlendNativeBannerAdView;
import com.inmobi.blend.ads.ui.viewholder.AdsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends BaseAdEnabledAdapter {

    private final List<DummyDataType> mValues;
    private final OnListFragmentInteractionListener mListener;

    @IntDef({DummyDataTypes.TYPE_ITEM, DummyDataTypes.TYPE_AD})
    public @interface DummyDataTypes {
        int TYPE_ITEM = 1;
        int TYPE_AD = 2;
    }

    public MyItemRecyclerViewAdapter(Context context, List<DummyDataType> items, OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
        mValues.addAll(items);
        addAdViews(context);
    }

    /** Add views to 4th and 14th position in the list view
     *
     *  4th is small banner
     *  14th is the medium banner
     * @param context
     */
    private void addAdViews(Context context) {
        setBannerAdViews(context, 4, BlendAdManager.AdType.SMALL_BANNER, AdConstants.TODAY_BANNER_TOP_NAME);
        setBannerAdViews(context, 21, BlendAdManager.AdType.MEDIUM_BANNER, AdConstants.FORECASTDISCUSSION_MREC_NAME);
    }

    private void setBannerAdViews(Context context, int position, @BlendAdManager.AdType String adType, String bannerName) {
        DummyDataType dataType = mValues.get(position);
        if (dataType instanceof AdsViewHolder) {
            CustomAdItem adItem = (CustomAdItem) dataType;
            adItem.getAdView().destroy();
            mValues.remove(position);
        }
        BlendNativeBannerAdView adView = new BlendNativeBannerAdView(context, bannerName, adType);
        adViews.add(adView);
        CustomAdItem adItem = new CustomAdItem(adView);
        mValues.add(position, adItem);
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DummyDataTypes.TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_item, parent, false);
                return new ViewHolder(view);
            case DummyDataTypes.TYPE_AD:
                View adView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.blend_ad_container, parent, false);
                return new AdsViewHolder(adView);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DummyDataType dataType = mValues.get(position);
        switch (dataType.getType()) {
            case DummyDataTypes.TYPE_ITEM:
                ViewHolder viewHolder = (ViewHolder)holder;
                DummyItem dummyItem = (DummyItem) dataType;
                viewHolder.mItem = dummyItem;
                viewHolder.mIdView.setText(dummyItem.id);
                viewHolder.mContentView.setText(dummyItem.content);

                viewHolder.mView.setOnClickListener(v -> {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(viewHolder.mItem);
                    }
                });
                break;
            case DummyDataTypes.TYPE_AD:
                AdsViewHolder adsViewHolder = (AdsViewHolder) holder;
                CustomAdItem adItem = (CustomAdItem)dataType;
                adsViewHolder.bindView(adItem);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
