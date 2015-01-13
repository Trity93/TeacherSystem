package com.ts.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;

import com.common.Info;
import com.common.Leave;
import com.common.OverTime;
import com.common.Wage;
import com.common.Work;
import com.teachersystem.R;

public class Tools {
	public static void makeToast(Context context, int rstring) {
		Toast.makeText(context, rstring, Toast.LENGTH_LONG).show();
	}

	public static void makeToast(Context context, String rstring) {
		Toast.makeText(context, rstring, Toast.LENGTH_LONG).show();
	}

	// 捕获磁盘访问或者网络访问中与主进程之间交互产生的问题
	public static void startStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
	}

	// 得到基本信息表
	public static String[] getInfo(Info mInfo) {
		return new String[] { mInfo.getEmpId(), mInfo.getEmpName(),
				mInfo.getEmpSex(), mInfo.getEmpage(), mInfo.getEmpDept() };
	}

	public static String[] getWork(Work mInfo) {
		return new String[] { mInfo.getEmpId(), mInfo.getDayNum() + "",
				mInfo.getStartTime() + "", mInfo.getEndTime() + "" };
	}

	public static String[] getOverTime(OverTime mInfo) {
		return new String[] { mInfo.getEmpId(), mInfo.getOverDate() + "" };
	}

	public static String[] getLeave(Leave mInfo) {
		return new String[] { mInfo.getEmpId(), mInfo.getLeaveDate() + "" };
	}

	public static String[] getWage(Wage mInfo) {
		return new String[] { mInfo.getEmpId(), mInfo.getWorkNote() + "",
				mInfo.getLeaveNote() + "", mInfo.getOverNote() + "",
				mInfo.getEmpWage() + "" };
	}
	//为listview得到需要的条件
	public static List<Map<String, Object>> getStringData(String[] titles,
			String[] contents) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < titles.length; i++) {
			map = new HashMap<String, Object>();
			map.put("title", titles[i]);
			map.put("info", contents[i]);
			list.add(map);
		}
		return list;
	}

	// 判断是否联网
	public static boolean connectInternet(Context context) {
		// 网络管理类，可以判断是否能上网，以及网络类型
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netWInfo = cm.getActiveNetworkInfo();
		if (netWInfo != null) {
			return true;
		} else {
			Toast.makeText(context.getApplicationContext(), R.string.network_error,
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}
