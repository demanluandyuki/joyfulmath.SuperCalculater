package com.joyfulmath.supercalculater.Engine;

import java.util.ArrayList;


import android.util.Log;

public class EqualCapitalEngineType extends GeneralEngineType {

	private static final String TAG = "SuperCalculater.EqualCapitalEngineType";

	public EqualCapitalEngineType(int loan, int months, double mRate) {
		super(loan, months, mRate);
		this.loanStyle = LOAN_STYLE_AVERAGE_CAPITAL;
	}

	@Override
	public ArrayList<MonthLoanResult> onStartCalculater() {
		Log.i(TAG, "[onStartCalculater]");
		if (this.mRate == 0.0) {
			throw new IllegalArgumentException(
					"rate is 0,no need to calculater");
		}
		ArrayList<MonthLoanResult> mLoanResult = new ArrayList<GeneralEngineType.MonthLoanResult>(
				n);
		mLoanResult.clear();

		double mReturn = 0.0;
		double mLastCaptial = mLoan;
		double mReturnCaptial = mLoan /(n*1.0);
		double mInterest = 0.0;
		for (int i = 0; i < n; i++) {
			mInterest = mLastCaptial * mRate;
			mLastCaptial = mLastCaptial - mReturnCaptial;
			mReturn = mReturnCaptial + mInterest;
			MonthLoanResult mCurResult = new MonthLoanResult(mReturn,
					mLastCaptial, mReturnCaptial, mInterest);
			mCurResult.transferResultWithByte(DOUBLE_VALUE_BIT);
			mLoanResult.add(mCurResult);
			this.mTotalInterest+=mInterest;
		}
		Log.i(TAG, "[onStartCalculater] mLastCaptial:"+mLastCaptial);
		return mLoanResult;
	}

}
