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
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.common.OverTime;
import com.common.Work;
import com.json.http.UrlConnectionToServer;
import com.json.utils.GsonTools;
import com.slidingmenu.adapter.TalkPersonalCollectAdapter;
import com.teachersystem.R;
import com.ts.utils.Tools;

@EFragment(R.layout.fragment_work)
public class WorkFragment extends Fragment {

	public WorkFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setHasOptionsMenu(true);

		super.onCreate(savedInstanceState);
	}

	@ViewById(R.id.work_lv_table)
	ListView mListView;
	String response = null;
	String userID;
	Work mInfo;
	String[] tableWork;
	String[] titleWork;

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
		titleWork = rs.getStringArray(R.array.work_items);

		if (!Tools.connectInternet(getActivity())) {
			if (mSharedPreferences.getString("work", null) != null) {// 这个用来保存json的字符串
				response = mSharedPreferences.getString("work", null);
				// 进行转换，并得到相应的资料
				mInfo = GsonTools.getClass(response, Work.class);
				tableWork = Tools.getWork(mInfo);
				mListView.setAdapter(new TalkPersonalCollectAdapter(
						getActivity().getApplicationContext(), Tools
								.getStringData(titleWork, tableWork)));
			}
		}
		else
			updateWork();
	}

	@Background
	public void updateWork() {
		response = new UrlConnectionToServer().postWhat(2, userID);
		if(response==null){
			return;
		}
		
		if (!response.equals("null")) {
			// 进行转换，并得到相应的资料
			mInfo = GsonTools.getClass(response, Work.class);
			tableWork = Tools.getWork(mInfo);
			// 并且保存状态
			SharedPreferences mSharedPreferences = getActivity()
					.getApplicationContext().getSharedPreferences("EmpInfo",
							Context.MODE_PRIVATE);
			Editor mEditor = mSharedPreferences.edit();
			mEditor.putString("work", response);
			mEditor.commit();
			// 更新ui
			updateListView();
		}
	}

	@UiThread
	public void updateListView() {
		mListView.setAdapter(new TalkPersonalCollectAdapter(getActivity()
				.getApplicationContext(), Tools.getStringData(titleWork,
						tableWork)));
	}

}
