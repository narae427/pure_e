package com.example.puree2013;


import java.util.ArrayList; 
import java.util.List; 
import android.content.Context; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.LayoutInflater;
  
public class MyPageAdapter extends BaseAdapter {
	//Button play_btn, test_btn;
	static TextView pre_btnV;
	String url;
	//static PlayButton pButton;
	private LayoutInflater inflater;
	
	IconTextView itemView;
  
    private Context mContext; 
  
    public static List<IconTextItem> mItems = new ArrayList<IconTextItem>(); 
  
    public MyPageAdapter(Context context) { 
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
  
    public boolean isSelectable(int position) { 
        try { 
            return mItems.get(position).isSelectable(); 
        } catch (IndexOutOfBoundsException ex) { 
            return false; 
        } 
    } 
  
    public long getItemId(int position) { 
        return position; 
    } 
    
    public class ViewHolder {
    	ImageView iv_album;
    	TextView tv_title;
    	TextView tv_artist;
    	String url;
    	RatingBar ratingBar;
    }

	 
    public View getView(int position, View convertView, ViewGroup parent) { 
    	View cv = convertView;
    	final ViewHolder holder;
    	 if (cv == null) { 
    		 holder = new ViewHolder();
    		 
         	cv = inflater.inflate(R.layout.mp_listrow, parent, false); 
          	
         	holder.iv_album = (ImageView)cv.findViewById(R.id.mp_iconItem);
         	holder.tv_title = (TextView)cv.findViewById(R.id.mp_dataItem01);
         	holder.tv_artist = (TextView)cv.findViewById(R.id.mp_dataItem02);
         	holder.ratingBar = (RatingBar)cv.findViewById(R.id.ratingbar);
         	
         	
         	cv.setTag(holder);
         }else{
         	holder = (ViewHolder)cv.getTag();
         }
         
       // IconTextItem icItem = mItems.get(position);
       
         
        // itemView = new IconTextView(mContext, mItems.get(position));
         
         if(mItems.get(position) != null){
         	
         	holder.tv_title.setText(mItems.get(position).getData(0));
         	holder.tv_artist.setText(mItems.get(position).getData(1));
         	holder.ratingBar.setRating(mItems.get(position).getScore());
         	
         	//holder.play_btn.setSelected(mItems.get(position).isSelectable());
         	Bitmap bm = openPhoto(mItems.get(position).getIcon());
         	if(bm != null){
         		holder.iv_album.setImageBitmap(bm);        		
         	}
         }
         /*
         if(icItem != null){
         	holder.tv_title.setText(icItem.getData(0));
         	holder.tv_artist.setText(icItem.getData(1));
         	holder.url = icItem.getData(2);
         	//url1 = holder.url;
         	Bitmap bm = openPhoto(icItem.getIcon());
         	if(bm != null){
         		holder.iv_album.setImageBitmap(bm);         		
         	}
         }
       */
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
    			//mIcon.setImageBitmap(bitmap);
    			//bitmap.recycle();
    			//bitmap=null;
    	
    		}
    		else
    		{
    			//mIcon.setImageResource(R.drawable.icon);
    		}
    	}
    	return bitmap;
    }
   
    
  
} 
