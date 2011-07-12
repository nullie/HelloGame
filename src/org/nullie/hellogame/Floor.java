package org.nullie.hellogame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

class Floor implements GameObject {
	public Floor() {
		float vertices[] = {
				-1, -1, 0,
				-1, 1, 0,
				1, -1, 0,
				1, 1, 0,
		};
		
		float colors[] = {
				1, 0, 0, 1,
				0, 1, 0, 1,
				0, 0, 1, 1,
				1, 0, 1, 1,
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
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * Float.SIZE / 8);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * Float.SIZE / 8);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
		
		ByteBuffer tcbb = ByteBuffer.allocateDirect(colors.length * Float.SIZE / 8);
		tcbb.order(ByteOrder.nativeOrder());
		mTexCoordBuffer = tcbb.asFloatBuffer();
		mTexCoordBuffer.put(textureCoords);
		mTexCoordBuffer.position(0);
		
		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);
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
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture);
		
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
		
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
		
		gl.glPopMatrix();
		
		mAngle += 3;
	}
	
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mColorBuffer;
	private FloatBuffer mTexCoordBuffer;
	private ByteBuffer mIndexBuffer;
	private float mAngle = 0;
	private int mTexture;
}