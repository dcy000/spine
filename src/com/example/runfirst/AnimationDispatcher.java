package com.example.runfirst;

import java.util.concurrent.BlockingQueue;

import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;

import android.util.Log;


public class AnimationDispatcher extends Thread {
	private BlockingQueue<String> mAnimationQueue;
	private Character ownCharacter;
	private Character partnerCharacter;
	private boolean isAnimationEnd = true;
	
	public AnimationDispatcher(BlockingQueue<String> animationQueue, 
			Character ownCharacter, Character partnerCharacter) {
		this.mAnimationQueue = animationQueue;
		this.ownCharacter = ownCharacter;
		this.partnerCharacter = partnerCharacter;
	}
	
	@Override
	public void run() {
		
//		while (true) {
//			Log.i("mylog44", "33333333333333333");
//			try {
//				Log.i("mylog44", "111111111111111");
//				if(isAnimationEnd){
//					isAnimationEnd = false;
//					String item = mAnimationQueue.take();
//					ownCharacter.animateWithAttachment(item);
//					ownCharacter.startAnimation();
//					ownCharacter.mAnimationState.addListener(mAnimationListener);
//				}
//				Log.i("mylog44", "222222222222222");
//			} catch (InterruptedException e) {
//				ownCharacter.stopAnimation();
//				return;
//			}
//		}
	}
	
	private void startHeadQueueAnimation(){
		try {
			String item = mAnimationQueue.take();
			ownCharacter.animateWithAttachment(item);
			ownCharacter.startAnimation();
			ownCharacter.mAnimationState.addListener(mAnimationListener);
		} catch (InterruptedException e) {
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
			Log.i("mylog44", "endddddddddd");
			isAnimationEnd = true;
		}
		
		@Override
		public void complete(int trackIndex, int loopCount) {
		}
	};
}
