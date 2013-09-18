package com.cyenterprise.YahooStockChartConfig;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;







import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.cyenterprise.StockChartConfigV6.R;

public class ConstantsBrowser extends ListActivity {
	private static final int ADD_ID = Menu.FIRST+1;
	private static final int DELETE_ID = Menu.FIRST+3;
	private static final int SET_CHARTP = Menu.FIRST+4;
	private static final int SET_CHARTP5 = Menu.FIRST+5;
	private static final int SET_CHARTP6 = Menu.FIRST+6;
	private static final int SET_CHARTP8 = Menu.FIRST+7;
	private static final int SET_CHARTP3m = Menu.FIRST+8; 


	private DatabaseHelper db=null;
	private Cursor constantsCursor=null;
	String[] presidents ;

	private SQLiteDatabase sdb;

	String[] stocks2 ;


	ArrayList<String> stringList = new ArrayList<String>();         
	//private ArrayList<String> words=null;
	ArrayList<String> stringList2 = new ArrayList<String>();   




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		//getListView().addHeaderView(buildHeader());


		db=new DatabaseHelper(this);

		constantsCursor=db
				.getReadableDatabase()
				.rawQuery("SELECT _ID, title, chartperiod, value  "+
						"FROM constants ORDER BY title",
						null);

		/*    ListAdapter adapter=new SimpleCursorAdapter(this,
                          R.layout.row, constantsCursor,
                          new String[] {DatabaseHelper.TITLE,
                                        DatabaseHelper.VALUE},
                          new int[] {R.id.title, R.id.value});*/

		ListAdapter adapter=new SimpleCursorAdapter(this,
				R.layout.row, constantsCursor,
				new String[] {DatabaseHelper.TITLE,
				"chartperiod",
				DatabaseHelper.VALUE
		},
		//new int[] {R.id.title, R.id.chartP, R.id.value});    
		new int[] {R.id.title});    

		//    setListAdapter(adapter);

		//registerForContextMenu(getListView());

		InputStream is = this.getResources().openRawResource(R.raw.textfile);

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = null; 
		try {
			while ((str = br.readLine()) != null) {


				//String delims = "[ ]+";
				String delims = ",";

				String[] tokens = str.split(delims);
				String beforeFirstDot2;
				String beforeFirstDot = tokens[0];    // Text before the first dot
				int l =tokens.length;

				if (l == 2) {               	
					beforeFirstDot2 = tokens[1] ;    // Text before the first dot
					beforeFirstDot2 = beforeFirstDot2.replaceAll("\"", "");

					stringList2.add(  beforeFirstDot + " , " +  beforeFirstDot2);

				}
				if (l == 3) {



					beforeFirstDot2 = tokens[1] + tokens[2];    // Text before the first dot
					beforeFirstDot2 = beforeFirstDot2.replaceAll("\"", "");

					//stringList2.add( beforeFirstDot + "  ,  " +  beforeFirstDot2);
					//stringList2.add( beforeFirstDot2 + "   " +  beforeFirstDot);
					//stringList2.add( "\"" + beforeFirstDot2 + "\"");
					//stringList2.add(  beforeFirstDot2 );

					stringList2.add(  beforeFirstDot + " , " +  beforeFirstDot2);

					//stringList2.add( str);
				}





				/*                Toast.makeText(getBaseContext(), 
            		beforeFirstDot, Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), 
            		beforeFirstDot2, Toast.LENGTH_SHORT).show();*/

				/*                Toast.makeText(getBaseContext(), 
            		beforeFirstDot3, Toast.LENGTH_SHORT).show();
				 */
			}
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}       

		String[] itemArray = new String[stringList2.size()];
		stocks2 = stringList2.toArray(itemArray);   

		getListView().addHeaderView(buildHeaderEnterStock());

		getListView().addHeaderView(buildHeaderAutoCompleteTextView());
		getListView().addHeaderView(buildHeaderSaveStock());


