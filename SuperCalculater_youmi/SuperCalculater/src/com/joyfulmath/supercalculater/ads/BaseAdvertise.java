package com.joyfulmath.supercalculater.ads;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdvertise {
	
	public static final int TYPE_AD_APPLIST = 1;
	public static final int TYPE_AD_INSERT = 2;
	public static final int TYPE_AD_BANNER = 3;
	public static final int MSG_SHOW_BANNER = 10;
	Context mContext;
	ViewGroup mViewGrop = null;
	AdsHandle mhandle = null;
	public BaseAdvertise(Context context)
	{
		mContext = context;
		mhandle = new AdsHandle();
	}
	
	public abstract void init();
	public abstract void exit();
	public abstract void preLoad(int type);	
	public abstract boolean isLoaded(int type);
	public abstract View showAppWidget();
	public abstract void showAppWall();
	public abstract void setBanner(ViewGroup container);
	public abstract void showBanner();
	
	public void showBannerAsync()
	{
		Message msg = mhandle.obtainMessage();
		msg.what = MSG_SHOW_BANNER;
		mhandle.sendMessage(msg);
	}
	
	public  class AdsHandle extends Handler{
		
		public AdsHandle()
		{
			super(Looper.getMainLooper());
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what)
			{
			case MSG_SHOW_BANNER:
				showBanner();
				break;
			}
		}
		
		
		
	}
}
