package com.joyfulmath.supercalculater.ads;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class YoumiAds extends BaseAdvertise {

	private static final String AppKey_ID = "f17216ca7332e03b";
	private static final String SECRET_KEY = "77335dd233ca15a9";
	private static final String TAG = "SuperCalculater.YoumiAds";
	
	private static YoumiAds mInstance = null;
	
	public static YoumiAds getInstance(Context context)
	{
		if(mInstance == null)
		{
			mInstance = new YoumiAds(context);
		}
		
		return mInstance;
	}
	
	protected YoumiAds(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		AdManager.getInstance(mContext).init(AppKey_ID, SECRET_KEY, false);
		OffersManager.getInstance(mContext).onAppLaunch();
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AdManager.getInstance(mContext).setUserDataCollect(true);
			}
		}).start();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		OffersManager.getInstance(mContext).onAppExit();
		mInstance = null;
	}
	
	@Override
	public void preLoad(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLoaded(int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View showAppWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showAppWall() {
		// TODO Auto-generated method stub
		OffersManager.getInstance(mContext).showOffersWall();
	}

	@Override
	public View showBanner(ViewGroup container) {
		Log.i(TAG, "showBanner");
		// 实例化广告条
		AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
		// 将广告条加入到布局中
		container.addView(adView);
		return adView;
	}



}
