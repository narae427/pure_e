package com.example.puree2013;

import static com.example.puree2013.ListAdapterMP.mp_pButton;

import com.example.puree2013.R.color;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;



public class Tab3_Mypage extends Activity {
	Button play_btn;
	ListView listV1, listV2;
	static ListAdapterMP mp_adapter_iti;
	MyPageAdapter mp_adapter;
	DBmanager dBase;
	SQLiteDatabase db;
	SQLiteOpenHelper manager;
	boolean mFlag = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_mypage);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	   
	    
	    TabHost my_tabHost = (TabHost)findViewById(R.id.my_tabhost);
        TabHost.TabSpec spec;
                 
        // 탭의 background가 될 이미지
        ImageView tabwidget01 = new ImageView(this);
        tabwidget01.setImageResource(R.drawable.tab3_1);
        ImageView tabwidget02 = new ImageView(this);
        tabwidget02.setImageResource(R.drawable.tab3_2);
	  
    
	    
	    // TODO Auto-generated method stub
	    mp_adapter_iti=new ListAdapterMP(this);
	    mp_adapter= new MyPageAdapter(this);
	    listV1 = new ListView(this);
	    listV2 = new ListView(this);
	    listV2.setSelector(new PaintDrawable(0x00000000));
	    listV1.setCacheColorHint(Color.TRANSPARENT);
	    listV2.setCacheColorHint(Color.TRANSPARENT);
	    listV1.setDivider(null);
	    listV2.setDivider(null);
		LinearLayout linLayout1 = (LinearLayout)findViewById(R.id.tab3_1);
		LinearLayout linLayout2 = (LinearLayout)findViewById(R.id.tab3_2);
		linLayout1.addView(listV1);
		linLayout2.addView(listV2);
		
			
		manager = new DBmanager(this);
		db = manager.getWritableDatabase();
		
		mp_pButton = new PlayButton();
		
		String aSQL = "select * "
		           + " from music"
		           + " where count > 0"
		           + " order by count desc"; 
		 
		Cursor cursor = db.rawQuery(aSQL, null); 
		AddCursorData(cursor);
		listV1.setAdapter(mp_adapter_iti);
		
		
		String aSQL2 = "select * "
		           + " from music"
		           + " where count > 0"
		           + " order by count desc"; 
		Cursor cursor2 = db.rawQuery(aSQL2, null); 
		AddCursorData2(cursor2);
		listV2.setAdapter(mp_adapter);
		
		
	    TabHost mp_th = (TabHost)findViewById(R.id.my_tabhost);
	    //mp_th.setCacheColorHint(Color.TRANSPARENT);
	    mp_th.setup();
	    
	    mp_th.addTab(mp_th.newTabSpec("tab3_1")
	    		.setIndicator("").setContent(R.id.tab3_1));
	    
	    mp_th.addTab(mp_th.newTabSpec("tab3_2")
	    		.setIndicator("").setContent(R.id.tab3_2));
	    
	    mp_th.getTabWidget().getChildAt(0).getLayoutParams().height = 100;
	    mp_th.getTabWidget().getChildAt(1).getLayoutParams().height = 100;
	  
	   mp_th.getTabWidget().getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab3_1));
	  mp_th.getTabWidget().getChildAt(1).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab3_2));
	    
	    ///////////////////////////////////////////////////////////////////
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
	}
	

	

	public void AddCursorData(Cursor outCursor)
	{
		mp_adapter_iti.clear();
		
		int musicNumberCol = outCursor.getColumnIndex("music_num");
		int titleCol = outCursor.getColumnIndex("m_title");
		int artistCol = outCursor.getColumnIndex("m_artist");
		int imageCol = outCursor.getColumnIndex("m_image");
		int urlCol = outCursor.getColumnIndex("m_url");
		int lyricsCol = outCursor.getColumnIndex("lyrics");
		int scoreCol = outCursor.getColumnIndex("score");
		
		Resources res = getResources();
		
		while(outCursor.moveToNext()) {
			int music_num = outCursor.getInt(musicNumberCol);
			String m_title = outCursor.getString(titleCol);
			String m_artist = outCursor.getString(artistCol);
			byte[] m_image = outCursor.getBlob(imageCol);
			String m_url = outCursor.getString(urlCol);
			String lyrics = outCursor.getString(lyricsCol);
			int score = outCursor.getInt(scoreCol);
			mp_adapter_iti.addItem(new IconTextItem(m_image,m_title,m_artist,m_url,lyrics,score));
			
	
		}
		outCursor.close();
	}
	public void AddCursorData2(Cursor outCursor)
	{
		mp_adapter.clear();
		
		int musicNumberCol = outCursor.getColumnIndex("music_num");
		int titleCol = outCursor.getColumnIndex("m_title");
		int artistCol = outCursor.getColumnIndex("m_artist");
		int imageCol = outCursor.getColumnIndex("m_image");
		int urlCol = outCursor.getColumnIndex("m_url");
		int lyricsCol = outCursor.getColumnIndex("lyrics");
		int scoreCol = outCursor.getColumnIndex("score");
		
		Resources res = getResources();
		
		while(outCursor.moveToNext()) {
			int music_num = outCursor.getInt(musicNumberCol);
			String m_title = outCursor.getString(titleCol);
			String m_artist = outCursor.getString(artistCol);
			byte[] m_image = outCursor.getBlob(imageCol);
			String m_url = outCursor.getString(urlCol);
			String lyrics = outCursor.getString(lyricsCol);
			int score = outCursor.getInt(scoreCol);
			mp_adapter.addItem(new IconTextItem(m_image,m_title,m_artist,m_url,lyrics,score));
			
	
		}
		outCursor.close();
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


