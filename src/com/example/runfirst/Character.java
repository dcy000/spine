package com.example.runfirst;

import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.SlotData;

public class Character {
	public AnimationState mAnimationState;
	private String animateName;
	private Skeleton mSkeleton;
	private SkeletonJson stickJson;
	
	public Character(SkeletonJson skeletonJson) {
		this.stickJson = skeletonJson;
	}
	
	public void setupSkeleton(Skeleton paramSkeleton, boolean parmsBoolean){
		this.mSkeleton = paramSkeleton;
		this.mSkeleton.setX(Gdx.graphics.getWidth() / 2);
		this.mSkeleton.setY(0.0f);
		this.mSkeleton.setFlipX(parmsBoolean);
		setSkin("eye_b");
	}
	
	public void addPart(String name) {
		Log.i("mylog", "add part : " + name);
		if(TextUtils.isEmpty(name) || mSkeleton == null){
			return;
		}
		SkeletonData localData = mSkeleton.getData();
		TextureAtlas partAtlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));
		SkeletonData partData = new SkeletonJson(partAtlas).readSkeletonData(Gdx.files.internal(name + ".json"));
		for(int i = 0 ; i < partData.getSlots().size; i ++){
			SlotData item = partData.getSlots().get(i);
			if(!TextUtils.isEmpty(item.getAttachmentName())
					&& partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()) != null){
				//change attachment
				localData.findSlot(item.getName()).setAttachmentName(item.getAttachmentName());
				//change color
				localData.findSlot(item.getName()).getColor().set(item.getColor());
				localData.getDefaultSkin().addAttachment(localData.findSlotIndex(item.getName()), item.getAttachmentName(), 
						partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()));
			}
		}
		mSkeleton.setSlotsToSetupPose();
	}
	
	private void removeAttachment(String name){
		if(TextUtils.isEmpty(name)){
			return;
		}
		TextureAtlas partAtlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));
		SkeletonData partData = new SkeletonJson(partAtlas).readSkeletonData(Gdx.files.internal(name + ".json"));
		SkeletonData localData = mSkeleton.getData();
		for(int i = 0 ; i < partData.getSlots().size; i ++){
			SlotData item = partData.getSlots().get(i);
//			if(partData.getDefaultSkin().getAttachment(i, name)){
//				
//			}
			localData.findSlot(item.getName()).setAttachmentName(null);
		}
	}
	
	public boolean animateWithAttachment(String animationName){
		this.animateName = animationName;
//		addPart(animationName);
		if(TextUtils.isEmpty(animationName) || mSkeleton == null){
			return false;
		}
		SkeletonData localData = mSkeleton.getData();
		TextureAtlas partAtlas = new TextureAtlas(Gdx.files.internal(animationName + ".atlas"));
		SkeletonData partData = new SkeletonJson(partAtlas).readSkeletonData(Gdx.files.internal(animationName + ".json"));
		for(int i = 0 ; i < partData.getSlots().size; i ++){
			SlotData item = partData.getSlots().get(i);
			if(!TextUtils.isEmpty(item.getAttachmentName())
					&& partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()) != null){
				localData.findSlot(item.getName()).setAttachmentName(item.getAttachmentName());
				localData.getDefaultSkin().addAttachment(localData.findSlotIndex(item.getName()), item.getAttachmentName(), 
						partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()));
			}
		}
		
		mAnimationState = new AnimationState(new AnimationStateData(partData));
		return true;
	}
	
	public boolean animateWithoutAttachment(String animationName){
		this.animateName = animationName;
		FileHandle dataHandle = Gdx.files.internal(animationName + ".json");
		if(!dataHandle.exists()){
			return false;
		}
		try {
			mAnimationState = new AnimationState(new AnimationStateData(stickJson.readSkeletonData(dataHandle)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setSkin(String skinName){
		if(mSkeleton != null){
			mSkeleton.setSkin(skinName);
			mSkeleton.setSlotsToSetupPose();
			mSkeleton.updateWorldTransform();
		}
	}
	
	public void setSlotColor(){
//		mSkeleton.findSlot("front_a_c").getColor().set(Color.valueOf("ff00ff"));
		mSkeleton.getData().findSlot("front_a_c").getColor().set(Color.valueOf("ff00ff"));
//		mSkeleton.getData().findSlot("eyeball_l").getColor().set(Color.valueOf("ffff00"));
		mSkeleton.getData().findSlot("eyeball_l_c").getColor().set(Color.valueOf("ffff00"));
		
		mSkeleton.setSlotsToSetupPose();
		
//		mSkeleton.updateWorldTransform();
		
//		for(SlotData itemSlot : mSkeleton.getData().getSlots()){
//			itemSlot.getColor().set(Color.valueOf("00ffff"));
//		}
		
	}
	
	public void startAnimation(){
		if(mAnimationState != null && !TextUtils.isEmpty(animateName)){
//			mAnimationState.setAnimation(0, animateName, true);
			mAnimationState.setAnimation(0, "1", false);
		}
	}
	
	public void stopAnimation(){
		if(mAnimationState != null){
			mAnimationState.clearTracks();
		}
	}
	
	public void setPosition(float xPosition, float yPosition){
		if(mSkeleton != null){
			mSkeleton.setX(xPosition);
			mSkeleton.setY(yPosition);
		}
	}
	
	public void updateAnimationState(float paramsFloat){
		if(mAnimationState != null){
			this.mAnimationState.update(paramsFloat);
		}
	}
	
	public void applySkeletonToAnimationState(){
		if(mAnimationState != null && mSkeleton != null){
			mAnimationState.apply(mSkeleton);
		}
	}
	
	public void updateWorldTransform(){
		if(mSkeleton != null){
			mSkeleton.updateWorldTransform();
		}
	}
	
	public Skeleton getSkeleton(){
		return mSkeleton;
	}
}
