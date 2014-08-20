package com.joyfulmath.supercalculater.Engine;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.util.Log;

public abstract class GeneralEngineType {
	private static final String TAG = "SuperCalculater.GeneralEngineType";
	public final static int LOAN_STYLE_AVERAGE_CAPITAL_PLUS_INTEREST = 1;
	public final static int LOAN_STYLE_AVERAGE_CAPITAL = 2;
	public final static int DOUBLE_VALUE_BIT = 2;
	public int mLoan = 0;
	public int n = 0;//n = year*12
	public double mRate = 0.0;//month rate
	public int loanStyle = -1;
	public double mTotalInterest = 0.0;
	
	public GeneralEngineType(int loan,int months,double mRate){
		this.mLoan = loan;
		this.n = months;
		this.mRate = mRate;
	}
	
	public static class MonthLoanResult{
		public double mReturn = 0.0;
		public double mLastCaptial = 0.0;
		public double mReturnCaptial = 0.0;
		public double mInterest = 0.0;
		
		public MonthLoanResult(double dReturn,double dCaptureLast,double mReturnCaptial,double interest)
		{
			this.mReturn = dReturn;
			this.mLastCaptial = dCaptureLast;
			this.mReturnCaptial = mReturnCaptial;
			this.mInterest = interest;
		}
		
		public void transferResultWithByte(int m)
		{
			mReturn = transferDoubleWithByte(m, mReturn);
			mLastCaptial = transferDoubleWithByte(m, mLastCaptial);
			mReturnCaptial = transferDoubleWithByte(m, mReturnCaptial);
			mInterest = transferDoubleWithByte(m, mInterest);
		}
		
		public static MonthLoanResult plus(MonthLoanResult a,MonthLoanResult b)
		{
			MonthLoanResult c = new MonthLoanResult(a.mReturn+b.mReturn,
					a.mLastCaptial+b.mLastCaptial,
					a.mReturnCaptial+b.mReturnCaptial,
					a.mInterest+b.mInterest);
			c.transferResultWithByte(DOUBLE_VALUE_BIT);
			return c;
		}
		
		public static double transferDoubleWithByte(int m,double value)
		{
			BigDecimal d = new BigDecimal(value);
			double value_2 = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			Log.i(TAG, "transferDoubleWithByte "+value_2);
			return value_2;
		}
		
		public static ArrayList<MonthLoanResult> addResult(ArrayList<MonthLoanResult> result1,
				ArrayList<MonthLoanResult> result2)
		{
			boolean bResult1 = (result1 == null||result1.size() == 0);
			boolean bResult2 = (result2 == null||result2.size() == 0);
			if(bResult1 && bResult2)
			{
				return null;
			}
			else if(bResult1)
			{
				return result2;
			}
			else if(bResult2)
			{
				return result1;
			}
			
			Log.i(TAG, "[addResult] start to add");
			
			if(result1.size() == result2.size())
			{
				ArrayList<MonthLoanResult> totalResult = new ArrayList<MonthLoanResult>(result1.size());
				for(int i=0;i<result1.size();i++)
				{
					MonthLoanResult newResult = plus(result1.get(i),result2.get(i));
					totalResult.add(newResult);
				}
				Log.i(TAG, "[addResult] totalResult size:"+totalResult.size());

				return totalResult;
			}
			
			
			return null;
		}
	}
	
	public abstract ArrayList<MonthLoanResult> onStartCalculater();
}
