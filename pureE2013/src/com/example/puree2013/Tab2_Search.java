package com.example.puree2013;

import static com.example.puree2013.ListAdapterSP.sp_pButton;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.*;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.puree2013.MainActivity.tabHost;




public class Tab2_Search extends Activity {
	
	Button btn, title_btn, artist_btn, play_btn;
	TextView text, s_result;
	EditText searchEdit;
	String searchStr;
	String searchStrQuery;
	RadioGroup radio;
	ListView listV;
	ScrollView scrollV;
	static ListAdapterSP sp_adapter;
	//DBmanager dBase;
	SQLiteDatabase db;
	SQLiteOpenHelper manager;
	boolean mFlag = false;

   
   // Intent test_intent;
	
	InputMethodManager imm; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_search);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().setFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		//getWindow().setFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		
		imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		
		sp_adapter=new ListAdapterSP(this);
		listV = new ListView(this);
		listV.setCacheColorHint(Color.TRANSPARENT);
		listV.setDivider(null);
		//listV.setDivider(new ColorDrawable(Color.parseColor("#ffcc66")));
		//listV.setDividerHeight(1);
		
		LinearLayout linLayout = (LinearLayout)findViewById(R.id.musicList);
		linLayout.addView(listV);
		//linLayout.setBackgroundColor(Color.TRANSPARENT);
		
		
		searchEdit = (EditText)findViewById(R.id.searchBox);
		searchEdit.setInputType(0);
		btn = (Button)findViewById(R.id.searchBtn);
		radio = (RadioGroup)findViewById(R.id.radioGroup1);
		title_btn = (RadioButton)findViewById(R.id.radio01);
		artist_btn = (RadioButton)findViewById(R.id.radio02);
		s_result = (TextView)findViewById(R.id.resultTV);
	


		//imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);        
		
		manager = new DBmanager(this);
		db = manager.getWritableDatabase();
		
		sp_pButton = new PlayButton();
		//test_intent = new Intent(MainActivity.this, TestActivity.class);		
		
		
		String aSQL = "select * "
		           + " from music"
		           + " order by m_title asc"; 
		 
		      // String[] args = {searchStrQuery}; 
		 
		       Cursor cursor = db.rawQuery(aSQL, null); 
		
		
		AddCursorData(cursor);
		listV.setAdapter(sp_adapter);
		//imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
		
		searchEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				searchEdit.setInputType(1);
			
					//tabHost.getTabWidget().setVisibility(View.GONE);//getChildAt(1).setVisibility(View.GONE);
				
			}
		});
		
		
		btn.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {		

					imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);	
					
					searchStr = searchEdit.getText().toString();
					searchStrQuery = "%"+searchStr+"%";
					db = manager.getWritableDatabase();
			
					if(radio.getCheckedRadioButtonId()==R.id.radio01) {
										
						 String aSQL = "select * "
						           + " from music"
						           + " where m_title like ?"
						           + " order by m_title asc"; 
						       String[] args = {searchStrQuery};					       
						       Cursor cursor1 = db.rawQuery(aSQL,args);
						    
						AddCursorData(cursor1);
						
					} else if(radio.getCheckedRadioButtonId()==R.id.radio02) {
						
						
						String aSQL = "select * "
						           + " from music"
						           + " where m_artist like ?"
						           + " order by m_artist asc"; 
						 
						       String[] args = {searchStrQuery}; 
						 
						       Cursor cursor2 = db.rawQuery(aSQL, args); 
						       
								
				
						AddCursorData(cursor2);
				
					}		
			
					listV.setAdapter(sp_adapter);
				searchEdit.setText("");
				db.close();
				
			}
			
		});
		

		
	}
	protected void onDestroy() {
		super.onDestroy();
		
	}
	

	

	public void AddCursorData(Cursor outCursor)
	{
		sp_adapter.clear();
		
		if(outCursor.getCount()==0){
			s_result.setText(" 검색 결과가 없습니다 ! ");
			
			return;
		}
		s_result.setText("");
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
			sp_adapter.addItem(new IconTextItem(m_image,m_title,m_artist,m_url,lyrics,score));
			
	
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
