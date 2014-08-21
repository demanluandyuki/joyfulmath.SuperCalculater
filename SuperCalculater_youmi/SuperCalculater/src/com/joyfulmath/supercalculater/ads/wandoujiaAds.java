package com.joyfulmath.supercalculater.ads;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

//import com.wandoujia.ads.sdk.AdListener;
//import com.wandoujia.ads.sdk.Ads;
//import com.wandoujia.ads.sdk.loader.Fetcher.AdFormat;
//import com.wandoujia.ads.sdk.widget.AdBanner;

/*this class is define the interface of ads for wandoujiaAds
 * 
 * */
public class wandoujiaAds extends BaseAdvertise 
//implements AdListener 
{

	private static final String AppKey_ID = "100011966";
	private static final String SECRET_KEY = "b770aab47ae23c5c19616ede91bc29a7";
	private static final String BANNER_ID = "84bc1791a0b02f748a5137514659049b";
	private static final String APPLIST_ID = "78273eef03875afc9b082c1bcaa088a6";
	private static final String INSERT_ID = "fc89af0658a5cfda9cb880b4b1b206a7";
	private static final String TAG = "SuperCalculater.wandoujiaAds";

	public wandoujiaAds(Context context) {
		super(context);
	}

	@Override
	public void init() {
		Log.i(TAG, "[init]");
//		try {
//			Ads.init(mContext, AppKey_ID, SECRET_KEY);
//		} catch (Exception e) {
//			Log.w(TAG, "[init]:wrong " + e.getMessage());
//			// e.printStackTrace();
//		}
	}

//	@Override
//	public void onAdLoaded() {
//		// TODO Auto-generated method stub
//		Log.i(TAG, "onAdLoaded");
////		if(Ads.getUpdateAdCount("LIST_APP")>0)
////		{
////			
////		}
//	}

//	@Override
	public void preLoad(int type) {
		Log.i(TAG, "[preLoad] type:" + type);
//		switch (type) {
//		case TYPE_AD_APPLIST:
//			Ads.preLoad(mContext, AdFormat.appwall, "LIST_APP", APPLIST_ID,
//					this);
//			break;
//		case TYPE_AD_INSERT:
//			Ads.preLoad(mContext, AdFormat.interstitial, "INSERT_APP",
//					INSERT_ID, this);
//			break;
//		}
	}

//	@Override
	public boolean isLoaded(int type) {
		// TODO Auto-generated method stub
		boolean result = false;
//		switch (type) {
//		case TYPE_AD_APPLIST:
//			result = Ads.isLoaded(AdFormat.appwall, APPLIST_ID);
//			break;
//		case TYPE_AD_INSERT:
//			result = Ads.isLoaded(AdFormat.interstitial, INSERT_ID);
//			break;
//		}

		return result;
	}

	@Override
	public View showAppWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showAppWall() {
//		if (isLoaded(TYPE_AD_APPLIST))
//		{
//			Log.i(TAG, "[showAppWall ]");
//			Ads.showAppWall(mContext,APPLIST_ID);// should running in UI thread.
//		}
	}

	@Override
	public void setBanner(ViewGroup container) {
//		AdBanner banner = Ads.showBannerAd(mContext, container, BANNER_ID);
//		return banner.getView();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showBanner() {
		// TODO Auto-generated method stub
		
	}
}
