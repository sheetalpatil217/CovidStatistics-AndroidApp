package com.example.covid_19tracker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.covid_19tracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CountryLocationRender extends DefaultClusterRenderer<CountryLocation> {
    private final Context mContext;

    public CountryLocationRender(Context context, GoogleMap map, ClusterManager<CountryLocation> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(CountryLocation item, MarkerOptions markerOptions) {
        IconGenerator iconGenerator = new IconGenerator(mContext);
        String cases = Integer.valueOf(item.getTitle())>1000? item.getTitle().substring(0,3)+"k+" :item.getTitle();
        Bitmap icon = iconGenerator.makeIcon(cases);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getCountryName() +":"+item.getTitle());
    }
    @Override
    protected void onBeforeClusterRendered(Cluster<CountryLocation> cluster, MarkerOptions markerOptions) {
        markerOptions.icon(generateBitmapDescriptorFromRes(mContext,R.drawable.ic_album_black_24dp));

    }

    public static BitmapDescriptor generateBitmapDescriptorFromRes(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    protected boolean shouldRenderAsCluster(Cluster<CountryLocation> cluster){
        return cluster.getSize() > 1;
    }

}