		getListView().addHeaderView(buildHeader());     
		setListAdapter(adapter);
		registerForContextMenu(getListView());

	}

	private View buildHeader() {

		TextView txt=new TextView(this);
		txt.setText("Portfolio:\n");
		//@android:color/green

		//textView1.setTextColor(getResources().getColor(R.color.mycolor))

		//txt.setBackgroundColor(getResources().getColor(R.color.mycolor1)) ;
		txt.setBackgroundColor(Color.DKGRAY) ;
		//txt.setBackgroundColor(Color.GREEN) ;

		//textview1.setTextColor(Color.CYAN);
		//updateFooter(txt);

		return(txt);

		/*	    Button btn=new Button(this);

	    btn.setText("Randomize!");
	    btn.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View v) {
	        List<String> list=Arrays.asList(items);

	        Collections.shuffle(list);

	        setListAdapter(new ArrayAdapter<String>(HeaderFooterDemo.this,
	                            android.R.layout.simple_list_item_1,
	                            list));
	      }
	    }
	    );

	    return(btn);*/
	}

	private View buildHeaderEnterStock() {

		TextView txt=new TextView(this);
		txt.setText("Enter Stock Symbol:\n");
		//@android:color/green

		//textView1.setTextColor(getResources().getColor(R.color.mycolor))

		//txt.setBackgroundColor(getResources().getColor(R.color.mycolor1)) ;
		txt.setBackgroundColor(Color.DKGRAY) ;
		//txt.setBackgroundColor(Color.GREEN) ;

		//textview1.setTextColor(Color.CYAN);
		//updateFooter(txt);

		return(txt);

		/*	    Button btn=new Button(this);

	    btn.setText("Randomize!");
	    btn.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View v) {
	        List<String> list=Arrays.asList(items);

	        Collections.shuffle(list);

	        setListAdapter(new ArrayAdapter<String>(HeaderFooterDemo.this,
	                            android.R.layout.simple_list_item_1,
	                            list));
	      }
	    }
	    );

	    return(btn);*/
	}
	private View buildHeaderSaveStock() {

		/*	    TextView txt=new TextView(this);
	    txt.setText("Enter Stock Symbol:\n");
	    //@android:color/green

	    //textView1.setTextColor(getResources().getColor(R.color.mycolor))

	    //txt.setBackgroundColor(getResources().getColor(R.color.mycolor1)) ;
	    txt.setBackgroundColor(Color.DKGRAY) ;
	    //txt.setBackgroundColor(Color.GREEN) ;

	    //textview1.setTextColor(Color.CYAN);
	    //updateFooter(txt);

	    return(txt);*/

		Button btn=new Button(this);

		btn.setText("Save Stock Symbol");
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ListView lstView = getListView();

				String stockStr = textViewAuto.getText().toString()  ;  

				//String delims = "[ ]+";
				String delims = ",";

				String[] tokens = stockStr.split(delims);
				String stockSymStr = tokens[0];    // Text before the first dot

				/*	            Toast.makeText(this,
	                    "You have entered stockStr " + stockSymStr,
	                    Toast.LENGTH_SHORT).show();
				 */
				/*    	String itemsSelected = "Selected items: \n";
	        	for (int i=0; i<lstView.getCount(); i++) {
	        		if (lstView.isItemChecked(i)) {
	        			itemsSelected += lstView.getItemAtPosition(i) + "\n";
	        		}
	        	}
	        	Toast.makeText(this, itemsSelected, Toast.LENGTH_LONG).show();*/


				//	            ListView lstView = getListView();

				//lstView.setChoiceMode(ListView.CHOICE_MODE_NONE) ; 
				//lstView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
				lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);        
				lstView.setTextFilterEnabled(true);

				/*        presidents =
	                    getResources().getStringArray(R.array.presidents_array);*/




				//ArrayList<String> stringList = new ArrayList<String>();         
				/*        stringList.add("IBM4");
	            stringList.add("TSLA");*/

				/*	            String upper = stockSymStr.toUpperCase();
	            if (stringList.contains(upper) == false )   {     
	                stringList.add(upper);

	                db.open();
	                long id = db.insertContact(upper, "weimenglee@learn2develop.net");
	                db.close();                

	            }*/

				ContentValues values=new ContentValues(2);
				//	            String stockStr = wrapper.getTitle();
				String upper = stockSymStr.toUpperCase();

				if (!getStock(upper ) ) {

					values.put(DatabaseHelper.TITLE, upper);
					//values.put(DatabaseHelper.VALUE, wrapper.getValue());
					//db.getWritableDatabase().delete("constants", "1", null);


					db.getWritableDatabase().insert("constants", DatabaseHelper.TITLE, values);
					constantsCursor.requery();
				}	            




				int size = stringList.size();


				//String[] presidents = new String[size];

				/*       for (int i = 0; i < stringList.size(); i++){
	            	   String item = stringList.get(i);
	            	   presidents[i] = item;
	            }*/

				String[] itemArray = new String[stringList.size()];
				presidents = stringList.toArray(itemArray);

				//       ArrayList stringList2 = (ArrayList) Arrays.asList(new String[]{"One", "Two", "Three"});

				/*	            setListAdapter(new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_checked, presidents));     */  

				//String stockStr = textViewAuto.getText().toString()  ;  

				textViewAuto.setText("");  	 
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(textViewAuto.getWindowToken(), 0); 

				/*	        List<String> list=Arrays.asList(items);

	        Collections.shuffle(list);

	        setListAdapter(new ArrayAdapter<String>(HeaderFooterDemo.this,
	                            android.R.layout.simple_list_item_1,
	                            list));*/
			}
		}
				);

		return(btn);
	}

	AutoCompleteTextView textViewAuto;
	private View buildHeaderAutoCompleteTextView() {

		TextView txt=new TextView(this);
		txt.setText("Portfolio2:\n");
		//@android:color/green

		//textView1.setTextColor(getResources().getColor(R.color.mycolor))

		//txt.setBackgroundColor(getResources().getColor(R.color.mycolor1)) ;
		txt.setBackgroundColor(Color.DKGRAY) ;
		//txt.setBackgroundColor(Color.GREEN) ;

		//textview1.setTextColor(Color.CYAN);
		//updateFooter(txt);


		ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, stocks2);

		/*	        AutoCompleteTextView textView = (AutoCompleteTextView)
	            findViewById(R.id.txtCountries);*/

		textViewAuto = new AutoCompleteTextView(this);

		textViewAuto.setThreshold(1);
		textViewAuto.setAdapter(adapterAuto);   	        

		return(textViewAuto);




		/*	    Button btn=new Button(this);

	    btn.setText("Randomize!");
	    btn.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View v) {
	        List<String> list=Arrays.asList(items);

	        Collections.shuffle(list);

	        setListAdapter(new ArrayAdapter<String>(HeaderFooterDemo.this,
	                            android.R.layout.simple_list_item_1,
	                            list));
	      }
	    }
	    );

	    return(btn);*/
	}



	public void onListItemClick(
			ListView parent, View v, int position, long id)
	{
		Toast.makeText(this,
				//"onListItemClick-----You have selected position=" + presidents[position],
				"onListItemClick-----You have selected position=" + position,

				Toast.LENGTH_SHORT).show();
		int i = (int) id;
		dis(i);

		/*		        String yahooStr ="http://finance.yahoo.com/q/ta?s=";

		        String stockStr = presidents[position];
		        String chartPeriodStr ="5d";

		        String preferencesStr ="&t=";
		        String preferences2Str ="&l=on&z=l&q=c&p=m10%2Cm50%2Cv%2Cm200&a=m26-12-9%2Cr14&c=";


		        Intent i = new
		                Intent("android.intent.action.VIEW");
		        i.setData(Uri.parse(yahooStr + stockStr + preferencesStr + chartPeriodStr + preferences2Str));		


		       //i.setData(Uri.parse("http://www.marketwatch.com/investing/stock/fb/charts?ma=4&maval=50&uf=7168&lf=1&lf2=4&lf3=2&type=4&size=3&state=8&sid=0&style=1013&time=3&freq=7&startdate=1%2F4%2F1999&enddate=3%2F9%2F2012&height=820&widht=720&random=1907449066&arrowdates=0"));		
				startActivity(i);  */
	}

	private void dis(int j) {
		sdb = db.getWritableDatabase();
		//		sdb.open();
		Cursor c = getStock(j);
		if (c.moveToFirst())
			DisplayStock(c);
		else
			Toast.makeText(this, "No title found", Toast.LENGTH_LONG).show();
		//		sdb.close();
	}

	public void DisplayStock(Cursor c) {
		//System.out.println("bool");
		String Stock= c.getString(1);
		String chartPeriodStr =c.getString(2);

		if (chartPeriodStr == null) {
			chartPeriodStr ="5d";

		}

		Toast.makeText(
				this,
				"id: " + c.getString(0) + "\n" + "Stock: " + Stock
				+ "\n" + "ChartP: " + chartPeriodStr + "\n"
				, Toast.LENGTH_LONG)
				.show();

		String yahooStr ="http://finance.yahoo.com/q/ta?s=";

		String stockStr = Stock;

		String preferencesStr ="&t=";
		String preferences2Str ="&l=on&z=l&q=c&p=m10%2Cm50%2Cv%2Cm200&a=m26-12-9%2Cr14&c=";


		Intent i = new
				Intent("android.intent.action.VIEW");
		i.setData(Uri.parse(yahooStr + stockStr + preferencesStr + chartPeriodStr + preferences2Str));		


		//i.setData(Uri.parse("http://www.marketwatch.com/investing/stock/fb/charts?ma=4&maval=50&uf=7168&lf=1&lf2=4&lf3=2&type=4&size=3&state=8&sid=0&style=1013&time=3&freq=7&startdate=1%2F4%2F1999&enddate=3%2F9%2F2012&height=820&widht=720&random=1907449066&arrowdates=0"));		
		startActivity(i);  



	}

	static final String TITLE="title";
	static final String VALUE="value";
	public static final String KEY_ROWID = "_id";

	// ---retrieves a particular Stock---
	// Cursor android.database.sqlite.SQLiteDatabase.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)



	public Cursor getStock(long rowId) throws SQLException {
		String[] args={String.valueOf(rowId)};


		/*		db.getReadableDatabase().
		Cursor mCursor = db.query(true, "constants", new String[] {
				KEY_ROWID, TITLE, VALUE }, "_ID"
				+ "=" + rowId, null, null, null, null, null);*/
		//"_ID=?", args
		Cursor mCursor=db.getReadableDatabase()
				.rawQuery("SELECT _ID, title, chartperiod, value  "+
						"FROM constants WHERE  _ID=? ", args
						);


		/*		 constantsCursor=db
                 .getReadableDatabase()
                 .rawQuery("SELECT _ID, title, chartperiod, value  "+
                           "FROM constants ORDER BY title",
                           null);*/


		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public  Boolean getStock(String stock) throws SQLException {
		String[] args={String.valueOf(stock)};


		/*		db.getReadableDatabase().
		Cursor mCursor = db.query(true, "constants", new String[] {
				KEY_ROWID, TITLE, VALUE }, "_ID"
				+ "=" + rowId, null, null, null, null, null);*/
		//"_ID=?", args
		/*		Cursor mCursor=db.getReadableDatabase()
                .rawQuery("SELECT _ID, title, chartperiod, value  "+
                          "FROM constants WHERE  title=? ", args
                          );*/


		Cursor mCursor=db
				.getReadableDatabase()
				.rawQuery("SELECT _ID, title, chartperiod, value  "+
						"FROM constants ORDER BY title",
						null);

		if (mCursor != null) {
			mCursor.moveToFirst();

		}
		ArrayList<String> stringList = new ArrayList<String>();         

		try {
			Cursor c = mCursor;
			if (c.moveToFirst()) {
				do {
					String Stock= c.getString(1);

					stringList.add(Stock);	

					Toast.makeText(this,
							"getStock-=" + Stock,

							Toast.LENGTH_SHORT).show();				

				} while (c.moveToNext());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		String upper = stock.toUpperCase();
		if (stringList.contains(upper) == true )   {  
			return true;
		}

		return false ;
	}



	@Override
	public void onDestroy() {
		super.onDestroy();

		constantsCursor.close();
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "Add Stock")
		//        .setIcon(R.drawable.add)
		.setAlphabeticShortcut('a');

		return(super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ADD_ID:
			add();
			return(true);
		}

		return(super.onOptionsItemSelected(item));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_ID, 7, "Delete Stock Symbol")
		.setAlphabeticShortcut('d');
		menu.add(Menu.NONE, SET_CHARTP, 1, "Set Chart Period to 1 Day")
		.setAlphabeticShortcut('e');
		menu.add(Menu.NONE, SET_CHARTP5, 2, "Set Chart Period to 5 Days")
		.setAlphabeticShortcut('f');    
		menu.add(Menu.NONE, SET_CHARTP6, 4, "Set Chart Period to 6 Months")
		.setAlphabeticShortcut('g');    

		menu.add(Menu.NONE, SET_CHARTP8, 5, "Set Chart Period to 1 Year")
		.setAlphabeticShortcut('h'); 

		menu.add(Menu.NONE, SET_CHARTP3m, 3, "Set Chart Period to 3 Month")
		.setAlphabeticShortcut('h'); 

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info=
				(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();	  

		switch (item.getItemId()) {


		case DELETE_ID:


			delete(info.id);
			return(true);

		case SET_CHARTP:
			setChartPeriod(info.id);
			return(true);  
		case SET_CHARTP5:
			setChartPeriod5(info.id);
			return(true);                
		case SET_CHARTP6:
			setChartPeriod6(info.id);
			return(true);    
		case SET_CHARTP8:
			setChartPeriod8(info.id);
			return(true);   
		case SET_CHARTP3m:
			setChartPeriod3m(info.id);
			return(true);               


		}

		return(super.onOptionsItemSelected(item));
	}

	private void add() {
		LayoutInflater inflater=LayoutInflater.from(this);
		View addView=inflater.inflate(R.layout.add_edit, null);
		final DialogWrapper wrapper=new DialogWrapper(addView);

		new AlertDialog.Builder(this)
		.setTitle(R.string.add_title)
		.setView(addView)
		.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int whichButton) {
				processAdd(wrapper);
			}
		})
		.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int whichButton) {
				// ignore, just dismiss
			}
		})
		.show();
	}

