package com.ts.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.protocol.ResponseServer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.common.Info;
import com.json.http.UrlConnectionToServer;
import com.slidingmenu.MainActivity;
import com.teachersystem.R;
import com.ts.app.TsApplication;
import com.ts.utils.Tools;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
	TsApplication mTsApplication;
	@ViewById(R.id.login_btn)
	Button loginButton;
	@ViewById(R.id.login_et_account)
	EditText accountET;
	@ViewById(R.id.login_et_pwd)
	EditText pwdET;

	@AfterViews
	public void init() {
		mTsApplication = (TsApplication) getApplication();// 得到Application
		Tools.startStrictMode();
	}

	@Click(R.id.login_btn)
	public void verify() {
		Log.i("login_btn", "click me");
		if (!Tools.connectInternet(LoginActivity.this)) { // 是否连接网络
			Tools.makeToast(getApplicationContext(), R.string.network_error);
			return;
		}
		String account = accountET.getText().toString();
		String pwd = pwdET.getText().toString();
		if (account == null || pwd == null) { // 判断是否为空
			Tools.makeToast(getApplicationContext(), R.string.login_error_null);
			return;
		}
		// 封装进行传送
		Info mInfo = new Info();
		mInfo.setEmpId(account);
		mInfo.setEmpPwd(pwd);
		// 进行登录
		String repsonseStr=new UrlConnectionToServer().doPost(mInfo);
		if(repsonseStr==null){
			Tools.makeToast(getApplicationContext(), R.string.network_error);
		}
		else if ( repsonseStr.equals("-1"))
			Tools.makeToast(getApplicationContext(), R.string.login_error);
		else{
			//保存状态后，进行跳转,保存用户的编号
			SharedPreferences mSharedPreferences=getApplicationContext().getSharedPreferences("EmpInfo", getApplicationContext().MODE_PRIVATE);
			Editor mEditor=mSharedPreferences.edit();
			mEditor.putString("userId", account);
			mEditor.commit();
			startActivity(new Intent(this,MainActivity.class));
			finish();
		}
	}

}
