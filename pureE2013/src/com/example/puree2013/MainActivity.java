package com.example.puree2013;

import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList; 
import java.util.List; 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActivityManager;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;
import android.content.res.Resources;

public class MainActivity extends TabActivity {
	//final TabHost tabHost = getTabHost();
	static boolean isTest = false;
	InputStream input = null;
    OutputStream output = null;
    String dirPath = "/mnt/sdcard/MyProject/Test4.db";
    public static Context  mContext;
    File file = new File(dirPath);
    static int spCount = 0;
    boolean mFlag = false;
    static int tabIndex = 0;
    PopupWindow pw;
    static TabHost tabHost;
   
    //Resources res = getResources();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	startActivity(new Intent(this,SplashActivity.class));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext=this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*
		if(!file.exists()){
			DBdownload();
		}
		*/
		if(spCount == 0){
			spCount = 1;
		}
		
		Handler delayHandler = new Handler();
		Runnable run = new Runnable(){
			public void run(){
				intent_pop();
				
			}
		};
		delayHandler.postDelayed(run, 4000);
		
		tabHost = getTabHost();
		
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("")
				.setContent(new Intent(this,Tab1_List.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
		
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("")
				.setContent(new Intent(this,Tab2_Search.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
		
		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("")
				.setContent(new Intent(this,Tab3_Mypage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		
		
		tabHost.addTab(tabHost.newTabSpec("tab4")
				.setIndicator("")
				.setContent(new Intent(this,Tab4_Help.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
		tabHost.setCurrentTab(0);
		
		
	//	tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 120;
	//	tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 120;
	//	tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 150;
	//	tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 150;
		
		tabHost.getTabWidget().getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab1_bg));
		tabHost.getTabWidget().getChildAt(1).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab2_bg));
		tabHost.getTabWidget().getChildAt(2).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab3_bg));
		tabHost.getTabWidget().getChildAt(3).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab4_bg));
		
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				tabIndex = tabHost.getCurrentTab();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
	
	
	public String getTopActivity(){
    	ActivityManager am = (ActivityManager)this.getSystemService(this.ACTIVITY_SERVICE);
    	List<ActivityManager.RunningTaskInfo> task = am.getRunningTasks(1);
    	
    	ComponentName topActivity = task.get(0).topActivity;
    	String nameTopActivity = topActivity.getPackageName();
    	
    	return nameTopActivity;
    }
	
	public void intent_pop(){
		startActivity(new Intent(this,PopupDialog.class));
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
