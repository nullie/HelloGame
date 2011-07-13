package org.nullie.hellogame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

class World implements GLSurfaceView.Renderer {
	public interface WorldObject {
		public void init(GL10 gl, Context context);
		public void draw(GL10 gl);
	}
	
	public World(Context context) {
		mContext = context;
		
		initPhysics();
				
		mObjects = new WorldObject[5];
		
		mObjects[0] = new Floor(mDynamicsWorld);
		mObjects[1] = new Cube(mDynamicsWorld, new Vector3f(0.f, 1.1f, 5.f));
		mObjects[2] = new Cube(mDynamicsWorld, new Vector3f(1.1f, 0.f, 10.f));
		mObjects[3] = new Cube(mDynamicsWorld, new Vector3f(1.1f, 0.f, 15.f));
		mObjects[4] = new Cube(mDynamicsWorld, new Vector3f(1.1f, 0.f, 20.f));		
	}
	
	private void initPhysics() {
		// Build the broadphase
		DbvtBroadphase broadphase = new DbvtBroadphase();
		
		// Set up the collision configuration and dispatcher
		CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
		CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
		
		// The actual physics solver
		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
		
		mDynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		
		mDynamicsWorld.setGravity(new Vector3f(0.0f, 0.0f, -10.0f));		
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        
		GLU.gluLookAt(gl, -10, -10, 10, 0, 0, 0, 0, 0, 1);
		
		for(WorldObject object : mObjects) {
			object.draw(gl);
		}
		
		mDynamicsWorld.stepSimulation(.033f);
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
		for(WorldObject object : mObjects) {
			object.init(gl, mContext);
		}
	}
	
	private WorldObject[] mObjects;
	private Context mContext;
	private float mFOV = 90;
	private DiscreteDynamicsWorld mDynamicsWorld;
}
