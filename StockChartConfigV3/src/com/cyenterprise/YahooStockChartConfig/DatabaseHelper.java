package com.cyenterprise.YahooStockChartConfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.SensorManager;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static int    DB_VERSION = 3;
	
  private static final String DATABASE_NAME="db2";
  static final String TITLE="title";
  static final String VALUE="value";
  
  /*public TracksDB(Context context) {
      super(context, DATABASE_NAME, null, DB_VERSION);
  }  */
  
  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DB_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    //db.execSQL("CREATE TABLE constants (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value REAL);");
    db.execSQL("CREATE TABLE constants (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,  chartperiod TEXT, value REAL);");
   
   ContentValues cv=new ContentValues();
   //cv.put(VALUE, SensorManager.GRAVITY_DEATH_STAR_I);

   
  
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    android.util.Log.w("Constants", "Upgrading database, which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS constants");
    onCreate(db);
    
    
  /*  android.util.Log.w("Constants> onUpgrade", "Upgrading database, from oldVersion=" + oldVersion + "  newVersion=" + newVersion);
    if (oldVersion < 2) {
        final String ALTER_TBL = 
            "ALTER TABLE constants"  +
            " ADD COLUMN chartperiod TEXT;";
        
        //db.execSQL("CREATE TABLE constants (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,  chartperiod TEXT, value REAL);");
        
        db.execSQL(ALTER_TBL);
    
    }*/
  }
}

