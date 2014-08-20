package com.joyfulmath.supercalculater;

import java.util.ArrayList;

import com.joyfulmath.supercalculater.Engine.CaculaterEngine.EngineResultParam;
import com.joyfulmath.supercalculater.Engine.GeneralEngineType.MonthLoanResult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RealResultAdapter extends BaseAdapter {

	private static final String TAG = "SuperCalculater.RealResultAdapter";
	private EngineResultParam mEngineResult = null;
	ArrayList<MonthLoanResult> result = null;
	private Context mContext = null;

	public RealResultAdapter(EngineResultParam param, Context context) {
		mContext = context;
		setResult(param);
	}

	public void setResult(EngineResultParam param) {
		mEngineResult = param;
		if (mEngineResult != null) {
			result = mEngineResult.result;
		} else {
			result = new ArrayList<MonthLoanResult>();
			result.clear();
		}
		Log.i(TAG, "[setResult]:result size:" + result.size());

	}

	@Override
	public int getCount() {
//		Log.i(TAG, "getCount");
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
//		Log.i(TAG, "getItem");

		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
//		Log.i(TAG, "getItemId");

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		Log.i(TAG, "[getView]:" + position);
		ViewHolder viewHolder = null;
		MonthLoanResult monthResult = result.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_re_result_list_item, null);
			viewHolder.indexView = (TextView) convertView
					.findViewById(R.id.result_item_index);
			viewHolder.interestView = (TextView) convertView
					.findViewById(R.id.result_item_interest);
			viewHolder.captialView = (TextView) convertView
					.findViewById(R.id.result_item_captial);

			viewHolder.returnView = (TextView) convertView
					.findViewById(R.id.result_item_return);
			viewHolder.remainView = (TextView) convertView
					.findViewById(R.id.result_item_remain);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.indexView.setText(String.valueOf(position));
		viewHolder.returnView.setText(String.valueOf(monthResult.mReturn));
		viewHolder.interestView.setText(String.valueOf(monthResult.mInterest));
		viewHolder.captialView.setText(String.valueOf(monthResult.mReturnCaptial));
		viewHolder.remainView.setText(String.valueOf(monthResult.mLastCaptial));
		return convertView;
	}

	private class ViewHolder {
		TextView indexView;
		TextView interestView;
		TextView captialView;
		TextView returnView;
		TextView remainView;
	}
}
