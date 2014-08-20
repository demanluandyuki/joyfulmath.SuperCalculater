package com.joyfulmath.supercalculater.Engine;

import java.util.ArrayList;

import com.joyfulmath.supercalculater.Engine.GeneralEngineType.MonthLoanResult;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CaculaterEngine {

	public final static String TAG = "SuperCalculater.Engine";

	public final static int MONTH_LENGTH = 12;
	public final static int LOAN_WANG = 10000;
	public final static double month_length_l = 0.08333333;
	private static CalculaterEngineListener mListener = null;

	private CaculaterEngine() {
	};

	public static void setListener(CalculaterEngineListener listener) {
		mListener = listener;
	}

	public static void StartRealEstatentTransfer(EngineParams params) {
		new Thread(new RealEstatentTask(params)).start();
	}
	
	
	public static void readEstatentCalcCapitalAndInterest(EngineParams mParam)
	{
		ArrayList<MonthLoanResult> mCommerCialResult = null;
		double mTotalInterest = 0;
		if (mParam.commercial_loan != 0 && mParam.commercial_rate != 0) {
			EqualCapitalandInterestEngineType eqEngine = new EqualCapitalandInterestEngineType(
					mParam.commercial_loan, mParam.year * MONTH_LENGTH,
					mParam.commercial_rate / MONTH_LENGTH);
			mCommerCialResult = eqEngine.onStartCalculater();
			mTotalInterest += eqEngine.mTotalInterest;
			// notifyResult(firstMonth, eqEngine.mTotalInterest,
			// mCommerCialResult);
			Log.i(TAG, "[readEstatentCalcCapitalAndInterest] commercial_loan " + eqEngine.mTotalInterest);

		}

		ArrayList<MonthLoanResult> mGongjjResult = null;
		if (mParam.gongjj_loan != 0 && mParam.gongjj_rate != 0) {
			EqualCapitalandInterestEngineType eqEngine = new EqualCapitalandInterestEngineType(
					mParam.gongjj_loan, mParam.year * MONTH_LENGTH,
					mParam.gongjj_rate / MONTH_LENGTH);
			mGongjjResult = eqEngine.onStartCalculater();
			mTotalInterest += eqEngine.mTotalInterest;
			Log.i(TAG, "[readEstatentCalcCapitalAndInterest] gongjj_loan " + eqEngine.mTotalInterest);

		}

		ArrayList<MonthLoanResult> mTotalResult = MonthLoanResult
				.addResult(mCommerCialResult, mGongjjResult);
		if (mTotalResult != null) {
			mTotalInterest = MonthLoanResult.transferDoubleWithByte(GeneralEngineType.DOUBLE_VALUE_BIT, mTotalInterest);
			EngineResultParam calcResult = new EngineResultParam(
					mTotalResult.get(0).mReturn, mTotalInterest,
					mTotalResult);
			notifyResult(calcResult);
		} else {
			notifyResult(null);
		}
	}
	
	public static void readEstatentCalcCapital(EngineParams mParam)
	{
		ArrayList<MonthLoanResult> mCommerCialResult = null;
		double mTotalInterest = 0;
		if (mParam.commercial_loan != 0 && mParam.commercial_rate != 0) {
			EqualCapitalEngineType eqEngine = new EqualCapitalEngineType(
					mParam.commercial_loan, mParam.year * MONTH_LENGTH,
					mParam.commercial_rate / MONTH_LENGTH);
			mCommerCialResult = eqEngine.onStartCalculater();
			mTotalInterest += eqEngine.mTotalInterest;
			Log.i(TAG, "[readEstatentCalcCapital] commercial_loan " + eqEngine.mTotalInterest);
		}
		
		ArrayList<MonthLoanResult> mGongjjResult = null;
		if (mParam.gongjj_loan != 0 && mParam.gongjj_rate != 0) {
			EqualCapitalEngineType eqEngine = new EqualCapitalEngineType(
					mParam.gongjj_loan, mParam.year * MONTH_LENGTH,
					mParam.gongjj_rate / MONTH_LENGTH);
			mGongjjResult = eqEngine.onStartCalculater();
			mTotalInterest += eqEngine.mTotalInterest;
			Log.i(TAG, "[readEstatentCalcCapitalAndInterest] gongjj_loan " + eqEngine.mTotalInterest);
		}
		
		ArrayList<MonthLoanResult> mTotalResult = MonthLoanResult
				.addResult(mCommerCialResult, mGongjjResult);
		if (mTotalResult != null) {
			mTotalInterest = MonthLoanResult.transferDoubleWithByte(GeneralEngineType.DOUBLE_VALUE_BIT, mTotalInterest);
			EngineResultParam calcResult = new EngineResultParam(
					mTotalResult.get(0).mReturn, mTotalInterest,
					mTotalResult);
			notifyResult(calcResult);
		} else {
			notifyResult(null);
		}
		
	}
	
	public static void realEstatentTransfer(EngineParams mParam) {
		int style = mParam.loan_style;
		Log.i(TAG, "[realEstatentTransfer] style " + style);
		switch (style) {
		case GeneralEngineType.LOAN_STYLE_AVERAGE_CAPITAL_PLUS_INTEREST:
			readEstatentCalcCapitalAndInterest(mParam);
			break;
		case GeneralEngineType.LOAN_STYLE_AVERAGE_CAPITAL:
			readEstatentCalcCapital(mParam);
			break;
		}

	}

	public static void notifyResult(EngineResultParam calcResult) {
		if (mListener != null) {
			mListener.onCalculaterResult(calcResult);
		}
	}

	public static class RealEstatentTask implements Runnable {
		private EngineParams mParam = null;

		public RealEstatentTask(EngineParams param) {
			mParam = param;
		}

		@Override
		public void run() {
			realEstatentTransfer(mParam);
		}

	}

	public static class EngineParams implements Parcelable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 101L;
		public int year = 0;
		public double commercial_rate = 0.0;
		public int commercial_loan = 0;
		public double gongjj_rate = 0.0;
		public int gongjj_loan = 0;
		public int loan_style = 0;

		public static final Parcelable.Creator<EngineParams> CREATOR = new Parcelable.Creator<EngineParams>() {

			@Override
			public EngineParams createFromParcel(Parcel source) {
				// TODO Auto-generated method stub
				EngineParams param = new EngineParams(source);
				return param;
			}

			@Override
			public EngineParams[] newArray(int size) {
				// TODO Auto-generated method stub
				EngineParams[] params = new EngineParams[size];
				return params;
			}
		};

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public double getCommercial_rate() {
			return commercial_rate;
		}

		public void setCommercial_rate(double commercial_rate) {
			this.commercial_rate = commercial_rate;
		}

		public int getCommercial_loan() {
			return commercial_loan;
		}

		public void setCommercial_loan(int commercial_loan) {
			this.commercial_loan = commercial_loan;
		}

		public double getGongjj_rate() {
			return gongjj_rate;
		}

		public void setGongjj_rate(double gongjj_rate) {
			this.gongjj_rate = gongjj_rate;
		}

		public int getGongjj_loan() {
			return gongjj_loan;
		}

		public void setGongjj_loan(int gongjj_loan) {
			this.gongjj_loan = gongjj_loan;
		}

		public int getLoan_style() {
			return loan_style;
		}

		public void setLoan_style(int loan_style) {
			this.loan_style = loan_style;
		}

		public EngineParams(int year, double commercial_rate,
				int commercial_loan, double gongjj_rate, int gongjj_loan,
				int loan_style) {
			this.year = year;
			this.commercial_rate = commercial_rate;
			this.commercial_loan = commercial_loan;
			this.gongjj_rate = gongjj_rate;
			this.gongjj_loan = gongjj_loan;
			this.loan_style = loan_style;
		}

		public EngineParams(Parcel parcel) {
			readfromParcel(parcel);
		}

		public void readfromParcel(Parcel parcel) {
			Log.i(TAG, "readfromParcel");
			year = parcel.readInt();
			commercial_rate = parcel.readDouble();
			commercial_loan = parcel.readInt();
			gongjj_rate = parcel.readDouble();
			gongjj_loan = parcel.readInt();
			loan_style = parcel.readInt();
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			Log.i(TAG, "writeToParcel");
			dest.writeInt(year);
			dest.writeDouble(commercial_rate);
			dest.writeInt(commercial_loan);
			dest.writeDouble(gongjj_rate);
			dest.writeInt(gongjj_loan);
			dest.writeInt(loan_style);
		}

	}

	public static class EngineResultParam {

		private static final long serialVersionUID = -2449843421756795694L;

		public double firstmonth = 0.0;
		public double totalInterest = 0.0;
		public ArrayList<MonthLoanResult> result = null;

		public EngineResultParam(double firstmonth, double totalInterest,
				ArrayList<MonthLoanResult> result) {
			this.firstmonth = firstmonth;
			this.totalInterest = totalInterest;
			this.result = result;
		}
	}

}
