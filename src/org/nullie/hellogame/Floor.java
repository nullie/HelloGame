package org.nullie.hellogame;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

class Floor implements World.WorldObject {
	public Floor() {
		float vertices[] = {
				-10, -10, 0,
				-10, 10, 0,
				10, -10, 0,
				10, 10, 0,
		};
		
		float textureCoords[] = {
				0, 0,
				0, 1,
				1, 0,
				1, 1,
		};
		
		byte indices[] = {
				0, 1, 2, 3
		};
		
		mVertexBuffer = Utils.allocateFloatBuffer(vertices);

		mTexCoordBuffer = Utils.allocateFloatBuffer(textureCoords);
		
		mIndexBuffer = Utils.allocateByteBuffer(indices);
	}
	
	public void init(GL10 gl, Context context) {
		mTexture = Utils.loadTexture(gl, context, R.drawable.floor);
	}

	@Override
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW);
		
		gl.glPushMatrix();
		gl.glRotatef(mAngle, 0, 0, 1);
		
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture);
		
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
		
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glPopMatrix();
		
		// mAngle += 3;
	}
	
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mTexCoordBuffer;
	private ByteBuffer mIndexBuffer;
	private float mAngle = 0;
	private int mTexture;
}