/*	private void delete(final long rowId) {
		if (rowId>0) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.delete_title)
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processDelete(rowId);
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}
*/
	
	private void delete(final long rowId) {
		String stocksymbol = null;;  
		String delete_title = null;

		if (rowId>0) {

			sdb = db.getWritableDatabase();
			Cursor c = getStock(rowId);

			if (c.moveToFirst()) {
				stocksymbol= c.getString(1);
				delete_title = "Delete stock symbol: " + stocksymbol  + "\n Are You Sure? ";
			}

			else
				Toast.makeText(this, "delete>> No Stock found", Toast.LENGTH_LONG).show();



			//String stocksymbol = "Z";


			new AlertDialog.Builder(this)
			//        .setTitle(R.string.delete_title)
			.setTitle(delete_title)

			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processDelete(rowId);
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}



	private void setChartPeriod(final long rowId) {
		if (rowId>0) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.set_ChartPeriod)
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processSetChartPeriod(rowId, "1d");
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}

	private void setChartPeriod5(final long rowId) {
		if (rowId>0) {

			String mystring = getResources().getString(R.string.set_ChartPeriod5);


			new AlertDialog.Builder(this)

			.setTitle(R.string.set_ChartPeriod5 )
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processSetChartPeriod(rowId, "5d");
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}

	private void setChartPeriod6(final long rowId) {
		if (rowId>0) {

			//String mystring = getResources().getString(R.string.set_ChartPeriod5);


			new AlertDialog.Builder(this)

			.setTitle(R.string.set_ChartPeriod6 )
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processSetChartPeriod(rowId, "6m");
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}

	private void setChartPeriod8(final long rowId) {
		if (rowId>0) {

			//String mystring = getResources().getString(R.string.set_ChartPeriod5);


			new AlertDialog.Builder(this)

			.setTitle(R.string.set_ChartPeriod8 )
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processSetChartPeriod(rowId, "1y");
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}

	private void setChartPeriod3m(final long rowId) {
		if (rowId>0) {

			//String mystring = getResources().getString(R.string.set_ChartPeriod5);


			new AlertDialog.Builder(this)

			.setTitle(R.string.set_ChartPeriod3m )
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					processSetChartPeriod(rowId, "3m");
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
		}
	}

/*	private void processAdd(DialogWrapper wrapper) {


		ContentValues values=new ContentValues(2);
		String stockStr = wrapper.getTitle();
		String upper = stockStr.toUpperCase();

		values.put(DatabaseHelper.TITLE, upper);
		//values.put(DatabaseHelper.VALUE, wrapper.getValue());
		//db.getWritableDatabase().delete("constants", "1", null);


		db.getWritableDatabase().insert("constants", DatabaseHelper.TITLE, values);
		constantsCursor.requery();
	}
*/
	@SuppressLint("DefaultLocale")
	@SuppressWarnings("deprecation")
	private void processAdd(DialogWrapper wrapper) {


		ContentValues values=new ContentValues(2);
		String stockStr = wrapper.getTitle();
		String upper = stockStr.toUpperCase();

		if (!getStock(upper ) ) {

			values.put(DatabaseHelper.TITLE, upper);
			//values.put(DatabaseHelper.VALUE, wrapper.getValue());
			//db.getWritableDatabase().delete("constants", "1", null);


			db.getWritableDatabase().insert("constants", DatabaseHelper.TITLE, values);
			constantsCursor.requery();
		}
	}
	private void processSetChartPeriod(long rowId, String chartPeriod) {
		//Toast.makeText(this, "update =" + update, Toast.LENGTH_LONG).show();   
		Toast.makeText(this, "processSetChartPeriod update =" , Toast.LENGTH_LONG).show();   
		String[] args={String.valueOf(rowId)};



		ContentValues values=new ContentValues(2);

		// values.put(DatabaseHelper.TITLE, "ibm");
		values.put(DatabaseHelper.VALUE, "55");
		values.put("chartperiod", chartPeriod);

		ContentValues cv=new ContentValues();





		int update = db.getWritableDatabase().update("constants", values, "_ID=?", args);
		Toast.makeText(this, "processSetChartPeriod update  =" + update, Toast.LENGTH_LONG).show();   
		Toast.makeText(this, "rowId =" + rowId, Toast.LENGTH_LONG).show();   

		/*    db.getWritableDatabase().delete("constants", "_ID=?", args);
	    constantsCursor.requery();*/





	}

	private void processSetChartPeriod5(long rowId) {
		//Toast.makeText(this, "update =" + update, Toast.LENGTH_LONG).show();   
		Toast.makeText(this, "processSetChartPeriod update =" , Toast.LENGTH_LONG).show();   

	}


	private void processDelete(long rowId) {
		String[] args={String.valueOf(rowId)};
		db.getWritableDatabase().delete("constants", "_ID=?", args);
		constantsCursor.requery();   

		/*    //String[] args={String.valueOf("ibm")};
    //String[] args={"ibm"};


   ContentValues values=new ContentValues(2);

   // values.put(DatabaseHelper.TITLE, "ibm");
    values.put(DatabaseHelper.VALUE, "107");
    values.put("chartperiod", "5d");

    ContentValues cv=new ContentValues();

    //db.getWritableDatabase().delete("constants", "_ID=?", args);
    //db.getWritableDatabase().delete("constants", null, args);


    int update = db.getWritableDatabase().update("constants", values, "title=?", args);
    Toast.makeText(this, "update =" + update, Toast.LENGTH_LONG).show();   
    constantsCursor.requery();

    String[] args2={"ibm"};
    values.put("chartperiod", "1d");
    //values.put(DatabaseHelper.VALUE, "1d");

     update = db.getWritableDatabase().update("constants", values, "title=?", args2);
    Toast.makeText(this, "update =" + update, Toast.LENGTH_LONG).show();   
    constantsCursor.requery();

    int update = db.getWritableDatabase().update("constants", values, "_ID=?", args);
   Toast.makeText(this, "update =" + update, Toast.LENGTH_LONG).show();   
   Toast.makeText(this, "rowId =" + rowId, Toast.LENGTH_LONG).show();   */





	}

	class DialogWrapper {
		EditText titleField=null;
		EditText valueField=null;
		View base=null;

		DialogWrapper(View base) {
			this.base=base;
			valueField=(EditText)base.findViewById(R.id.title);
		}

		String getTitle() {
			return(getTitleField().getText().toString());
		}

		/*    float getValue() {
      return(new Float(getValueField().getText().toString())
                                                  .floatValue());
    }*/

		private EditText getTitleField() {
			if (titleField==null) {
				titleField=(EditText)base.findViewById(R.id.title);
			}

			return(titleField);
		}

		/* private EditText getValueField() {
      if (valueField==null) {
        valueField=(EditText)base.findViewById(R.id.value);
      }

      return(valueField);
    }*/
	}
}
