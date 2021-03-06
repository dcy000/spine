package com.example.runfirst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class AnimationLayer extends Game {
	public SpriteBatch batch;
	public SkeletonRenderer skeletonRenderer;
	public Character ownCharacter;
	private final SingleCharacterScreen singleCharacterScreen;
	public Character partnerCharacter;
	private final DoubleCharacterScreen doubleCharacterScreen;
	private boolean isAnimationLoading;
	private boolean needLoadQwnPart;
	private boolean needLoadPartnerPart;
	private List<String> loadedOwnParts = Collections.synchronizedList(new ArrayList<String>());
	private List<String> loadedPartnerParts = Collections.synchronizedList(new ArrayList<String>());
//	private PriorityBlockingQueue<String> mAnimationQueue = new PriorityBlockingQueue<String>();
//	public AnimationDispatcher mDispatcher;
	private List<String> mAnimationList = new ArrayList<String>();
	
	public AnimationLayer(float paramsHeight, float paramsScale) {
		Log.i("mylog", "layer new");
		singleCharacterScreen = new SingleCharacterScreen(this, paramsHeight, paramsScale);
		doubleCharacterScreen = new DoubleCharacterScreen(this, paramsHeight, paramsScale);
	}
	
	@Override
	public void create() {
		Log.i("mylog", "layer create");
		batch = new SpriteBatch();
		skeletonRenderer = new SkeletonRenderer();
		skeletonRenderer.setPremultipliedAlpha(true);
		
//		SkeletonJson stickJson = new SkeletonJson(new TextureAtlas(Gdx.files.internal("character/stick.atlas")));
//		Skeleton skeleton = new Skeleton(stickJson.readSkeletonData(Gdx.files.internal("character/stick.json")));
		SkeletonJson stickJson = new SkeletonJson(new TextureAtlas(Gdx.files.internal("default/default.atlas")));
		Skeleton skeleton = new Skeleton(stickJson.readSkeletonData(Gdx.files.internal("default/default.json")));
		
		ownCharacter = new Character(stickJson);
		ownCharacter.setupSkeleton(new Skeleton(skeleton.getData()), true);
//		partnerCharacter = new Character(stickJson);
//		partnerCharacter.setupSkeleton(new Skeleton(skeleton.getData()), false);
			
//		SkeletonJson partnerJson = new SkeletonJson(new TextureAtlas(Gdx.files.internal("character/stick.atlas")));
//		partnerCharacter = new Character(partnerJson);
//		partnerCharacter.setupSkeleton(new Skeleton(partnerJson.readSkeletonData(Gdx.files.internal("character/stick.json"))),false);
		//test
		SkeletonJson partnerJson = new SkeletonJson(new TextureAtlas(Gdx.files.internal("default/default.atlas")));
		partnerCharacter = new Character(partnerJson);
		partnerCharacter.setupSkeleton(new Skeleton(partnerJson.readSkeletonData(Gdx.files.internal("default/default.json"))),false);
		
//		mDispatcher = new AnimationDispatcher(mAnimationQueue, ownCharacter, partnerCharacter);
//		mDispatcher.start();
	}

	public void setSingleCharacterScreen(){
		setScreen(singleCharacterScreen);
	}
	
	public void setDoubleCharacterScreen(){
		setScreen(doubleCharacterScreen);
	}
	
	public void addOwnPart(String partName){
		if(!TextUtils.isEmpty(partName)){
			loadedOwnParts.add(partName);
			needLoadQwnPart = true;
		}
	}
	
	public void addPartnerPart(String partName){
		if(!TextUtils.isEmpty(partName)){
			loadedPartnerParts.add(partName);
			needLoadPartnerPart = true;
		}
	}
	
	@Override
	public void render() {
		super.render();
		Log.i("mylog", "layer render");
		if(needLoadQwnPart && loadedOwnParts.size() > 0){
			needLoadQwnPart = false;
			for (String ownPart : loadedOwnParts) {
				ownCharacter.addPart(ownPart);
			}
			Gdx.graphics.requestRendering();
		}
		if(needLoadPartnerPart && loadedPartnerParts.size() > 0){
			needLoadPartnerPart = false;
			for (String partnerPart : loadedPartnerParts) {
				partnerCharacter.addPart(partnerPart);
			}
		}
		animateItem();
//		startAnimation();
		
//		Gdx.graphics.requestRendering();
	}
	
	public void addAnimation(String fileName){
//		mAnimationQueue.add(fileName);
		mAnimationList.add(fileName);
//		needLoadAnimation = true;
	}
	
	public void finishAnimation(){
		mAnimationList.clear();
	}
	
	private void animateItem(){
		if(!isAnimationLoading){
			if(mAnimationList.size() > 0){
				isAnimationLoading = true;
				ownCharacter.animateWithAttachment(mAnimationList.get(0));
				ownCharacter.startAnimation();
				ownCharacter.mAnimationState.addListener(mAnimationListener);
				partnerCharacter.animateWithoutAttachment(mAnimationList.get(0));
				partnerCharacter.startAnimation();
			}
		}
	}
	
	private AnimationStateListener mAnimationListener = new AnimationStateListener() {
		@Override
		public void start(int trackIndex) {
		}
		
		@Override
		public void event(int trackIndex, Event event) {
		}
		
		@Override
		public void end(int trackIndex) {
			isAnimationLoading = false;
			if(mAnimationList.size() > 0){
				mAnimationList.remove(0);
			}
		}
		
		@Override
		public void complete(int trackIndex, int loopCount) {
			
		}
	};
	
//	private void startAnimation(){
//		if(!isAnimationRunning){
//			isAnimationRunning = true;
////			ownCharacter.animateWithAttachment(fileName + "_a");
//			ownCharacter.animateWithAttachment(fileName);
//			ownCharacter.startAnimation();
////			if(getScreen().equals(doubleCharacterScreen)){
//				partnerCharacter.animateWithoutAttachment(fileName);
//				partnerCharacter.startAnimation();
////			}
//		}
//	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
