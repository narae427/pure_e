package com.example.puree2013;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import static com.example.puree2013.Tab1_List.pButton;
import static com.example.puree2013.ListAdapterSP.sp_pButton;
import static com.example.puree2013.ListAdapterMP.mp_pButton;
import static com.example.puree2013.PlayButton.mediaPlayer;
import static com.example.puree2013.TestActivity.PATH;
import static com.example.puree2013.TestActivity.RECORD_FILE;
import static com.example.puree2013.TestActivity.musicTitle;
import static com.example.puree2013.MainActivity.tabIndex;
public class ResultActivity extends Activity {
	SQLiteDatabase db;
	SQLiteOpenHelper manager;
	
	boolean running;
	ProgressWheel pw_two;
	int progress = 0;
	
	////////////////////////////////////////////////////
	String receivedScore="";	 // 노래 점수
	int score = 0;
    Socket socket;
    BufferedReader networkReader;
    BufferedWriter networkWriter;
    OutputStream os;
    
    String ip = "192.168.1.123"; // IP
    int port = 1234; // PORT번호
    
    //TextView tv;
    String mpFile = "";
    
    InnerHandler hMessageHandler;
    //static Handler mHandler;
    ProgressDialog dlg;
    //Dialog mProgress;
    
    String select="";
    PrintWriter out;
    ////////////////////////////////////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_result);
	    //sFlag = true;
	    //startbtn.setEnabled(true);
	    //playbtn.setEnabled(true);
	    
	    // TODO Auto-generated method stub
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    Toast.makeText(getApplicationContext(), "테스트가 완료되었습니다.", Toast.LENGTH_SHORT).show(); 
	      
