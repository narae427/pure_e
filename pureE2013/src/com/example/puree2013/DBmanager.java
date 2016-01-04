package com.example.puree2013;


import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase;

  
  
public class DBmanager extends SQLiteOpenHelper { 
      
	//public static SQLiteDatabase    dbase; 
    public static String musicDatabaseFile = "/mnt/sdcard/MyProject/Test4.db"; 
  
    public DBmanager (Context context) { 
    	    	
    	super(context,musicDatabaseFile,null,1);
    	         
    } 
   
      
   @Override
    public void onCreate(SQLiteDatabase db) { 
          
        
    } 
   
   @Override
   public void onUpgrade(SQLiteDatabase db, int oleVersion, int newVersion) {
	 
	   
	   
   }
  
      
} 
