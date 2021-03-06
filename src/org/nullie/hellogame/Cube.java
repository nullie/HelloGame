package org.nullie.hellogame;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import android.content.Context;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class Cube implements World.WorldObject {
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mColorBuffer;
	private FloatBuffer mNormalBuffer;
	private ByteBuffer mIndexBuffer;
	private RigidBody mRigidBody;

	public Cube(DynamicsWorld world, Vector3f position) {
		createGeometry();
		
		Transform transform = new Transform();
		
		transform.setIdentity();
		transform.origin.set(position);
		
		DefaultMotionState motionState =new DefaultMotionState(transform); 
		
		createRigidBody(motionState);
		
		world.addRigidBody(mRigidBody);
	}
	
	private void createGeometry() {
		float vertices[] = {
				1.f, 1.f, 1.f,
				-1.f, 1.f, 1.f,
				-1.f, -1.f, 1.f,
				1.f, -1.f, 1.f,
				
				1.f, 1.f, -1.f,
				-1.f, 1.f, -1.f,
				-1.f, -1.f, -1.f,
				1.f, -1.f, -1.f,
                
				1.f, 1.f, 1.f,
				1.f, -1.f, 1.f,
				1.f, -1.f, -1.f,
				1.f, 1.f, -1.f,
                
				-1.f, -1.f, 1.f,
				-1.f, -1.f, -1.f,
				1.f, -1.f, -1.f,
				1.f, -1.f, 1.f,
                
				-1.f, 1.f, 1.f,
				1.f, 1.f, 1.f,
				1.f, 1.f, -1.f,
				-1.f, 1.f, -1.f,
                
				-1.f, 1.f, -1.f,
				-1.f, -1.f, -1.f,
				-1.f, -1.f, 1.f,
				-1.f, 1.f, 1.f
		};
		
		float colors[] = {
				1.f, 1.f, 1.f, 1.f,
				1.f, 1.f, 1.f, 1.f,
				1.f, 1.f, 1.f, 1.f,
				1.f, 1.f, 1.f, 1.f,
				
                1.f, 1.f, 0.f, 1.f,
                1.f, 1.f, 0.f, 1.f,
                1.f, 1.f, 0.f, 1.f,
                1.f, 1.f, 0.f, 1.f,
                
                1.f, 0.f, 1.f, 1.f,
                1.f, 0.f, 1.f, 1.f,
                1.f, 0.f, 1.f, 1.f,
                1.f, 0.f, 1.f, 1.f,
                
                0.f, 1.f, 1.f, 1.f,
                0.f, 1.f, 1.f, 1.f,
                0.f, 1.f, 1.f, 1.f,
                0.f, 1.f, 1.f, 1.f,
                
                1.f, 0.f, 0.f, 1.f,
                1.f, 0.f, 0.f, 1.f,
                1.f, 0.f, 0.f, 1.f,
                1.f, 0.f, 0.f, 1.f,
                
                0.f, 1.f, 0.f, 1.f,
                0.f, 1.f, 0.f, 1.f,
                0.f, 1.f, 0.f, 1.f,
                0.f, 1.f, 0.f, 1.f,
		};
		
		float normals[] = {
				0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f,
                0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f,
                1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f,
                0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f,
                0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f, 0.f, 1.f, 0.f,
                -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f, -1.f, 0.f, 0.f
		};
		
		byte indices[] = new byte[6 * 2 * 3]; // 6 faces, 2 triangles per face, 3 indices per triangle
		
		for(byte i = 0; i < 6; i++) {
			indices[i * 6 + 0] = (byte) (i * 4 + 0);
			indices[i * 6 + 1] = (byte) (i * 4 + 1);
			indices[i * 6 + 2] = (byte) (i * 4 + 2);

			indices[i * 6 + 3 + 0] = (byte) (i * 4 + 0);
			indices[i * 6 + 3 + 1] = (byte) (i * 4 + 2);
			indices[i * 6 + 3 + 2] = (byte) (i * 4 + 3);			
		}
		
		mVertexBuffer = Utils.allocateFloatBuffer(vertices);
		mColorBuffer = Utils.allocateFloatBuffer(colors);
		mNormalBuffer = Utils.allocateFloatBuffer(normals);
		mIndexBuffer = Utils.allocateByteBuffer(indices);
	}
		
	private void createRigidBody(DefaultMotionState motionState) {
		CollisionShape shape = new BoxShape(new Vector3f(1.f, 1.f, 1.f));
		
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(10.f, motionState, shape, new Vector3f(10.f, 10.f, 10.f));
		
		mRigidBody = new RigidBody(rbInfo);
	}

	@Override
	public void draw(GL10 gl) {
		Transform transform = new Transform();
		
		mRigidBody.getMotionState().getWorldTransform(transform);
		
		float m[] = new float[16];
		
		transform.getOpenGLMatrix(m);
		
		gl.glPushMatrix();
		gl.glMultMatrixf(m, 0);
		
		gl.glFrontFace(GL10.GL_CW);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, 6 * 2 * 3, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		
		gl.glPopMatrix();
	}

	@Override
	public void init(GL10 gl, Context context) {
		// TODO Auto-generated method stub

	}

}
