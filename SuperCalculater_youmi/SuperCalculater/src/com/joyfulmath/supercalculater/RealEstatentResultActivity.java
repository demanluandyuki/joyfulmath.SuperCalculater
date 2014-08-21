package com.joyfulmath.supercalculater;

import java.util.ArrayList;

import com.joyfulmath.supercalculater.Engine.CaculaterEngine;
import com.joyfulmath.supercalculater.Engine.CaculaterEngine.EngineParams;
import com.joyfulmath.supercalculater.Engine.CaculaterEngine.EngineResultParam;
import com.joyfulmath.supercalculater.Engine.CalculaterEngineListener;
import com.joyfulmath.supercalculater.Engine.GeneralEngineType.MonthLoanResult;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RealEstatentResultActivity extends Activity implements CalculaterEngineListener {
	public final static String ACTION = "android.intent.action.calculater.realestatent.result";
	public final static String KEY_RESULT = "result";
	public final static String KEY_PARAM = "param";
	private static final String TAG = "SuperCalculater.RealEstatentResultActivity";
	public EngineResultParam mResult = null;
	public EngineParams mParam = null;
	private ListView mListView = null;
	private RealResultAdapter mAdapter = null;
	private RealHandle handle = null;
	private LinearLayout mTitleLayout =null;
	private ArrayList<ViewHolder> mViewHolder = null;
	private TextView mLoanTotalView = null;
	private TextView mRepayTotalView = null;
	private TextView mLoanMonthView = null;
	private TextView mTotalInterestView = null;
	private TextView mRateView = null;
	private TextView mFirstPayView = null;
	private TextView mRateTitleView = null;
	private ProgressDialog mProdialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "[onCreate]");
		setContentView(R.layout.activity_realestatent_result);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();		
		mParam = (EngineParams) bundle.getParcelable(KEY_PARAM);
		mListView = (ListView) this.findViewById(R.id.list_result);
		mAdapter = new RealResultAdapter(mResult, this);
		handle = new RealHandle();
		mListView.setAdapter(mAdapter);
		mTitleLayout = (LinearLayout) this.findViewById(R.id.result_title);
		mProdialog = new ProgressDialog(this);
		StartCalculate(mParam);

	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "[onDestroy]");
	}
	
	private class RealHandle extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			Log.i(TAG, "[handleMessage]");
			dismissProdialog();
			showTitleResult(mResult);
//			mListView.invalidate();
			mListView.setAdapter(mAdapter);
			mListView.postInvalidate();
		}
		
	}
	
	public void StartCalculate(EngineParams mParam )
	{
		Log.i(TAG, "[StartCalculate]");
		CaculaterEngine.setListener(this);
		CaculaterEngine.StartRealEstatentTransfer(mParam);
		showProdialog();
	}

	public void showTitleResult(EngineResultParam result) {
		// TODO Auto-generated method stubx
		if(mViewHolder!=null && result!=null)
		{
			int total_loan = mParam.commercial_loan+mParam.gongjj_loan;
			mLoanTotalView.setText(String.valueOf((total_loan)/10000)+"W");
			
			double total_repay = total_loan+result.totalInterest;
			total_repay = MonthLoanResult.transferDoubleWithByte(2, total_repay);
			mRepayTotalView.setText(String.valueOf(total_repay));
			
			mLoanMonthView.setText(String.valueOf(result.result.size()));
			
			mTotalInterestView.setText(String.valueOf(result.totalInterest));
			
			mFirstPayView.setText(String.valueOf(result.firstmonth));
			
			double rate = 0;
			if(mParam.commercial_loan!=0)
			{
				rate = mParam.commercial_rate*100;
				mRateTitleView.setText(R.string.header_rate_commercial);
//				mRateView.setText(String.valueOf(mParam.commercial_rate*100)+"%");
			}
			else
			{
				rate = mParam.gongjj_rate*100;
				mRateTitleView.setText(R.string.header_rate_gongjj);
//				mRateView.setText(String.valueOf(mParam.gongjj_rate*100)+"%");
			}
			rate = MonthLoanResult.transferDoubleWithByte(2, rate);
			mRateView.setText(String.valueOf(rate)+"%");
			mTitleLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onCalculaterResult(EngineResultParam calcResult) {
		if(calcResult!=null)
		{
			Log.i(TAG, "[onCalculaterResult] calcResult:"+calcResult.totalInterest);
			if(mViewHolder == null)
			{
				mViewHolder = new ArrayList<ViewHolder>(6);
				mViewHolder.clear();
				if(mTitleLayout!=null)
				{
					mLoanTotalView = (TextView) mTitleLayout.findViewById(R.id.text_header_loan_total_content);
					mRepayTotalView = (TextView) mTitleLayout.findViewById(R.id.text_header_return_total_content);
					mLoanMonthView = (TextView) mTitleLayout.findViewById(R.id.text_header_loan_month_content);
					mTotalInterestView = (TextView) mTitleLayout.findViewById(R.id.text_header_total_interest_content);
					mRateView = (TextView) mTitleLayout.findViewById(R.id.text_head_loan_rate_content);
					mFirstPayView = (TextView) mTitleLayout.findViewById(R.id.text_header_repay_month_content);
					mRateTitleView = (TextView) mTitleLayout.findViewById(R.id.text_head_loan_rate);
				}
			}
						
			mAdapter.setResult(calcResult);
			mResult = calcResult;
			Message msg = handle.obtainMessage();
			handle.sendMessage(msg);
		}
	}
	
	public class ViewHolder{
		public TextView startView;
		public TextView endView;
	}
	
	public void showProdialog()
	{
		mProdialog.setMessage(this.getResources().getString(R.string.progress_dialog_title));
		mProdialog.show();
	}
	
	public void dismissProdialog()
	{
		mProdialog.dismiss();
	}
	
}
