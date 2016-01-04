package com.example.puree2013;

public class AlbumTextItem { 
	  
	   
	//private byte[] mImg;
   // private byte[] mIcon;    
    private String[] aData;    
    private boolean aSelectable = false; 
    public AlbumTextItem(String[] obj) { 
       // mIcon = icon; 
        aData = obj; 
    } 
  
  
   // public IconTextItem(Drawable icon, String obj01, String obj02) { 
    public AlbumTextItem(String obj01, String obj02) {     
    	//mIcon = icon;    
    	//mImg = img;   
        aData = new String[2]; 
        aData[0] = obj01; //title
        aData[1] = obj02; //artist
        //mData[2] = obj03; //url
        //mData[3] = obj04; //lyrics
       // PlayButton pButton = new PlayButton();
        

       
    } 
   
    public boolean isSelectable() { 
        return aSelectable; 
    } 
  
    
    public void setSelectable(boolean selectable) { 
        aSelectable = selectable; 
       // Log.d("mytag", "setSelectable");
       // adapter.notifyDataSetChanged();
        
    } 
  
   
    public String[] getData() { 
        return aData; 
    } 
  
    
    public String getData(int index) { 
        if (aData == null || index >= aData.length) { 
            return null; 
        } 
          
        return aData[index]; 
    } 
      
   
    public void setData(String[] obj) { 
        aData = obj; 
    } 
  
   
   
    public int compareTo(AlbumTextItem other) { 
        if (aData != null) { 
            String[] otherData = other.getData(); 
            if (aData.length == otherData.length) { 
                for (int i = 0; i < aData.length; i++) { 
                    if (!aData[i].equals(otherData[i])) { 
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
