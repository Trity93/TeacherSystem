package com.slidingmenu.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.common.Info;
import com.json.http.UrlConnectionToServer;
import com.json.utils.GsonTools;
import com.slidingmenu.adapter.TalkPersonalCollectAdapter;
import com.teachersystem.R;
import com.ts.utils.Tools;

@EFragment(R.layout.fragment_info)
public class InfoFragment extends Fragment {

	public InfoFragment() {
	}

	Activity mActivity;
	String userID;
	String response;
	Info mInfo;
	Button btnUpdateMsg;
	String[] tableInfo;
	String[] strTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}
	@AfterViews
	public void userIdget() {
		// 得到状态,为null，就进行跳转LoginActivity
		SharedPreferences mSharedPreferences = mActivity
				.getApplicationContext().getSharedPreferences("EmpInfo",
						Context.MODE_PRIVATE);
		userID = mSharedPreferences.getString("userId", null);
		if (userID == null) // 为null就跳转到登录窗口
			startActivity(new Intent(mActivity, com.ts.activity.LoginActivity_.class));
		Tools.startStrictMode();
		// 得到资源info的标题
		Resources rs = getResources();
		strTitle = rs.getStringArray(R.array.info_items);

		if (!Tools.connectInternet(getActivity())) {
			if (mSharedPreferences.getString("info", null) != null) {
				response = mSharedPreferences.getString("info", null);
				mInfo = GsonTools.getClass(response, Info.class);
				tableInfo = Tools.getInfo(mInfo);
				lvInfoMsg.setAdapter(new TalkPersonalCollectAdapter(
						getActivity().getApplicationContext(), Tools
								.getStringData(strTitle, tableInfo)));
			}
		} else
			updateInfo();
	}
	@Background
	public void updateInfo() {
		response = new UrlConnectionToServer().postWhat(1, userID);
		if (response == null) {
			return;
		}
		if (!response.equals("null")) {
			// 进行转换，并得到相应的资料
			mInfo = GsonTools.getClass(response, Info.class);
			tableInfo = Tools.getInfo(mInfo);
			// 并且保存状态
			SharedPreferences mSharedPreferences = mActivity
					.getApplicationContext().getSharedPreferences("EmpInfo",
							Context.MODE_PRIVATE);
			Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("info", response);
			mEditor.commit();
			//更新ui
			updateListView();
		}
	}
	@UiThread
	public void updateListView() {
		lvInfoMsg.setAdapter(new TalkPersonalCollectAdapter(getActivity()
				.getApplicationContext(), Tools.getStringData(strTitle,
						tableInfo)));
	}

	@ViewById(R.id.info_lv_msg)
	ListView lvInfoMsg;

	
}
