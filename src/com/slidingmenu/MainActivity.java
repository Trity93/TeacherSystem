package com.slidingmenu;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.slidingmenu.fragment.HomeFragment_;
import com.slidingmenu.fragment.InfoFragment_;
import com.slidingmenu.fragment.LeaveFragment_;
import com.slidingmenu.fragment.MenuFragment;
import com.slidingmenu.fragment.MenuFragment.SLMenuListOnItemClickListener;
import com.slidingmenu.fragment.OverTimeFragment_;
import com.slidingmenu.fragment.WageFragment_;
import com.slidingmenu.fragment.WorkFragment_;
import com.teachersystem.R;
import com.ts.activity.LoginActivity_;

public class MainActivity extends SlidingActivity implements SLMenuListOnItemClickListener{
	
	private SlidingMenu mSlidingMenu;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Home");
//		setTitle(R.string.sliding_title);
        setContentView(R.layout.frame_content);

        //1、 set the Behind View
        setBehindContentView(R.layout.frame_menu);
        
        // customize the SlidingMenu
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);//设置阴影图片
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); //设置阴影图片的宽度
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); //SlidingMenu划出时主页面显示的剩余宽度
        mSlidingMenu.setFadeDegree(0.35f);
        //设置SlidingMenu 的手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在整个content页面中，滑动，可以打开SlidingMenu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开SlidingMenu,你需要在屏幕边缘滑动才可以打开SlidingMenu
        //TOUCHMODE_NONE 不能通过手势打开SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //设置 SlidingMenu 内容,加载左边的slidingmenu
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new HomeFragment_());
        fragmentTransaction.commit();
        
        //使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        SharedPreferences mSharedPreferences = getApplicationContext()
    			.getApplicationContext().getSharedPreferences("EmpInfo",
    					Context.MODE_PRIVATE);
        if(mSharedPreferences.getString("useId", null)==null)
        	menu.findItem(R.id.action_logout).setTitle(R.string.login_text);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            
            toggle(); //动态判断自动关闭或开启SlidingMenu
            return true;
        case R.id.action_logout:	//进行注销
        	SharedPreferences mSharedPreferences = getApplicationContext()
			.getApplicationContext().getSharedPreferences("EmpInfo",
					Context.MODE_PRIVATE);
        	Editor mEditor=mSharedPreferences.edit();
        	mEditor.putString("useId", null);
        	mEditor.commit();
        	startActivity(new Intent(getApplicationContext(), LoginActivity_.class));
        	finish();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@SuppressLint("NewApi")
	@Override
	public void selectItem(int position, String title) {
		// update the main content by replacing fragments  
	    Fragment fragment = null;  
	    switch (position) {  
	    case 0:  
	        fragment = new HomeFragment_(); //扫描二维码 
	        break;  
	    case 1:  
	        fragment = new InfoFragment_();  //个人资料
	        break;  
	    case 2:  
	        fragment = new WorkFragment_();  //出勤记录
	        break;  
	    case 3:  
	        fragment = new OverTimeFragment_();  //加班记录
	        break;  
	    case 4:  
	        fragment = new LeaveFragment_();  //请假记录
	        break;  
	    case 5:  
	        fragment = new WageFragment_();  //工资记录
	        break;  
	    default:  
	        break;  
	    }  
	  
	    if (fragment != null) {  
	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction()  
	                .replace(R.id.content, fragment).commit();  
	        // update selected item and title, then close the drawer  
	        setTitle(title);
	        mSlidingMenu.showContent();
	    } else {  
	        // error in creating fragment  
	        Log.e("MainActivity", "Error in creating fragment");  
	    }  
	}
}
