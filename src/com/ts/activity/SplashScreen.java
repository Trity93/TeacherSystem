package com.ts.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.slidingmenu.MainActivity;
import com.teachersystem.R;

public class SplashScreen extends Activity {
	private static final long SPEND_TIME = 2000;
	private View view;
	private AlphaAnimation start_anima;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_splash_screen, null);
		setContentView(view);
		initData();
	}

	private void initData() {
		start_anima = new AlphaAnimation(0.1f, 1.0f); // 淡入浅出的效果，过度
		start_anima.setDuration(SPEND_TIME); // 用的时间
		view.startAnimation(start_anima);
		start_anima.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				redirectTo();
			}
		});
	}

	private void redirectTo() {
		// 得到状态,不为null，就进行跳转MainActivity
		SharedPreferences mSharedPreferences = getApplicationContext()
				.getApplicationContext().getSharedPreferences("EmpInfo",
						Context.MODE_PRIVATE);
		if(mSharedPreferences.getString("userId", null)!=null)
			startActivity(new Intent(getApplicationContext(),MainActivity.class));
		else
			startActivity(new Intent(getApplicationContext(), LoginActivity_.class));
		finish();

	}

}
