package com.example.runfirst;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class MainActivity extends FragmentActivity implements 
		OnClickListener, AndroidFragmentApplication.Callbacks{
//public class MainActivity extends AndroidApplication implements OnClickListener{	
	private AnimationFragment mFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		  initialize( new Test1());
//		initialize(new AddClothSimpleTest());
//		initialize(new SimpleTest1());
		
//		AnimationLayer layer = new AnimationLayer("fishslap", 500f, 1f);
//		initialize(layer);
//		layer.setDoubleCharacterScreen();
////		
//		layer.addOwnPart("character/hair_f_41");
//		layer.addOwnPart("character/tps_f_26");
//		layer.addOwnPart("character/pts_m_11");
//		layer.addPartnerPart("character/hair_f_41");
//		layer.addPartnerPart("character/skrt_22");
//		layer.addPartnerPart("character/tps_f_26");
		
//		layer.setSingleCharacterScreen();
		
		setContentView(R.layout.activity_main);
		findViewById(R.id.tv_test).setOnClickListener(this);
		mFragment = new AnimationFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.container, 
				mFragment).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_test:
			Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
			mFragment.addOwnPart("default/cloth1");
			mFragment.addOwnPart("default/hair_back1");
			mFragment.addOwnPart("default/hair_front1");
//			mFragment.addOwnPart("default/scok1");
			mFragment.addOwnPart("default/shoes1");
			
			mFragment.addAnimation("default/default_new");
			mFragment.addAnimation("default/default_new");
			mFragment.addAnimation("default/default_new");
//			mFragment.finishAnimation();
//			mFragment.addOwnPart("character/tps_f_26");
//			mFragment.addOwnPart("character/pts_m_11");
			
			mFragment.setDoubleCharacterScreen();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mFragment.setOwnSlotColor();
				}
			}, 1000);
			mFragment.setPartnerSkin("eye_c");
//			mFragment.setSingleCharacterScreen();
			
//			mFragment.setSkin();
			
//			mFragment.addPartnerPart("character/hair_f_41");
//			mFragment.addPartnerPart("character/skrt_22");
//			mFragment.addPartnerPart("character/tps_f_26");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void exit() {
	}
}
