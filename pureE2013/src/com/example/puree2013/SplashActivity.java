package com.example.puree2013;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashActivity extends Activity{

	ProgressBar progressBar;
	String dirPath = "/mnt/sdcard/MyProject/Test4.db";
    File file = new File(dirPath);
    Handler sHandler;
    Runnable sRun;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.splash); 
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	   // Handler sHandler = new Handler();//메인 실행
	    sHandler = new Handler();//메인 실행
		sRun = new Runnable(){				
	    	public void run(){
	    		intent_Main();
	    		//finish();
	    	}
	    };
	    Thread Thread1 = new BackgroundThread();//디비 다운 돌리는 쓰레드
	    progressBar = (ProgressBar)findViewById(R.id.progressBar);
	    Thread1.start();
	   /*
	    sHandler.postDelayed(new Runnable(){
	    	public void run(){
	    		Intent i = new Intent(SplashActivity.this,MainActivity.class);
	    		startActivity(i);
	    		finish();
	    	}
	    },3000);
	    */
	}
	class BackgroundThread extends Thread{
		public void run(){
			progressBar.setVisibility(View.VISIBLE);
			if(!file.exists()){
				DBdownload();
			}else{
				
				sHandler.postDelayed(sRun, 3000);
				
			}
			//progressBar.setVisibility(View.GONE);
		}
	}
	
	public void intent_Main(){
		Intent main = new Intent(SplashActivity.this,MainActivity.class);
		startActivity(main);
	}
	public void DBdownload(){
		InputStream inputStream = null;
        FileOutputStream fileOutputStream = null; 
        byte[] buf = new byte[100];
        try {
            inputStream = new URL("http://narae427.maru.net/Test4.db").openStream();
            fileOutputStream = new FileOutputStream("/mnt/sdcard/MyProject/Test4.db");
            //String file_name = "Test4.db";
            //fileOutputStream = openFileOutput(file_name, MODE_WORLD_READABLE);
            int cnt = 0;
            while((cnt = inputStream.read(buf )) != -1) {
                fileOutputStream.write(buf , 0, cnt);
                fileOutputStream.flush();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            try {
                if(inputStream != null) 
                	inputStream.close();
                if(fileOutputStream != null) 
                	fileOutputStream.close();
            } catch(IOException ie) {}
        }
        
        intent_Main();
			
	}
}


