package com.example.puree2013;

import android.content.Context; 
import android.graphics.*; 
import android.view.LayoutInflater; 
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.TextView; 
import android.widget.Button;

public class MyPageView extends LinearLayout {
	
	Context context_c;
    private ImageView mIcon; 
    private TextView mText01;  
    private TextView mText02; 
    IconTextItem m_item;
    String m_url;
    static LayoutInflater inflater;


   
    public MyPageView(Context context, IconTextItem aItem) { 
        super(context); 
          
        m_item = aItem;
        context_c=context;
       // bmp=BitmapFactory.decodeByteArray(aItem.getIcon(), 0, aItem.getIcon().length);
        
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.mp_listrow, this, true); 
     
          
        mIcon = (ImageView) findViewById(R.id.mp_iconItem); 
        mIcon.setImageBitmap(BitmapFactory.decodeByteArray(aItem.getIcon(), 0, aItem.getIcon().length));
       // mIcon.setImageDrawable(aItem.getIcon()); 
        
        mText01 = (TextView) findViewById(R.id.mp_dataItem01); 
        mText01.setText(aItem.getData(0)); 
        mText01.setPaintFlags(mText01.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
          
        mText02 = (TextView) findViewById(R.id.mp_dataItem02); 
        mText02.setText(aItem.getData(1)); 

        m_url = aItem.getData(2);
        
       
       // pButton = new PlayButton();
        /*
        play_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//pButton.PlayMusic("http://narae427.maru.net/Á¦¹ß.mp3");
				//Intent intent = new Intent(IconTextView.this, TestActivity.class);
				pButton.PlayMusic(m_url);

				
			}
		});	
		*/
          
    } 
  
    public void setText(int index, String data) { 
        if (index == 0) { 
            mText01.setText(data); 
        } else if (index == 1) { 
            mText02.setText(data); 
        } else if (index ==2) {
        	m_url = data;        	
        } else { 
            throw new IllegalArgumentException(); 
        } 
    } 

    public void setIcon(byte[] icon) { 
        //mIcon.setImageDrawable(icon); 0
    	if(icon != null){
    		if(icon.length > 3)
    		{
    			mIcon.setImageBitmap(BitmapFactory.decodeByteArray(icon, 0, icon.length));
    		}
    		else
    		{
    			mIcon.setImageResource(R.drawable.icon);
    		}
    	}
    	//mIcon.setImageBitmap(BitmapFactory.decodeByteArray(icon, 0, icon.length));
    } 
  
} 

