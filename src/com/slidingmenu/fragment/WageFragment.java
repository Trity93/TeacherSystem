package com.slidingmenu.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ListView;

import com.common.OverTime;
import com.common.Wage;
import com.json.http.UrlConnectionToServer;
import com.json.utils.GsonTools;
import com.slidingmenu.adapter.TalkPersonalCollectAdapter;
import com.teachersystem.R;
import com.ts.utils.Tools;

@EFragment(R.layout.fragment_wage)
public class WageFragment extends Fragment {

	public WageFragment() {
	}

	@ViewById(R.id.wage_lv_table)
	ListView WageMsg;
	String response = null;
	String userID;
	Wage mInfo;
	String[] tableWage;
	String[] wageTitle;
	@AfterViews
	public void userIdget() {
		// 得到状态,为null，就进行跳转LoginActivity
		SharedPreferences mSharedPreferences = getActivity()
				.getApplicationContext().getSharedPreferences("EmpInfo",
						Context.MODE_PRIVATE);
		userID = mSharedPreferences.getString("userId", null);
		if (userID == null) // 为null就跳转到登录窗口
			startActivity(new Intent(getActivity(),
					com.ts.activity.LoginActivity_.class));
		// 得到资源work的标题
		Resources rs = getResources();
		wageTitle = rs.getStringArray(R.array.wage_items);
		
		if (!Tools.connectInternet(getActivity())) {
			if (mSharedPreferences.getString("wage", null) != null) {// 这个用来保存json的字符串
				response = mSharedPreferences.getString("wage", null);
				// 进行转换，并得到相应的资料
				mInfo = GsonTools.getClass(response, Wage.class);
				tableWage = Tools.getWage(mInfo);
				WageMsg.setAdapter(new TalkPersonalCollectAdapter(
						getActivity().getApplicationContext(), Tools
								.getStringData(wageTitle, tableWage)));
			}
		}
		else
			updateWork();
	}

	@Background
	public void updateWork() {
		response = new UrlConnectionToServer().postWhat(5, userID);
		if(response==null){
			return;
		}
		if (!response.equals("null")) {
			// 进行转换，并得到相应的资料
			mInfo = GsonTools.getClass(response, Wage.class);
			tableWage = Tools.getWage(mInfo);
			// 并且保存状态
			SharedPreferences mSharedPreferences = getActivity()
					.getApplicationContext().getSharedPreferences("EmpInfo",
							Context.MODE_PRIVATE);
			Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("wage", response);
			mEditor.commit();
			// 更新ui
			updateListView();
		}
	}

	@UiThread
	public void updateListView() {
		WageMsg.setAdapter(new TalkPersonalCollectAdapter(getActivity()
				.getApplicationContext(), Tools.getStringData(wageTitle,
						tableWage)));
	}
}