	    connectServer();
	    
	    
	    Button retryBtn = (Button)findViewById(R.id.retrybtn);
	    Button homeBtn = (Button)findViewById(R.id.homereturnbtn);
	    Button rplayBtn = (Button)findViewById(R.id.replaybtn);
	    Button detailBtn = (Button)findViewById(R.id.detailpagebtn);
	   
	   
	    homeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mediaPlayer!=null){
					
					if(tabIndex==0){
						pButton.StopMusic();
					}
					else if(tabIndex==1){
						sp_pButton.StopMusic();
					}
					else if(tabIndex==2){
						mp_pButton.StopMusic();
					}
										
				}
				TestActivity tActivity = (TestActivity)TestActivity.testActivity;
				tActivity.finish();
				finish();
				// TODO Auto-generated method stub
				
			}
		});
	    rplayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mediaPlayer.isPlaying()==false){
					
					mediaPlayer = new MediaPlayer();
					try{
					mediaPlayer.setDataSource(PATH + RECORD_FILE);
					mediaPlayer.prepare();
					mediaPlayer.start();
					}catch(IOException e){
						
					}
				}				
			}
		});
	    retryBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					Intent rty_intent = new Intent(ResultActivity.this,TestActivity.class);
				}				
			
		});
	    detailBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					Intent det_intent = new Intent(ResultActivity.this,DetailActivity.class);
				}				
			
		});
	}
	
	private void updateDB(String musictitle){
		manager = new DBmanager(this);
	    
	    db = manager.getWritableDatabase();
		
		String aSQL = "select *"
		           + " from music"
				   + " where m_title = ?"; 
		
		String[] args = {musictitle};
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
					   + musictitle+"'";
			db.execSQL(aSQL2);
			
			String aSQL3 = "update music"
					   + " set score = "
					   + score 
					   + " where m_title = '"
					   + musictitle+"'";
			db.execSQL(aSQL3);
			
		
			manager.close();
		}
	}
		
	public void onBackPressed(){} //이거 지우면 안됨
	
	public void connectServer(){
		
		if(android.os.Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
            .permitAll().build();
            StrictMode.setThreadPolicy(policy); //이부분은 clean 하면 오류 없어져
	    }
		
		hMessageHandler = new InnerHandler( ResultActivity.this );
       /* 
        try {
        	setSocket(ip, port);
        	}catch (IOException e1) {
            e1.printStackTrace();
            }
            */
              
        ProgressThread thread = new ProgressThread();
        thread.start();
           
        DFTThread dft = new DFTThread();
        dft.start();
	}
	
	public void setSocket(String ip, int port) throws IOException {
		try {
			if(socket==null){
			socket = new Socket(ip, port);
            os = socket.getOutputStream();
            networkWriter = new BufferedWriter(new OutputStreamWriter(os));
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
		} catch (IOException e) {
			e.printStackTrace();
                //return;
			
		}
     
	}
	
	 public class ProgressThread extends Thread {
		 public void run() {
	            Message msg;
	            
	            try {
	                msg = hMessageHandler.obtainMessage();
	                msg.arg1 = 0;
	                hMessageHandler.sendMessage( msg );

	                Thread.sleep( 2000 );
	            }catch( Exception e ) {
	                msg = hMessageHandler.obtainMessage();
	                msg.arg1 = 99;
	                hMessageHandler.sendMessage( msg );
	            }
		 }
	 }
	 
	 static class InnerHandler extends Handler {
	        WeakReference< ResultActivity > mMainAct;
	        static final boolean DEFINE_PROGRESS_SPINNER = false;

	        InnerHandler( ResultActivity mMainActRef ) {
	            mMainAct = new WeakReference< ResultActivity > ( mMainActRef );
	        }

	        public void handleMessage( Message msg ) {
	            ResultActivity theMain = mMainAct.get();
	            switch( msg.arg1 ) {
	            case 0: // 다이얼로그 만들기
	                if( DEFINE_PROGRESS_SPINNER ) {
	                	//theMain.dlg = new ProgressDialog(theMain, R.style.LoadingDialog);
	                	//theMain.dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	                    theMain.dlg = ProgressDialog.show(
	                     theMain,
	                     null,
	                     "서버에서 테스트 결과를\n받아오는 중 입니다 !",
	                     false,
	                     false );//"0/100%"
	                } else {
	                    //theMain.dlg = new ProgressDialog( theMain );
	                	theMain.dlg = new ProgressDialog(theMain, R.style.LoadingDialog);
	                	theMain.dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	                	
	                	//theMain.dlg.addContentView(null, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	                	
	                    theMain.dlg.setProgressStyle( ProgressDialog.STYLE_SPINNER );
	                    theMain.dlg.setMax( 100 );
	                    theMain.dlg.setProgress( 0 );
	                    theMain.dlg.setMessage( "서버에서 테스트 결과를\n받아오는 중 입니다 !" );
	                    theMain.dlg.setCancelable( false );
	                    theMain.dlg.show();
	                }
	                break;

	            case 1: // 다이얼로그 돌리기
	                if( DEFINE_PROGRESS_SPINNER ) {
	                    //\theMain.dlg.setMessage( msg.arg2 + "/100%" );
	                } else {
	                    //theMain.dlg.setProgress( msg.arg2 );
	                }
	                break;
	 
	            case 2: // 다이얼로그 끝내기
	                theMain.dlg.dismiss();	                
	                theMain.showResult();
	                theMain.updateDB(musicTitle);
	                //Message m = mHandler.obtainMessage();
	    			//mHandler.sendMessage(m);
	                break;
	                
	            default:
	                break;
	            }
	        }
	 }
	 
	 public class DFTThread extends Thread{
		 public void run(){
			 try {
				setSocket(ip, port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			 out = new PrintWriter(networkWriter, true);	    		
	    	 select = "file";
	    	 out.println(select);
	    	
	    	 DataOutputStream dos;
	    	 FileInputStream fis;
	    	 BufferedInputStream bis;
	    		
	    	 try{
	    		 
	    		 dos = new DataOutputStream(socket.getOutputStream());
	    		 //File f = new File("/mnt/sdcard/MyProject/nativeJava.txt");
	    		 File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyProject/temp_voice.snd");
	    		 
	    		
	    		 fis = new FileInputStream(f);
	    		 bis = new BufferedInputStream(fis);
	    		 
	    		 int readBytes = 0;
	    		 byte[] buffer = new byte[1024];///////////////////////////
	    		 
	    		 while((readBytes=bis.read(buffer))!=-1){
	    				dos.write(buffer,0,readBytes);
	    				dos.flush();
	    		 }
	    		 
	    		 //dos.flush();
	    		 dos.close();
	    		 bis.close();
	    		 fis.close();
	    		 f=null;
	    		 networkWriter.close();
	    		 networkReader.close();
	    		 os.close();
	    		 socket.close();
	    		 socket = null;
	    		   	    		
	    		 setSocket(ip, port);
	    	 }catch(Exception e){
	    			e.printStackTrace();
	    	 }
	    	 
	    	 out = new PrintWriter(networkWriter, true);
	    	 select = "native";
	    	 out.println(select);
	    	 
	         //mpFile = "arirang.snd";//한글 전달 안됨 	
	    	 mpFile = musicTitle;//노래제목전송
			 out.println(mpFile);
				
			 try{
				 receivedScore = networkReader.readLine();
			 }catch(Exception e){
					e.printStackTrace();
			 }
			 
			 Message msg = hMessageHandler.obtainMessage();
	         msg.arg1 = 2;
	         hMessageHandler.sendMessage( msg );
	          
	         try {
	        	 networkWriter.close();
			     networkReader.close();
			     os.close();
			     socket.close();
			     socket = null;
			     
			     //setSocket(ip, port);
	         }catch (IOException e) {
					e.printStackTrace();
	         }
		 }
	 }
	 
	 public void showResult(){
		 
	    	
//////////////결과화면 ㅋ
		 pw_two = (ProgressWheel) findViewById(R.id.progressBarTwo);
		 ShapeDrawable bg = new ShapeDrawable(new RectShape());
		 int[] pixels = new int[] { 0xFF2E9121, 0xFF2E9121, 0xFF2E9121,
				 0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFFFFFFFF, 0xFFFFFFFF};
		 Bitmap bm = Bitmap.createBitmap(pixels, 8, 1, Bitmap.Config.ARGB_8888);
		 Shader shader = new BitmapShader(bm,
				 Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
 
      
		 final Runnable r = new Runnable() {
			 public void run() {
				 running = true;
				 score = Integer.valueOf(receivedScore);
				 while(progress<3.6*score) {
					 pw_two.incrementProgress();
					 progress++;
					 try {
						 Thread.sleep(10);
					 } catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
				 }
				 running = false;
			 }
		 };
		 
		 progress = 0;
		 pw_two.resetCount();
		 Thread s = new Thread(r);
		 s.start();
		 
		 TextView resultText = (TextView)findViewById(R.id.resultText);
		 resultText.setText("당신은\n가수입니다!짝짞짞");
		/////////////////////////////////////////////////
	 }
}
