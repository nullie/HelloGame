package org.nullie.hellogame;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class HelloGameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGLView = new HelloGameSurfaceView(this);
        
        setContentView(mGLView);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }
	    
    HelloGameSurfaceView mGLView;
}

class HelloGameSurfaceView extends GLSurfaceView {

	public HelloGameSurfaceView(Context context) {
		super(context);
		
		mRenderer = new World(context);
		setRenderer(mRenderer);
	}
	
	World mRenderer;
}
