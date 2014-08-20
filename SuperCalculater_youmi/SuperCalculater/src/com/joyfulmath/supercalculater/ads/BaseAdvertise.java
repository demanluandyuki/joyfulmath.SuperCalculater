package com.joyfulmath.supercalculater.ads;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdvertise {
	
	public static final int TYPE_AD_APPLIST = 1;
	public static final int TYPE_AD_INSERT = 2;
	public static final int TYPE_AD_BANNER = 3;
	Context mContext;

	public BaseAdvertise(Context context)
	{
		mContext = context;
	}
	
	public abstract void init();
	public abstract void exit();
	public abstract void preLoad(int type);	
	public abstract boolean isLoaded(int type);
	public abstract View showAppWidget();
	public abstract void showAppWall();
	public abstract View showBanner(ViewGroup container);
}
