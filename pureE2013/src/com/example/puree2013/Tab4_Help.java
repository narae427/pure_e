package com.example.puree2013;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Tab4_Help extends Activity implements OnTouchListener {
	ViewFlipper flipper;
	float xAtDown;
	float xAtUp;
	boolean mFlag = false;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_help);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	   
	    flipper = (ViewFlipper)findViewById(R.id.flipper);
	    flipper.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v!=flipper) return false; 
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			xAtDown = event.getX(); 
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			xAtUp = event.getX();
			if(xAtDown>xAtUp){
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_out));
				flipper.showNext();
				//
				//
				//
			}
			else if(xAtDown<xAtUp){
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_right_out));
				flipper.showPrevious();
			}
		}
		return true;
	}
	
	Handler mKillHandler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what ==0){
				mFlag = false;
			}
		}
	};
	public boolean onKeyDown(int KeyCode, KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(KeyCode == KeyEvent.KEYCODE_BACK){
				if(!mFlag){
					Toast.makeText(getApplicationContext(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
					mFlag = true;
					mKillHandler.sendEmptyMessageDelayed(0, 2000);
					return false;
				}else{
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		}
		return super.onKeyDown(KeyCode, event);
	}

}
