package com.example.runfirst;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class AnimationFragment extends AndroidFragmentApplication {
	private AnimationLayer mAnimLayer;
	private View mAnimationView;
	private AndroidApplicationConfiguration getApplicationConfiguration(){
		AndroidApplicationConfiguration localConfiguration = new AndroidApplicationConfiguration();
		localConfiguration.a = 8;
		localConfiguration.b = 8;
		localConfiguration.g = 8;
		localConfiguration.r = 8;
		return localConfiguration;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mAnimLayer = new AnimationLayer("fishslap", 50f, 1.0f);
		mAnimLayer = new AnimationLayer(250f, 1.0f);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		LinearLayout layoutView = (LinearLayout)inflater.inflate(R.layout.activity_main, null);
		mAnimationView = initializeForView(mAnimLayer, getApplicationConfiguration());
		if(graphics != null && graphics.getView() != null && (graphics.getView() instanceof GLSurfaceView)){
			GLSurfaceView surfaceView = (GLSurfaceView)graphics.getView();
//			surfaceView.getHolder().setFormat(LOG_INFO);
			surfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
			//PixelFormat.TRANSLUCENT 半透明
			surfaceView.setZOrderOnTop(true);
		}
		mAnimationView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
		layoutView.addView(mAnimationView);
		return layoutView;
	}
	
	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}
	
	public void setSingleCharacterScreen(){
		mAnimLayer.setSingleCharacterScreen();
	}
	
	public void setDoubleCharacterScreen(){
		mAnimLayer.setDoubleCharacterScreen();
	}
	
	public void addOwnPart(String ownPart){
		if(!TextUtils.isEmpty(ownPart)){
			mAnimLayer.addOwnPart(ownPart);
		}
	}
	
	public void addPartnerPart(String partnerPart){
		if(!TextUtils.isEmpty(partnerPart)){
			mAnimLayer.addPartnerPart(partnerPart);
		}
	}
	
	public void setOwnSkin(String skinName){
		if(!TextUtils.isEmpty(skinName)){
			mAnimLayer.ownCharacter.setSkin(skinName);
		}
	}
	
	public void setPartnerSkin(String skinName){
		if(!TextUtils.isEmpty(skinName)){
			mAnimLayer.partnerCharacter.setSkin(skinName);	
		}
	}
	
	public void setOwnSlotColor(){
		mAnimLayer.ownCharacter.setSlotColor();
	}
	
	public void addAnimation(String name){
		if(!TextUtils.isEmpty(name)){
			mAnimLayer.addAnimation(name);
		}
	}
	
	public void finishAnimation(){
//		mAnimLayer.mDispatcher.interrupt();
		mAnimLayer.finishAnimation();
	}
}
