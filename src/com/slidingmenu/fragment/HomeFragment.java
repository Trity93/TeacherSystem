package com.slidingmenu.fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import at.markushi.ui.CircleButton;

import com.qr.code.MipcaActivityCapture;
import com.teachersystem.R;

@SuppressLint("NewApi")
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	public HomeFragment(){}
	@ViewById(R.id.result)
	TextView resultTV;
	@ViewById(R.id.qrcode_bitmap)
	ImageView ivQrCode;
	@ViewById(R.id.main_qr_code)
	CircleButton mCircleButton;
	@Click(R.id.main_qr_code)
	public void sendToQrCode(){
		Intent mIntent=new Intent(getActivity(),MipcaActivityCapture.class);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(mIntent, SCANNIN_GREQUEST_CODE);	//为了onActivityResult的方法来显示扫描的结果
	}	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == getActivity().RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				resultTV.setText(bundle.getString("result"));
				//显示
				ivQrCode.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
	}
}
