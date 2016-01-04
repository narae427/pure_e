package com.example.puree2013;


import android.util.Log;
 
public class IconTextItem { 
  
   
	//private byte[] mImg;
    private byte[] mIcon;    
    private String[] mData; 
    private int mScore;
    private boolean mSelectable = false; 
    public IconTextItem(byte[] icon, String[] obj, int score) { 
        mIcon = icon; 
        mData = obj; 
        mScore = score;       
    } 
  
  
   // public IconTextItem(Drawable icon, String obj01, String obj02) { 
    public IconTextItem(byte[] icon, String obj01, String obj02, String obj03, String obj04, int score) {     
    	mIcon = icon;    
    	//mImg = img;   
        mData = new String[4]; 
        mData[0] = obj01; //title
        mData[1] = obj02; //artist
        mData[2] = obj03; //url
        mData[3] = obj04; //lyrics

        mScore = score; //score
       // PlayButton pButton = new PlayButton();
        

       
    } 
   
    public boolean isSelectable() { 
        return mSelectable; 
    } 
  
    
    public void setSelectable(boolean selectable) { 
        mSelectable = selectable; 
       // Log.d("mytag", "setSelectable");
       // adapter.notifyDataSetChanged();
        
    } 
  
   
    public String[] getData() { 
        return mData; 
    } 
  
    
    public String getData(int index) { 
        if (mData == null || index >= mData.length) { 
            return null; 
        } 
          
        return mData[index]; 
    } 
      
   
    public void setData(String[] obj) { 
        mData = obj; 
    } 
    
    public void setScore(int score){
    	mScore = score;
    }
    
    public int getScore(){
    	return mScore;
    }
  
   
    public void setIcon(byte[] icon) { 
        mIcon = icon; 
    } 
  
   
    public byte[] getIcon() { 
        return mIcon; 
    } 
  
    public int compareTo(IconTextItem other) { 
        if (mData != null) { 
            String[] otherData = other.getData(); 
            if (mData.length == otherData.length) { 
                for (int i = 0; i < mData.length; i++) { 
                    if (!mData[i].equals(otherData[i])) { 
                        return -1; 
                    } 
                } 
            } else { 
                return -1; 
            } 
        } else { 
            throw new IllegalArgumentException(); 
        } 
          
        return 0; 
    } 
  
} 
