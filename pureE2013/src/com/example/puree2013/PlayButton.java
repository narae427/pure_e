package com.example.puree2013;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import static com.example.puree2013.ListAdapterSP.sp_pButton;
import static com.example.puree2013.ListAdapterMP.mp_pButton;
import static com.example.puree2013.ListAdapterSP.sp_staticIti;
import static com.example.puree2013.ListAdapterMP.mp_staticIti;
import static com.example.puree2013.TestActivity.recorder;
import static com.example.puree2013.TestActivity.mProgressBar;
import static com.example.puree2013.MainActivity.tabIndex;
import static com.example.puree2013.Tab1_List.playBtn;
import static com.example.puree2013.ListAdapterSP.playButton_sp;
import static com.example.puree2013.ListAdapterMP.playButton_mp;
import static com.example.puree2013.Tab2_Search.sp_adapter;
import static com.example.puree2013.Tab3_Mypage.mp_adapter_iti;
import static com.example.puree2013.MainActivity.isTest;




public class PlayButton extends Activity implements Runnable{

	//static final String AUDIO_URL = "http://narae427.maru.net/Á¦¹ß.mp3";
	static MediaPlayer mediaPlayer = null;
	
	
	AlertDialog.Builder builder;
	AlertDialog alert;
	String url;
	
	public void PlayMusic(String audio_url) {
			url = audio_url;
			//Log.d("mytag",url);
			
		
		try {
			
			playAudio(url);
			//Log.d("mytag", "playMusic");
			
			

		} catch (Exception e) {
			e.printStackTrace();
			//Log.d("mytag", "FailplayMusic");
		}
		

	}
	
	
	public void StopMusic(){
		try{
			mProgressBar.setProgress(0);
		}catch(Exception e){
			
		}
		//itla.p
		mediaPlayer.stop();
		killMediaPlayer();
		if(tabIndex==1){
			sp_pButton.finish();
		}else if(tabIndex==2){
			mp_pButton.finish();
		}
		
		

	}
	

	public void playAudio(String m_url) throws Exception {
		
		

		killMediaPlayer();
		//Log.d("mytag", "killMP");

		mediaPlayer = new MediaPlayer();
		//Log.d("mytag", "newMP");
		//FileInputStream fis = new FileInputStream(m_url);
		//FileDescriptor fd = fis.getFD();
		mediaPlayer.setDataSource(m_url);
		//Log.d("mytag", "setDataSource");
		mediaPlayer.prepare();
		//Log.d("mytag", "prepare");
		mediaPlayer.start();
		//Log.d("mytag", "start");
		
		
		try {
			new Thread(PlayButton.this).start();         
			mProgressBar.setMax(mediaPlayer.getDuration());
		}catch (Exception e){
			
		}
		
		
		
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				
				try {
					if(isTest==false)
						Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Log.d("mytag", "onCompletion");
				// TODO Auto-generated method stub
				/*
				if(playing == true){
					Log.d("mytag", "return");
					playing = false;
					return;
				}
				*/
				try{ 
					
					if(mediaPlayer.isPlaying()==false){
						Log.d("mytag", "playbutton setselectable");
						
						if(tabIndex == 1){
							sp_staticIti.setSelectable(false);
							sp_adapter.notifyDataSetChanged();
						}
						else if (tabIndex ==2){
							mp_staticIti.setSelectable(false);
							mp_adapter_iti.notifyDataSetChanged();
						}
						
					}
					
					}catch(Exception e){}
			
				try{
					if(mediaPlayer.isPlaying()==false){	
						if(tabIndex == 0)
							playBtn.setSelected(false);
						else if(tabIndex == 1)
							playButton_sp.setSelected(false);
						else if(tabIndex == 2)
							playButton_mp.setSelected(false);
						Log.d("mytag", "playBtn");
						//return;
					}
					
					}catch(Exception e){}
				/*
				if(pORs==1){
					
				
					try{
						mProgressBar.setProgress(0);
						//btnV.setText("Play");
					
					}catch(Exception e){
					
					}
				}
				
				*/
				if(isTest==true){					
				
					try{
						//Thread.sleep(3000);
						//((TestActivity)TestActivity.mContext).recordStop(recorder);
						((TestActivity)TestActivity.mContext).recorder.stopRecording();
						((TestActivity)TestActivity.mContext).Intent_rActivity();
						
					
					}catch(Exception e){
					
					}
				}
				
				
			}
			
		});
		

		
	}
	

	protected void onDestroy() {
		super.onDestroy();
		killMediaPlayer();
	}

	public void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				
				mediaPlayer.release();
				mediaPlayer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	 public void run()
	    {
	        int currentPosition = 0;

	 

	        while(mediaPlayer != null)
	        {
	            try
	            {
	                Thread.sleep(50);
	                currentPosition = mediaPlayer.getCurrentPosition();
	            }
	            catch(InterruptedException e)
	            {
	            	return;
	            }
	            catch(Exception e)
	            {
	                return;
	            }

	 

	            try{
	            	if(mediaPlayer.isPlaying())
	            		{
	            		try{
	            			mProgressBar.setProgress(currentPosition);
	            		}catch(Exception e){}
	            		}
	            }catch(Exception e){}
	        }
	    }
	
	

	
	

}
