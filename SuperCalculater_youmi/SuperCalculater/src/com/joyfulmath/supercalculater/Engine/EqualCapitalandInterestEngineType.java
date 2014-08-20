package com.joyfulmath.supercalculater.Engine;

import java.util.ArrayList;

import android.util.Log;

public class EqualCapitalandInterestEngineType extends GeneralEngineType {

	private static final String TAG = "SuperCalculater.EqualCapitalandInterestEngineType";

	public EqualCapitalandInterestEngineType(int loan, int months, double mRate) {
		super(loan, months, mRate);
		this.loanStyle = LOAN_STYLE_AVERAGE_CAPITAL_PLUS_INTEREST;
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

		double midCommercial = Math.pow(1 + this.mRate, n);
		// Log.i(TAG, "midCommercial "+midCommercial);
		double mMonthReturn = mLoan * mRate * midCommercial
				/ (midCommercial - 1);
		Log.i(TAG, "[onStartCalculater] mReturn" + mMonthReturn);

		double mCurMonInterest = 0.0;
		double mCurLastCapital = mLoan;
		double mMonthCaptial = 0.0;
		for (int i = 0; i < n; i++) {
			mCurMonInterest = mCurLastCapital * mRate;
			mMonthCaptial = mMonthReturn - mCurMonInterest;
			mCurLastCapital = mCurLastCapital - mMonthCaptial;
			MonthLoanResult result = new MonthLoanResult(mMonthReturn,
					mCurLastCapital, mMonthCaptial, mCurMonInterest);
			result.transferResultWithByte(DOUBLE_VALUE_BIT);
			mLoanResult.add(result);
		}
		this.mTotalInterest = mMonthReturn*n-mLoan;
		mTotalInterest = MonthLoanResult.transferDoubleWithByte(DOUBLE_VALUE_BIT, mTotalInterest);
		//check if captial is 0.0
		Log.i(TAG, "[onStartCalculater] mCurLastCapital "+(int)mCurLastCapital);
		
		return mLoanResult;
	}

}
