package com.example.puree2013;


import java.util.ArrayList; 
import static com.example.puree2013.MainActivity.isTest;
import java.util.List; 
import android.app.ActivityManager;
import android.content.Context; 
import android.util.Log;
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.example.puree2013.Tab2_Search.sp_adapter;
  
public class ListAdapterSP extends BaseAdapter {
	static Button playButton_sp;
	static IconTextItem sp_staticIti;
	IconTextView itemView;
	static PlayButton sp_pButton;
	int getPrevPosition = -1;
	Button prev_play_btn;
	private LayoutInflater inflater;
    private Context mContext; 
    public static List<IconTextItem> mItems = new ArrayList<IconTextItem>(); 
    
    
    public ListAdapterSP(Context context) { 
        mContext = context; 
       
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } 
  
    public void clear() { 
        mItems.clear(); 
    } 
      
    public void addItem(IconTextItem it) { 
        mItems.add(it); 
    } 
  
    public void setListItems(List<IconTextItem> lit) { 
        mItems = lit; 
    } 
  
    public int getCount() { 
        return mItems.size(); 
    } 
  
    public Object getItem(int position) { 
        return mItems.get(position); 
    } 
  
    public boolean areAllItemsSelectable() { 
        return false; 
    } 

    public long getItemId(int position) { 
        return position; 
    } 
    
    public class ViewHolder {
    	ImageView iv_album;
    	TextView tv_title;
    	TextView tv_artist;
    	String url;
    	Button play_btn;
    	Button test_btn;
    	
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	//spORmp=true;
    	View cv = convertView;
    	final ViewHolder holder;
    	//Log.d("mytag", "IconTextListAdapter");
    	
    	//String ta = ((MainActivity)MainActivity.mContext).getTopActivity();
    	
    	
    	//tabHost.getTabWidget().findFocus();
    	//Log.d("mytag", String.valueOf(tabIndex));
    	
        if (cv == null) { 
        	
        	holder = new ViewHolder();
        	cv = inflater.inflate(R.layout.sch_listrow, parent, false);
        	holder.iv_album = (ImageView)cv.findViewById(R.id.iconItem);
        	holder.tv_title = (TextView)cv.findViewById(R.id.dataItem01);
        	holder.tv_artist = (TextView)cv.findViewById(R.id.dataItem02);
        	holder.play_btn = (Button)cv.findViewById(R.id.play);        	
        	holder.test_btn = (Button)cv.findViewById(R.id.test); 
        	
        	holder.play_btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isTest = false;
					int getPosition = (Integer)v.getTag();	
					playButton_sp = holder.play_btn;
					
					if(v.getId()==R.id.play){
					
					if(getPrevPosition != -1 && mItems.get(getPrevPosition).isSelectable()==true){//orange
						//Log.d("mytag", "1 ok");
						if(getPrevPosition == getPosition){
							//Log.d("mytag", "1-1 ok");
							mItems.get(getPrevPosition).setSelectable(true);
							
							sp_adapter.notifyDataSetChanged();
														
							}
						else{
							//Log.d("mytag", "1-2 ok");
							mItems.get(getPrevPosition).setSelectable(false);//yellow
							
							sp_adapter.notifyDataSetChanged();
							
							}
						}
					
					String url = mItems.get(getPosition).getData(2);
					if(mItems.get(getPosition).isSelectable()==false){//yellow
						//Log.d("mytag", "2 ok");
						prev_play_btn = holder.play_btn;
						getPrevPosition = getPosition;
						mItems.get(getPosition).setSelectable(true);//orange
						sp_adapter.notifyDataSetChanged();
						
						sp_pButton.PlayMusic(url);
						//Log.d("mytag", "set orange");
					}
					else if(mItems.get(getPosition).isSelectable()==true){//orange
						mItems.get(getPosition).setSelectable(false);
						sp_adapter.notifyDataSetChanged();
						
						//Log.d("mytag","setyellow");
						sp_pButton.StopMusic();
					}
					sp_staticIti = mItems.get(getPosition);
				}
				}
			});
        	holder.test_btn.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				isTest = true;
    				int getPosition = (Integer)v.getTag();	
    				try{
						mItems.get(getPrevPosition).setSelectable(false);
						sp_adapter.notifyDataSetChanged();
						
						//Log.d("mytag", "line172");
						sp_pButton.StopMusic();
    				}catch(Exception e){}
    				
    				Intent test_intent = new Intent(v.getContext(),TestActivity.class);
    				
    				test_intent.putExtra("icon", mItems.get(getPosition).getIcon());
    				test_intent.putExtra("title", mItems.get(getPosition).getData(0));
    				test_intent.putExtra("artist", mItems.get(getPosition).getData(1));
    				test_intent.putExtra("url", mItems.get(getPosition).getData(2));
    				test_intent.putExtra("lyrics",mItems.get(getPosition).getData(3));
    				
    				v.getContext().startActivity(test_intent);
    				
    				
    			}
    		});  
        
        	
        	cv.setTag(holder);
        	cv.setTag(R.id.play,holder.play_btn);
        	cv.setTag(R.id.test,holder.test_btn);
        }else{
        	holder = (ViewHolder)cv.getTag();
        }
        
        if(mItems.get(position) != null){
        	holder.play_btn.setTag(position);
        	holder.test_btn.setTag(position);
        	holder.tv_title.setText(mItems.get(position).getData(0));
        	holder.tv_artist.setText(mItems.get(position).getData(1));
        	//mItems.get(position).setSelectable(mItems.get(position).isSelectable());
        	//////////////////////////////////////////////////////////////////////
        	holder.play_btn.setSelected(mItems.get(position).isSelectable());
        	/////////////////////////////////////////////////////////////////////
        	//System.out.println("heelo");
        	//Log.d("mytag", String.valueOf(mItems.get(position).isSelectable()));
        	
        	Bitmap bm = openPhoto(mItems.get(position).getIcon());
        	if(bm != null){
        		holder.iv_album.setImageBitmap(bm);        		
        	}
        	
        }
        
        return cv; 
        
    } 
    private Bitmap openPhoto(byte[] icon){
    	Bitmap bitmap = null;
    	if(icon != null){
    		if(icon.length > 3)
    		{    			
    			BitmapFactory.Options option = new BitmapFactory.Options();
    			option.inSampleSize=2;
    		    option.inPurgeable=true;
    		    option.inDither=true;
    		    bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length,option);
    		    }
    	}
    	return bitmap;
    }
    
} 
