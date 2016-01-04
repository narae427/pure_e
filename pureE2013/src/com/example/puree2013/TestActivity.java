package com.example.puree2013;
import java.io.IOException;


import android.widget.ProgressBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import static com.example.puree2013.ListAdapterSP.sp_pButton;
import static com.example.puree2013.ListAdapterMP.mp_pButton;
import static com.example.puree2013.Tab1_List.pButton;
import static com.example.puree2013.PlayButton.mediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.media.MediaRecorder;
import android.content.Context;
import static com.example.puree2013.MainActivity.tabIndex;

public class TestActivity extends Activity {
	boolean f = false;
	ImageView icon_item;
	ImageButton finishBtn;
	
	static ProgressBar mProgressBar;
	static String musicTitle;
	String data_title, data_artist, data_url, data_lyrics;
	//static MediaRecorder recorder;
	static Recorder recorder;
	static String PATH = "/mnt/sdcard/MyProject";
	static String RECORD_FILE = "/voice.wav";
	public static Context  mContext;
	private static final int DIALOG_1 = 1;
	public static Activity testActivity;
	
	//extView btnV;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_test);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		startActivity(new Intent(this,CountActivity.class));
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext=this;
		testActivity = TestActivity.this;
		
		
		icon_item = (ImageView)findViewById(R.id.iconItem_t);
		
		
		TextView data_item01 = (TextView)findViewById(R.id.dataItem01_t);
		TextView data_item02 = (TextView)findViewById(R.id.dataItem02_t);
		TextView data_item03 = (TextView)findViewById(R.id.dataItem03_t);
		mProgressBar = (ProgressBar)findViewById(R.id.progressBar1);
		finishBtn = (ImageButton)findViewById(R.id.testFinish);
		finishBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_1);
			}
		});
		Intent test_intent = this.getIntent();
		byte[] img_icon = test_intent.getByteArrayExtra("icon");
		data_title = test_intent.getStringExtra("title");
		data_artist = test_intent.getStringExtra("artist");
		data_url = test_intent.getStringExtra("url");
		data_lyrics = test_intent.getStringExtra("lyrics");
		
		
		
		icon_item.setImageBitmap(BitmapFactory.decodeByteArray(img_icon, 0, img_icon.length));		
		data_item01.setText(data_title);
		data_item02.setText(data_artist);
		data_item03.setText(data_lyrics);
		
		 //new Thread(TestActivity.this).start();         

         mProgressBar.setVisibility(ProgressBar.VISIBLE);
         mProgressBar.setProgress(0);
         
         //mProgressBar.setMax(mediaPlayer.getDuration());
        // pORs = 2;
		musicTitle = data_title;
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		try{
			if(tabIndex==0){
				pButton.StopMusic();
			}
			else if(tabIndex==1){
				sp_pButton.StopMusic();
			}else if(tabIndex==2){
				mp_pButton.StopMusic();
			}
		
		}catch(Exception e){}
		//if(startbtn.isEnabled()==true){
			//sFlag = false;
			
			Toast.makeText(getApplicationContext(), "테스트가 시작되었습니다.", Toast.LENGTH_SHORT).show();
			final String url = data_url;	
			
			Handler delayHandler = new Handler();
			Runnable run = new Runnable(){
				public void run(){
					if(tabIndex==0){
						pButton.PlayMusic(url);
					}
					else if(tabIndex==1){
						sp_pButton.PlayMusic(url);
					}else if(tabIndex==2){
						mp_pButton.PlayMusic(url);
					}
				}
			};
						
			
			try{
				recorder = new Recorder(PATH + RECORD_FILE);
				Thread threadRecorder = new Thread(recorder);
				threadRecorder.start();
				//Thread.sleep(3000);
				//pButton.PlayMusic(url);
				delayHandler.postDelayed(run, 3000);
				
			}catch(Exception e){}
			
			
			//recordStop();
		//}
		
		 
		
		///////////////////////////////////////////////
		
			//adapter_mp.notifyDataSetChanged();
		/*
		 playbtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					pORs = 1;
				
					//btnVv = (TextView)findViewById(R.id.playbtn_t);
					String url = data_url;
					//mProgressBar.setMax(pButton.mediaPlayer.getDuration());
					
					if(playbtn.isSelected()==true){
						//btnVv.setText("Play");
						playbtn.setSelected(false);
						pButton.StopMusic();
						
					}
					
					else if(playbtn.isSelected()==false){
						//btnVv.setText("Stop");
						playbtn.setSelected(true);
						pButton.PlayMusic(url);
						
						//btnVv.setText("Play");
						 
						
					}
				}
				
				
			});	
			*/
		/*
		startbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pORs = 2;
				playbtn.setSelected(false);
				
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				
				try{
				pButton.StopMusic();
				}catch(Exception e){}
				if(startbtn.isEnabled()==true){
					//sFlag = false;
					
					Toast.makeText(getApplicationContext(), "테스트가 시작되었습니다.", Toast.LENGTH_SHORT).show();
					final String url = data_url;	
					
					Handler delayHandler = new Handler();
					Runnable run = new Runnable(){
						public void run(){
							pButton.PlayMusic(url);
						}
					};
								
					
					try{
						
						recordStart();
						//Thread.sleep(3000);
						//pButton.PlayMusic(url);
						delayHandler.postDelayed(run, 3000);
						
					}catch(Exception e){}
					
					
					//recordStop();
				}
				
				 
				
				///////////////////////////////////////////////
				db = manager.getWritableDatabase();
				
				String aSQL = "select *"
				           + " from music"
						   + " where m_title = ?"; 
				
				String[] args = {musicTitle};
				Cursor count_c = db.rawQuery(aSQL, args);
				
				if(count_c.getCount()==0){
										
					return;
				}else{
					
					int countCol = count_c.getColumnIndex("count");
					int count = 0;
					int newcount = 0;
					
					Resources res = getResources();
					
					while(count_c.moveToNext()) {
						count = count_c.getInt(countCol);
					
					}
					newcount = count + 1;
					count_c.close();
					//int newcount = count+1;
					//String str="";
					//String nullstr = "";
					//str =nullstr + newcount ;
					
					String aSQL2 = "update music"
							   + " set count = '"
							   + newcount +"'"
							   + " where m_title = '"
							   + musicTitle+"'";
					db.execSQL(aSQL2);
					
				
					manager.close();
					//adapter_mp.notifyDataSetChanged();
					
				}	
				//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
				
			}
		});
		
		*/
		
		
	}
	
	public void onBackPressed(){
	/*
		if(f==true){//녹음중		
				
			showDialog(DIALOG_1);
			
		}
		
		if(f==false){//녹음 안중
			if(mediaPlayer != null){
				pButton.StopMusic();
				super.onBackPressed();
			}
			else{
			super.onBackPressed();
			}
		}
		*/
		
	}
	
	
	
	public Dialog onCreateDialog(int id){
		switch(id){
		case DIALOG_1:
			return alertDial();
		}
		return null;
		
	}
	
	public Dialog alertDial(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("테스트를 중단하시겠습니까?").setCancelable(false)
		.setPositiveButton("예", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try{
					if(tabIndex==0){
						pButton.StopMusic();
					}
					else if(tabIndex==1){
						sp_pButton.StopMusic();
					}else if(tabIndex==2){
						mp_pButton.StopMusic();
					}
				}catch(Exception e){}
				//recordStop(recorder);
				recorder.stopRecording();
				finish();
			}
		})
		
		.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				
			}
		});
		AlertDialog alert = builder.create();
		return alert;
	}
	
	
	public void Intent_rActivity(){
		Intent result_intent = new Intent(TestActivity.this,ResultActivity.class);
		startActivity(result_intent);
	}
	
	public void killActivity(){

		if(mediaPlayer!=null){
			if(tabIndex==0){
				pButton.StopMusic();
			}
			else if(tabIndex==1){
				sp_pButton.StopMusic();
			}else if(tabIndex==2){
				mp_pButton.StopMusic();
			}
		}
		try{
			//recordStop(recorder);
			recorder.stopRecording();
			
		}catch(Exception e){}
		
		
	}
}
