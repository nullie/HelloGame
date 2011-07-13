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
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

class World implements GLSurfaceView.Renderer {
	public interface WorldObject {
		public void init(GL10 gl, Context context);
		public void draw(GL10 gl);
	}
	
	public World(Context context) {
		mContext = context;
		
		mObjects = new WorldObject[2];
		
		mObjects[0] = new Floor();
		mObjects[1] = new Cube();
		
		// Build the broadphase
		DbvtBroadphase broadphase = new DbvtBroadphase();
		
		// Set up the collision configuration and dispatcher
		CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
		CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
		
		// The actual physics solver
		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
		
		mDynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		
		mDynamicsWorld.setGravity(new Vector3f(0.0f, 0.0f, -10.0f));
		
		CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0.0f, 0.0f, 1.0f), 0);
		
		CollisionShape ballShape = new SphereShape(1);
		
		DefaultMotionState groundMotionState = new DefaultMotionState();
		
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(0,	groundMotionState, groundShape);
	
		RigidBody ground = new RigidBody(rbInfo);
		
		mDynamicsWorld.addRigidBody(ground);
		
		Transform startTransform = new Transform();
		
		startTransform.setIdentity();
		
		startTransform.origin.set(0.f, 0.f, 10.f);
		
		DefaultMotionState ballMotionState = new DefaultMotionState(startTransform);
		
		rbInfo = new RigidBodyConstructionInfo(10.f, ballMotionState, ballShape);
		
		RigidBody ball = new RigidBody(rbInfo);
		
		mDynamicsWorld.addRigidBody(ball);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        
		GLU.gluLookAt(gl, -2, -2, 2, 0, 0, 0, 0, 0, 1);
		
		for(WorldObject object : mObjects) {
			object.draw(gl);
		}
		
		mDynamicsWorld.stepSimulation(.01f);
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
