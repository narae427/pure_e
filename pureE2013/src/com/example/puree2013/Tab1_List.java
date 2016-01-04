package com.example.puree2013;

import android.app.Activity;
import static com.example.puree2013.PlayButton.mediaPlayer;
import static com.example.puree2013.MainActivity.isTest;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.puree2013.MyPageAdapter.ViewHolder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
public class Tab1_List extends Activity {

	 private int mSelectedPosition;
	 SQLiteDatabase db;
	 SQLiteOpenHelper manager;
	 Cursor cursor;
	 ArrayList<String> titleArr ;
	 ArrayList<String> artistArr ;
	 ArrayList<String> urlArr ;
	 ArrayList<byte[]> iconArr;
	 ArrayList<String> lyricsArr;
	 static Button playBtn, testBtn;
	 boolean mFlag = false;
	 //static boolean playing;
	 AlbumTextAdapter adapter;
	 ListView listV;
	 static PlayButton pButton;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.tab_list);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);    
	    
	    manager = new DBmanager(this); 
	    db = manager.getReadableDatabase();
	    
 	    String aSQL = "select * "
 		           + " from music";
 	    cursor = db.rawQuery(aSQL, null); 
 	    
	    playBtn = (Button)findViewById(R.id.playBtn);
	    testBtn = (Button)findViewById(R.id.testBtn);
	    
	    adapter=new AlbumTextAdapter(this);
		listV = new ListView(this);
		listV.setCacheColorHint(Color.TRANSPARENT);
		listV.setDivider(null);
		listV.requestDisallowInterceptTouchEvent(true);
		listV.setVerticalScrollBarEnabled(false);
		listV.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		
		LinearLayout linLayout = (LinearLayout)findViewById(R.id.album_text_list);
		linLayout.addView(listV);
		
		
	    CoverFlow coverFlow = (CoverFlow)findViewById(R.id.cover_flow);
	    coverFlow.setAdapter(new ImageAdapter(this));
	    ImageAdapter coverImageAdapter =  new ImageAdapter(this);
	     
	     mSelectedPosition = 20;
	    // int albumNumber = mSelectedPosition + 1;
	     
	     // set to show shadow images (we call it as reflected images)
	     // this method doesn't work originally (maybe need to modify)
	     
	     coverImageAdapter.createReflectedImages(); // original method
	     coverFlow.setAdapter(coverImageAdapter);
	     
	    // Sets the spacing between items(images) in a Gallery
	    // coverFlow.setSpacing(-25); // original value
//	     coverFlow.setSpacing(-50); // when not showing reflected images
	     //coverFlow.setSpacing(-180); // custom value
	     coverFlow.setSpacing(-160);

	     // Set Selected Images at starting Gallery (number is index of selection)
//	     coverFlow.setSelection(4, true); // original method
	     coverFlow.setSelection(mSelectedPosition, true); // original method
	     //coverFlow.setSelection(mSelectedPosition, false);
//	     coverFlow.setSelection(8, true);
	     
	     // Set persistence time of animation
//	     coverFlow.setAnimationDuration(1000); // original value
	     coverFlow.setAnimationDuration(500); // original value
	     
	     // set Zoom level
	     coverFlow.setMaxZoom(-120); // original value
