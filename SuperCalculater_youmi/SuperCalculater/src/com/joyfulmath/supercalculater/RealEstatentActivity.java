package com.joyfulmath.supercalculater;

import com.joyfulmath.supercalculater.Engine.CaculaterEngine.EngineParams;
import com.joyfulmath.supercalculater.Engine.GeneralEngineType;
import com.joyfulmath.supercalculater.ads.BaseAdvertise;
import com.joyfulmath.supercalculater.ads.YoumiAds;
import com.joyfulmath.supercalculater.ads.wandoujiaAds;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class RealEstatentActivity extends Activity implements
		android.view.View.OnClickListener, OnItemSelectedListener {

	private final static String TAG = "SuperCalculater.activity";
	private final static String gongjjRateStr = "4.5%";
	private final static double RATE_GONGJJ = 4.5;
	private final static double RATE_COMMERCIAL_BASE = 6.55;
	private final static double[] instrment = { 1.3, 1.2, 1.1, 1.0, 0.95, 0.9,
			0.85, 0.8, 0.75, 0.7 };
	private final static int ALERT_TYPE_NOLOAN = 1;
	private final static int ALERT_TYPE_SHOW_APP = 2;
	private Resources mRes;
	private Spinner mSpinnerYear;
	private Spinner mSpinnerRate;
	private TextView mRatetextview;
	private Button mBtnCal = null;
	private Button mBtnReset = null;
	private EditText mEditGongjj = null;
	private EditText mEditCommercial = null;
	private RadioGroup mStyleGroup = null;
	private AlertDialog mAltDialog = null;
	private AlertDialog mNullAlt = null;
	private int mAltType = -1;
	private static String rateString = null;
	private static String commercialRateStr;
	private static EngineParams mEnginePamap = null;
	private AlertClickListener mAlertListener = null;
	private BaseAdvertise mAds = null;
	private LinearLayout mAdsBanner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_super_calculater);
		mRes = this.getResources();
		prepareArrayAdaper();
		mAlertListener = new AlertClickListener(this);
		mAds = YoumiAds.getInstance(this.getApplicationContext());
		mAds.init();
		mAdsBanner = (LinearLayout) this.findViewById(R.id.linear_banner_1);

	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		if(!mAds.isLoaded(BaseAdvertise.TYPE_AD_APPLIST))
