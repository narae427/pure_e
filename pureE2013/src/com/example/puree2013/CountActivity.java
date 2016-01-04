package com.example.puree2013;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;

public class CountActivity extends Activity {
	ImageView cntAni;
	AnimationDrawable ani;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.count);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    cntAni = (ImageView)findViewById(R.id.imgv);
	    
		cntAni.setBackgroundResource(R.drawable.count_anim);
		ani = (AnimationDrawable)cntAni.getBackground();
	    
	    // TODO Auto-generated method stub
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) 
	{  
		
		Handler handler = new Handler (){
	    	public void handleMessage(Message msg){
	    		finish();
	    	}
	    };
	    handler.sendEmptyMessageDelayed(0,3000);
	    ani.start();
		super.onWindowFocusChanged(hasFocus); 
	} 
	
	



}