//	     coverFlow.setMaxZoom(-200); // custom value
	     
	     // set Rotation angle of images
	    // coverFlow.setMaxRotationAngle(180); // optimal value
	     coverFlow.setMaxRotationAngle(200); // test value (deprecated)
	     /*if(tabIndex==1){
	    	 sp_pButton = new PlayButton();
	     }
	     else if(tabIndex==2){
	    	 mp_pButton = new PlayButton();
	     }
	     	*/
	     pButton = new PlayButton();
	     coverFlow.setOnItemSelectedListener(new OnItemSelectedListener()
		{
	    	
			@Override 
			public void onItemSelected(AdapterView<?> parent,View view,int position,long id)
			
	        {
				
				mSelectedPosition = position;
				
				playBtn.setSelected(false);
				
				playBtn.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							isTest = false;
							String url = urlArr.get(mSelectedPosition).toString();
							//if(mediaPlayer!=null){
								//playing = true;
							//}
							
							if(playBtn.isSelected()==true){
								playBtn.setSelected(false);
								pButton.StopMusic();
								Log.d("mytag", "1");
							}
							
							else if(playBtn.isSelected()==false){
								playBtn.setSelected(true);
								pButton.PlayMusic(url);
								Log.d("mytag", "2");
								
							}
					
						}
						
					});	
	
			        testBtn.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							isTest = true;
							try{
								playBtn.setSelected(false);
								pButton.StopMusic();
		    				}catch(Exception e){}
							
							Intent test_intent = new Intent(Tab1_List.this,TestActivity.class);
							
							test_intent.putExtra("icon", iconArr.get(mSelectedPosition));
							test_intent.putExtra("title", titleArr.get(mSelectedPosition).toString());
							test_intent.putExtra("artist", artistArr.get(mSelectedPosition).toString());
							test_intent.putExtra("url", urlArr.get(mSelectedPosition).toString());
							test_intent.putExtra("lyrics", lyricsArr.get(mSelectedPosition).toString());
							
							startActivity(test_intent);
							
							
						}
					});       
			       
			       
			        listV.setAdapter(adapter);
			        listV.setSelection(position);
			        
			        
	        }

			@Override public void onNothingSelected(AdapterView<?> parent)
	        {
				
	        }
		});
	     
	     listV.setAdapter(adapter);
	     if(adapter.isEmpty()==true){
				Log.d("mytag", "empty");
				for(int i=0; i<titleArr.size();i++){
					adapter.addItem(new AlbumTextItem(titleArr.get(i),artistArr.get(i)));
					}
				}
	     listV.setSelection(mSelectedPosition);
	     
	     	playBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isTest = false;
					String url = urlArr.get(mSelectedPosition).toString();
					if(playBtn.isSelected()==true){
						playBtn.setSelected(false);
						pButton.StopMusic();
					}
					
					else if(playBtn.isSelected()==false){
						playBtn.setSelected(true);
						pButton.PlayMusic(url);
						
						
					}
					
				}
			});	

	        testBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					isTest = true;
					try{
						playBtn.setSelected(false);
						pButton.StopMusic();
    				}catch(Exception e){}
					
					Intent test_intent = new Intent(Tab1_List.this,TestActivity.class);
					
					test_intent.putExtra("icon", iconArr.get(mSelectedPosition));
					test_intent.putExtra("title", titleArr.get(mSelectedPosition).toString());
					test_intent.putExtra("artist", artistArr.get(mSelectedPosition).toString());
					test_intent.putExtra("url", urlArr.get(mSelectedPosition).toString());
					test_intent.putExtra("lyrics", lyricsArr.get(mSelectedPosition).toString());
					
					startActivity(test_intent);
					
					
				}
			});       
	}
	
	public class AlbumTextAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private Context mContext; 
	  
	    public List<AlbumTextItem> aItems = new ArrayList<AlbumTextItem>(); 
	  
	    public AlbumTextAdapter(Context context) { 
	        mContext = context; 
	        
	        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    } 
	  
	    public void clear() { 
	        aItems.clear(); 
	    } 
	      
	    public void addItem(AlbumTextItem it) { 
	        aItems.add(it); 
	    } 
	  
	    public void setListItems(List<AlbumTextItem> lit) { 
	        aItems = lit; 
	    } 
	  
	    public int getCount() { 
	        return aItems.size(); 
	    } 
	  
	    public Object getItem(int position) { 
	        return aItems.get(position); 
	    } 
	  
	    public boolean areAllItemsSelectable() { 
	        return false; 
	    } 
	  
	    public boolean isSelectable(int position) { 
	        try { 
	            return aItems.get(position).isSelectable(); 
	        } catch (IndexOutOfBoundsException ex) { 
	            return false; 
	        } 
	    } 
	  
	    public long getItemId(int position) { 
	        return position; 
	    } 
	    
	    public class ViewHolder {
	    	
	    	TextView album_title;
	    	TextView album_artist;
	    }

		 
	    public View getView(int position, View convertView, ViewGroup parent) { 
	    	View cv = convertView;
	    	final ViewHolder holder;
	    	 if (cv == null) { 
	    		 holder = new ViewHolder();
	    		 
	         	cv = inflater.inflate(R.layout.album_text, parent, false); 
	          	
	         	holder.album_title = (TextView)cv.findViewById(R.id.album_title);
	         	holder.album_artist = (TextView)cv.findViewById(R.id.album_artist);
	         	
	         	cv.setTag(holder);
	         }else{
	         	holder = (ViewHolder)cv.getTag();
	         }
	    	 
	    	 if(aItems.get(position) != null){
	        	holder.album_title.setText(aItems.get(position).getData(0));
	        	holder.album_title.setPaintFlags(holder.album_title.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
	         	holder.album_artist.setText(aItems.get(position).getData(1));
	         	listV.getLastVisiblePosition();
	         	
	         }
	        
	        return cv; 
	    } 
	
	
	   
	    
	  
	} 

	
	public class ImageAdapter extends BaseAdapter {
	  	 
	  	 
	    int mGalleryItemBackground;
	    private Context mContext;

	    private FileInputStream fis;
	    
	    public void getAlbumImages() {
	    	// adapter.getItem(0);
	    	 
	    }


	    private ImageView[] mImages;
	    
	    public ImageAdapter(Context c) {
			super();
			mContext = c;
			mImages = new ImageView[cursor.getCount()];
			titleArr = new ArrayList<String>();
        	artistArr= new ArrayList<String>();
        	iconArr = new ArrayList<byte[]>();
        	urlArr = new ArrayList<String>();
        	lyricsArr = new ArrayList<String>();
			
	    }
	     public boolean createReflectedImages() {
	         //The gap we want between the reflection and the original image
	         final int reflectionGap = 4;
	         
	         int index = 0;
	 	 
	 			int musicNumberCol = cursor.getColumnIndex("music_num");
	        	 int titleCol = cursor.getColumnIndex("m_title");
	        	 int artistCol = cursor.getColumnIndex("m_artist");
	        	 int imageCol = cursor.getColumnIndex("m_image");
	        	 int urlCol = cursor.getColumnIndex("m_url");
	        	 int lyricsCol = cursor.getColumnIndex("lyrics");
	        	
	        	 while(cursor.moveToNext()) {
	        		  String m_title = cursor.getString(titleCol);
	        		  String m_artist = cursor.getString(artistCol);
	        		  byte[] m_image = cursor.getBlob(imageCol);
	        		  String m_url = cursor.getString(urlCol);
	        		  String lyrics = cursor.getString(lyricsCol);
	        		        	
	        		  titleArr.add(m_title);
	        		  artistArr.add(m_artist);
	        		  iconArr.add(m_image);
	        		  urlArr.add(m_url);
	        		  lyricsArr.add(lyrics);
	        		  
	        		  Bitmap originalImage  = BitmapFactory.decodeByteArray(m_image, 0, m_image.length);
	        	
	        		  int width = originalImage.getWidth();
	        		  int height = originalImage.getHeight();
	          
	    
	          //This will not scale but will flip on the Y axis
	          Matrix matrix = new Matrix();
	          matrix.preScale(1, -1); // original code
	          
	          //Create a Bitmap with the flip matrix applied to it.
	          //We only want the bottom half of the image
	          Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);
	          
	              
	          //Create a new bitmap with same width but taller to fit reflection
	          Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
	            , (height + height/2), Config.ARGB_8888);
	        
	         //Create a new Canvas with the bitmap that's big enough for
	         //the image plus gap plus reflection
	         Canvas canvas = new Canvas(bitmapWithReflection);
	         //Draw in the original image
	         canvas.drawBitmap(originalImage, 0, 0, null);
	         //Draw in the gap
	         Paint deafaultPaint = new Paint();
	         canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
	         //Draw in the reflection
	         canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
	         
	         //Create a shader that is a linear gradient that covers the reflection
	         Paint paint = new Paint(); 
	         LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, 
	           bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
	           TileMode.CLAMP); 
	         //Set the paint to use this shader (linear gradient)
	         paint.setShader(shader); 
	         //Set the Transfer mode to be porter duff and destination in
	         paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
	         //Draw a rectangle using the paint with our linear gradient
	         canvas.drawRect(0, height, width, 
	           bitmapWithReflection.getHeight() + reflectionGap, paint); 
	         
	         ImageView imageView = new ImageView(mContext);
	         imageView.setImageBitmap(bitmapWithReflection);
	         
	         
	         // set size of Images
	         //imageView.setLayoutParams(new CoverFlow.LayoutParams(500, 500)); // original value
	         imageView.setLayoutParams(new CoverFlow.LayoutParams(380, 330)); // custom value
//	         imageView.setScaleType(ScaleType.MATRIX); // original code (deprecated)
	         
	         // set aspect of Images (this setting is needed in order to use reflected images (!)
	         //imageView.setScaleType(ScaleType.CENTER_INSIDE); // custom code
	         imageView.setScaleType(ScaleType.FIT_CENTER); // custom code
	         //imageView.setPadding(10, 0, 10, 0);
	         mImages[index++] = imageView;
	         
	          originalImage.recycle();
		      reflectionImage.recycle();
		      //Log.d("mytag", "recycle");
		      //bitmapWithReflection.recycle();
	         
	         }
	         cursor.close();
	         return true;
	     }

	  
		public int getCount() {
	        //return mImageIds.length;
			return cursor.getCount();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	    
	    	return mImages[position];
	    }
	    
	    
	  
	  /** Returns the size (0.0f to 1.0f) of the views 
	     * depending on the 'offset' to the center. */ 
	     public float getScale(boolean focused, int offset) { 
	       /* Formula: 1 / (2 ^ offset) */ 
	         return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
	     } 
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
