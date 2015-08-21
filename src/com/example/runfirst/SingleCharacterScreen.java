package com.example.runfirst;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class SingleCharacterScreen implements Screen{
	private AnimationLayer animationLayer;
	private float cutOffHeight;
	private float scale;
	private boolean isPositionSet = false;
	
	public SingleCharacterScreen(AnimationLayer animationLayer, float paramsHeight,
			float paramsScale){
		Log.i("mylog", "single create");
		this.animationLayer = animationLayer;
		this.cutOffHeight = paramsHeight;
		this.scale = paramsScale;
	}
	
	@Override
	public void render(float deltaTime) {
		Log.i("mylog", "single render " + deltaTime);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		if(!this.isPositionSet){
			show();
		}
		if(animationLayer.batch != null){
			animationLayer.batch.begin();
			if(animationLayer.skeletonRenderer != null && animationLayer.ownCharacter != null
					&& animationLayer.ownCharacter.getSkeleton() != null){
				animationLayer.skeletonRenderer.draw(animationLayer.batch, animationLayer.ownCharacter.getSkeleton());
			}
			this.animationLayer.batch.end();
		}
		if(this.animationLayer.ownCharacter != null){
			animationLayer.ownCharacter.updateAnimationState(deltaTime);
			animationLayer.ownCharacter.applySkeletonToAnimationState();
			animationLayer.ownCharacter.updateWorldTransform();
		}
	}
	
	@Override
	public void show() {
		if(animationLayer.ownCharacter != null && animationLayer.ownCharacter.getSkeleton() != null){
			isPositionSet = true;
//			animationLayer.ownCharacter.setPosition(Gdx.graphics.getWidth() / 2 - 220.0f * scale,  this.cutOffHeight);
			animationLayer.ownCharacter.setPosition(Gdx.graphics.getWidth() / 2,  this.cutOffHeight);
		}
	}
	
	@Override
	public void dispose() {
		Log.i("mylog", "single dispose");
	}

	@Override
	public void hide() {
		Log.i("mylog", "single hide");
	}

	@Override
	public void pause() {
		Log.i("mylog", "single pause");
	}

	@Override
	public void resize(int arg0, int arg1) {
		Log.i("mylog", "single resize");
	}

	@Override
	public void resume() {
		Log.i("mylog", "single resume");
	}
}
