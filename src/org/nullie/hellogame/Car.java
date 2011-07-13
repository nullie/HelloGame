package org.nullie.hellogame;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Car implements World.WorldObject {

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GL10 gl, Context context) {
		// TODO Auto-generated method stub
	}

	Float mPosition[] = {0.0f, 0.0f, 0.0f};
	Float mRotation[] = {0.0f, 0.0f, 1.0f, 0.0f};
}
