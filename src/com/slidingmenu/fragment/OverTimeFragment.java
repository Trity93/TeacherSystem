package com.slidingmenu.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.common.Common;
import com.common.OverTime;
import com.json.http.UrlConnectionToServer;
import com.json.utils.GsonTools;
import com.slidingmenu.adapter.TalkPersonalCollectAdapter;
import com.teachersystem.R;
import com.ts.utils.Tools;

@EFragment(R.layout.fragment_overtime)
public class OverTimeFragment extends Fragment {

	public OverTimeFragment() {
	}

	@ViewById(R.id.over_time_table)
	ListView overtimeMsg;
	@ViewById(R.id.overtime_btn_add)
	Button btnAdd;
	String response = null;
	String userID;
	OverTime mInfo;
	String[] tableOverTime;
	String[] overtimeTitle;
	SharedPreferences mSharedPreferences;

	@AfterViews
	public void userIdget() {
		// 得到状态,为null，就进行跳转LoginActivity
		mSharedPreferences = getActivity().getApplicationContext()
				.getSharedPreferences("EmpInfo", Context.MODE_PRIVATE);
		userID = mSharedPreferences.getString("userId", null);
		if (userID == null) // 为null就跳转到登录窗口
			startActivity(new Intent(getActivity(),
					com.ts.activity.LoginActivity_.class));
		// 得到资源work的标题
		Resources rs = getResources();
		overtimeTitle = rs.getStringArray(R.array.overtime_items);
		
		if (!Tools.connectInternet(getActivity())) {
			if (mSharedPreferences.getString("overtime", null) != null) {// 这个用来保存json的字符串
				response = mSharedPreferences.getString("overtime", null);
				// 进行转换，并得到相应的资料
				mInfo = GsonTools.getClass(response, OverTime.class);
				tableOverTime = Tools.getOverTime(mInfo);
				overtimeMsg.setAdapter(new TalkPersonalCollectAdapter(
						getActivity().getApplicationContext(), Tools
								.getStringData(overtimeTitle, tableOverTime)));
			}
		}
		else
			updateWork();
	}

	@Background
	public void updateWork() {
		response = new UrlConnectionToServer().postWhat(3, userID);
		if(response==null){
			return;
		}
		if (!response.equals("null")) {
			// 进行转换，并得到相应的资料
			mInfo = GsonTools.getClass(response, OverTime.class);
			tableOverTime = Tools.getOverTime(mInfo);
			// 并且保存状态
			mSharedPreferences = getActivity().getApplicationContext()
					.getSharedPreferences("EmpInfo", Context.MODE_PRIVATE);
			Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("overtime", response);
			mEditor.commit();
			// 更新ui
			updateListView();
		}
	}

	@UiThread
	public void updateListView() {
		overtimeMsg.setAdapter(new TalkPersonalCollectAdapter(getActivity()
				.getApplicationContext(), Tools.getStringData(overtimeTitle,
				tableOverTime)));
	}

	@Click(R.id.overtime_btn_add)
	public void addOvertime() {
		// 上网，进行请假操作
		int isSuccess = new UrlConnectionToServer().postAddWhat(
				Common.ADD_TO_OVERTIME, userID);
		if (isSuccess == 1)
			Tools.makeToast(getActivity(), R.string.success);
		else
			Tools.makeToast(getActivity(), R.string.error);
	}
}