//		{
//			mAds.preLoad(BaseAdvertise.TYPE_AD_APPLIST);
//		}
		mAds.showBanner(mAdsBanner);
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEnginePamap = null;
		mAds.exit();
		mAds = null;
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if((keyCode == KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0)
		{
			backKeyDialog();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.super_calculater, menu);
		return true;
	}

	public void resetUIStatus() {
		Log.i(TAG, "[resetUIStatus]");
		mSpinnerYear.setSelection(19);
		mSpinnerRate.setSelection(3);
		mEditGongjj.setText("");
		mEditCommercial.setText("");
		commercialRateStr = String.valueOf(RATE_COMMERCIAL_BASE) + "%";
		StringBuilder mybuilder = new StringBuilder();
		mybuilder.append(mRes.getString(R.string.Gongjj_loan_rate));
		mybuilder.append(gongjjRateStr);
		mybuilder.append("\n");
		mybuilder.append(mRes.getString(R.string.commercial_loan_rate));
		mybuilder.append(commercialRateStr);
		rateString = mybuilder.toString();
		mRatetextview.setText(rateString);
	}

	public void prepareArrayAdaper() {
		Log.i(TAG, "[prepareArrayAdaper]");
		mSpinnerYear = (Spinner) this.findViewById(R.id.loan_year_spinner);
		mSpinnerYear.setOnItemSelectedListener(this);
		mSpinnerRate = (Spinner) this.findViewById(R.id.loan_rate_spinner);
		mSpinnerRate.setOnItemSelectedListener(this);
		mRatetextview = (TextView) this.findViewById(R.id.loan_rate_textview);
		mBtnCal = (Button) this.findViewById(R.id.btn_cal_start);
		mBtnCal.setOnClickListener(this);
		mBtnReset = (Button) this.findViewById(R.id.btn_cal_reset);
		mBtnReset.setOnClickListener(this);
		mEditGongjj = (EditText) this.findViewById(R.id.gongjj_edit);
		mEditCommercial = (EditText) this.findViewById(R.id.commercial_edit);
		mStyleGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
		resetUIStatus();
	}

	public void StartTransferRealEstatentEngine(int year,
			double commercial_rate, int commercial_loan, double gongjj_rate,
			int gongjj_loan, int loan_style) {
		Log.i(TAG, "[StartTransferRealEstatentEngine] year " + year
				+ "\n commercial_rate " + commercial_rate
				+ "\n commercial_loan " + commercial_loan + "\n gongjj_rate "
				+ gongjj_rate + "\n gongjj_loan " + gongjj_loan
				+ "\n loan_style " + loan_style);

		mEnginePamap = new EngineParams(year, commercial_rate * 0.01,
				commercial_loan, gongjj_rate * 0.01, gongjj_loan, loan_style);
		// CaculaterEngine.StartRealEstatentTransfer(mEnginePamap);
		// startavtivity
		Intent intent = new Intent(this, RealEstatentResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(RealEstatentResultActivity.KEY_PARAM, mEnginePamap);
		intent.putExtras(bundle);
		Log.i(TAG, "[StartTransferRealEstatentEngine] start activity");
		this.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cal_start:
			onStartTransfer();
			break;
		case R.id.btn_cal_reset:
			resetUIStatus();
			break;
		}
	}

	void onStartTransfer() {
		int year = mSpinnerYear.getSelectedItemPosition() + 1;
		double commercial_rate = instrment[mSpinnerRate
				.getSelectedItemPosition()] * RATE_COMMERCIAL_BASE;
		String mEditGongjjText = mEditGongjj.getText().toString();
		int loan_gjj = 0;
		if (!mEditGongjjText.isEmpty()) {
			loan_gjj = Integer.valueOf(mEditGongjjText);
		}
		String mEditCommercialText = mEditCommercial.getText().toString();
		int loan_com = 0;
		if (!mEditCommercialText.isEmpty()) {
			loan_com = Integer.valueOf(mEditCommercialText);
		}
		int id = mStyleGroup.getCheckedRadioButtonId();
		int style = 0;
		if (id == R.id.radioLoanStyle2) {
			style = GeneralEngineType.LOAN_STYLE_AVERAGE_CAPITAL;
		} else {
			style = GeneralEngineType.LOAN_STYLE_AVERAGE_CAPITAL_PLUS_INTEREST;
		}
		
		if(loan_com == 0 && loan_gjj == 0)
		{
			showNoLoanAlert();
		}
		else
		{
			StartTransferRealEstatentEngine(year, commercial_rate,
					loan_com * 10000, RATE_GONGJJ, loan_gjj * 10000, style);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		if (id == R.id.loan_year_spinner) {

		} else if (id == R.id.loan_rate_spinner) {
			int index = arg0.getSelectedItemPosition();
			double commercial_rate = instrment[index] * RATE_COMMERCIAL_BASE;
			commercialRateStr = String.format("%2.2f", commercial_rate) + "%";
			StringBuilder mybuilder = new StringBuilder();
			mybuilder.append(mRes.getString(R.string.Gongjj_loan_rate));
			mybuilder.append(gongjjRateStr);
			mybuilder.append("\n");
			mybuilder.append(mRes.getString(R.string.commercial_loan_rate));
			mybuilder.append(commercialRateStr);
			rateString = mybuilder.toString();
			mRatetextview.setText(rateString);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public void backKeyDialog() {
		mAltType = ALERT_TYPE_SHOW_APP;
		if(mAltDialog == null)
		{
			mAltDialog = new AlertDialog.Builder(this)
			.setTitle(R.string.homepage_back_dialog_title)
			.setMessage(R.string.homepage_back_dialog_message)
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton(R.string.homepage_dialog_ok, mAlertListener).
			setNegativeButton(R.string.homepage_dialog_applist, mAlertListener).
			setNeutralButton(R.string.homepage_dialog_cancel,mAlertListener).
			create();
		}
		mAltDialog.show();
	}

	public void disMissDialog()
	{
		mAltDialog.dismiss();
	}
	
	public void showNoLoanAlert()
	{
		mAltType = ALERT_TYPE_NOLOAN;
		if(mNullAlt == null)
		{
			mNullAlt = new AlertDialog.Builder(this)
						.setTitle(R.string.app_name)
						.setMessage(R.string.alert_no_loan)
						.setNegativeButton(R.string.homepage_dialog_ok,mAlertListener)
						.create();
		}
		mNullAlt.show();
	}
	
	public void dismissNoLoanAlert()
	{
		mNullAlt.dismiss();
	}
	
	public void showAppWall()
	{
		mAds.showAppWall();
	}
	
	private class AlertClickListener implements DialogInterface.OnClickListener {

		private RealEstatentActivity mReActivity = null;

		public AlertClickListener(RealEstatentActivity activity) {
			mReActivity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			if(mAltType == ALERT_TYPE_SHOW_APP)
			{
				mReActivity.disMissDialog();
				Log.i(TAG, "AlertClickListener.onclick which:"+which);
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					mReActivity.finish();
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					mReActivity.showAppWall();
					break;
				}
			}
			else if(mAltType == ALERT_TYPE_NOLOAN)
			{
				mReActivity.dismissNoLoanAlert();
			}

		}

	}
}
