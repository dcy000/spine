/******************************************************************************
 * Spine Runtimes Software License
 * Version 2.3
 * 
 * Copyright (c) 2013-2015, Esoteric Software
 * All rights reserved.
 * 
 * You are granted a perpetual, non-exclusive, non-sublicensable and
 * non-transferable license to use, install, execute and perform the Spine
 * Runtimes Software (the "Software") and derivative works solely for personal
 * or internal use. Without the written permission of Esoteric Software (see
 * Section 2 of the Spine Software License Agreement), you may not (a) modify,
 * translate, adapt or otherwise create derivative works, improvements of the
 * Software or develop new applications using the Software or (b) remove,
 * delete, alter or obscure any trademarks or any copyright, trademark, patent
 * or other intellectual property or proprietary rights notices on or in the
 * Software, including any copy thereof. Redistributions in binary or source
 * form must include this license and terms.
 * 
 * THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL ESOTERIC SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.example.runfirst;

import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.SlotData;
import com.esotericsoftware.spine.attachments.Attachment;

public class AddClothSimpleTest extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	SkeletonRenderer renderer;
	SkeletonRendererDebug debugRenderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	AnimationState state;

	public void create () {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
		debugRenderer = new SkeletonRendererDebug();
		debugRenderer.setBoundingBoxes(false);
		debugRenderer.setRegionAttachments(false);

		atlas = new TextureAtlas(Gdx.files.internal("double/fishslap_a.atlas"));
//		atlas = new TextureAtlas(Gdx.files.internal("character/stick.atlas"));
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		//json.setScale(1.6f); // Load the skeleton at 60% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("double/fishslap_a.json"));
//		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("character/stick.json"));
		
		
//		addPart(skeletonData, "character/stick");
//		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		
//		AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
//		state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
//		state.setTimeScale(1f); // Slow all animations down to 50% speed.
////		state.setAnimation(0, "badumtsss", true);
//		state.setAnimation(0, "fishslap_a", true);
	
		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setPosition(500, 200);
		
		addPart(skeletonData, "character/pts_m_11");
		addPart(skeletonData, "character/hair_f_41");
		addPart(skeletonData, "character/tps_f_26");
		removeAttachment(skeletonData, "character/pts_m_11");
		addPart(skeletonData, "character/skrt_22");
		skeleton.setSlotsToSetupPose();
	}

	private void addPart(SkeletonData skeletonData, String name) {
		if(TextUtils.isEmpty(name)){
			return;
		}
		TextureAtlas partAtlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));
		SkeletonData partData = new SkeletonJson(partAtlas).readSkeletonData(Gdx.files.internal(name + ".json"));
		for(int i = 0 ; i < partData.getSlots().size; i ++){
			SlotData item = partData.getSlots().get(i);
			skeletonData.findSlot(item.getName()).setAttachmentName(item.getAttachmentName());
			if(!TextUtils.isEmpty(item.getAttachmentName())
					&& partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()) != null){
				skeletonData.getDefaultSkin().addAttachment(skeletonData.findSlotIndex(item.getName()), item.getAttachmentName(), 
						partData.getDefaultSkin().getAttachment(i, item.getAttachmentName()));
			}
		}
	}
	
	private void removeAttachment(SkeletonData skeletonData, String name){
		if(TextUtils.isEmpty(name)){
			return;
		}
		TextureAtlas partAtlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));
		SkeletonData partData = new SkeletonJson(partAtlas).readSkeletonData(Gdx.files.internal(name + ".json"));
		for(int i = 0 ; i < partData.getSlots().size; i ++){
			SlotData item = partData.getSlots().get(i);
//			if(partData.getDefaultSkin().getAttachment(i, name)){
//				
//			}
			skeletonData.findSlot(item.getName()).setAttachmentName(null);
		}
	}

	
	private boolean test = false;
	
	public void render () {
		if(!test){
			test = true;
			AnimationStateData stateData = new AnimationStateData(skeleton.getData()); // Defines mixing (crossfading) between animations.
			state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
			state.setTimeScale(1f); // Slow all animations down to 50% speed.
//			state.setAnimation(0, "badumtsss", true);
			state.setAnimation(0, "fishslap_a", true);
		}
		state.update(Gdx.graphics.getDeltaTime()); // Update the animation time.

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);

		state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
		skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

		// Configure the camera, SpriteBatch, and SkeletonRendererDebug.
		camera.update();
		batch.getProjectionMatrix().set(camera.combined);
		debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);

		batch.begin();
		renderer.draw(batch, skeleton); // Draw the skeleton images.
		batch.end();

		debugRenderer.draw(skeleton); // Draw debug lines.
	}

	public void resize (int width, int height) {
		camera.setToOrtho(false); // Update camera with new size.
	}

	public void dispose () {
		atlas.dispose();
	}
}
