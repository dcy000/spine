package com.example.runfirst;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class DoubleCharacterScreen implements Screen{
	private AnimationLayer animationLayer;
	private float cutOffHeight;
	private float scale;
	private boolean ownPositionSet;
	private boolean partnerPositionSet;
	
	public DoubleCharacterScreen(AnimationLayer animationLayer, float paramsHeight,
			float paramsScale) {
		this.animationLayer = animationLayer;
		this.cutOffHeight = paramsHeight;
		this.scale = paramsScale;
	}
	
	@Override
	public void render(float deltaTime) {
//		Log.i("mylog", "double render");
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		setOwnPosition();
		setPartnerPosition();
		if(animationLayer.batch != null && animationLayer.skeletonRenderer != null){
			animationLayer.batch.begin();
			if(animationLayer.partnerCharacter != null && animationLayer.partnerCharacter.getSkeleton() != null){
				animationLayer.skeletonRenderer.draw(animationLayer.batch, animationLayer.partnerCharacter.getSkeleton());
			}
			if(animationLayer.ownCharacter != null && animationLayer.ownCharacter.getSkeleton() != null){
				Log.i("mylog", animationLayer.ownCharacter.getSkeleton().getData().findSlot("front_a_c").getColor().toString());
				animationLayer.skeletonRenderer.draw(animationLayer.batch, animationLayer.ownCharacter.getSkeleton());
			}
			animationLayer.batch.end();
		}
		if(this.animationLayer.ownCharacter != null){
			animationLayer.ownCharacter.updateAnimationState(deltaTime);
			animationLayer.ownCharacter.applySkeletonToAnimationState();
			animationLayer.ownCharacter.updateWorldTransform();
		}
		if(this.animationLayer.partnerCharacter != null){
			animationLayer.partnerCharacter.updateAnimationState(deltaTime);
			animationLayer.partnerCharacter.applySkeletonToAnimationState();
			animationLayer.partnerCharacter.updateWorldTransform();
		}
	}
	
	@Override
	public void show() {
		ownPositionSet = false;
		partnerPositionSet = false;
	}
	
	private void setOwnPosition(){
		if(!this.ownPositionSet && animationLayer.ownCharacter != null && animationLayer.ownCharacter.getSkeleton() != null){
			ownPositionSet = true;
			animationLayer.ownCharacter.setPosition(Gdx.graphics.getWidth() / 2 - 100,  this.cutOffHeight);
		}
	}
	
	private void setPartnerPosition(){
		if(!this.partnerPositionSet && animationLayer.partnerCharacter != null && animationLayer.partnerCharacter.getSkeleton() != null){
			partnerPositionSet = true;
			animationLayer.partnerCharacter.setPosition(Gdx.graphics.getWidth() / 2,  this.cutOffHeight);
		}
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}
}
