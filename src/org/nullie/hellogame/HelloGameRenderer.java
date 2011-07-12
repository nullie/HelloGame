package org.nullie.hellogame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

class HelloGameRenderer implements GLSurfaceView.Renderer {
	public HelloGameRenderer() {
		mObjects = new GameObject[1];
		
		mObjects[0] = new Floor();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        
		GLU.gluLookAt(gl, -2, -2, 2, 0, 0, 0, 0, 0, 1);
		
		for(GameObject object : mObjects) {
			object.draw(gl);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, mFOV, (float)width / height, 1.0f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}
	
	GameObject[] mObjects;
	float mFOV = 90;
}