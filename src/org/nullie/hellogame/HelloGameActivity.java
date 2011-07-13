package org.nullie.hellogame;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

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
	
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		queueEvent(new Runnable() {
			public void run() {
			}
		});
		
		return true;
	}
	
	World mRenderer;
}
