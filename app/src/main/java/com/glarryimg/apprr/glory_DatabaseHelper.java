package com.glarryimg.apprr;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public final class glory_DatabaseHelper extends SQLiteOpenHelper {

	private static String saleh_DB_DIR = "/data/data/android.saleh/databases/";
	private static String saleh_DB_NAME = "galary.sqlite";
	private static String DB_PATH = saleh_DB_DIR + saleh_DB_NAME;
	private static String OLD_DB_PATH = saleh_DB_DIR + "old_" + saleh_DB_NAME;

	private boolean createDatabase = false;
	private boolean upgradeDatabase = false;

 	public SQLiteDatabase saleh_db =null;
	public boolean load_main_activity=true;
	public  static   int saleh_DATA_BASE_VERSION = 18;
	private Context myContext;
	public String
			saleh_tbl_setting ="tbl_fav";



	Cursor cursor;

	final String CREATE_C_TABLE_setting = "CREATE TABLE "+ saleh_tbl_setting +" ("+"namecell Text,fav Boolean"+");";


	public glory_DatabaseHelper(Context context) {
		super(context, saleh_DB_NAME, null, saleh_DATA_BASE_VERSION);
		DB_PATH = context.getDatabasePath(saleh_DB_NAME).getAbsolutePath();
		myContext=context;


	}





	@Override
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    if (!db.isReadOnly()) {

	    }
	}
	@Override
	public void onCreate(SQLiteDatabase db) {


		db.execSQL(CREATE_C_TABLE_setting);


	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


		
	}



	public boolean remove_data(String querydel)
	{
		 saleh_db =this.getReadableDatabase();
		 saleh_db.execSQL(querydel);
		 saleh_db.close();
	     return true;

	}
	//insert order
	public boolean add_new_data(ContentValues contnt, String table){//when you want update something it will be true
		   //Log.w("insert content of data", contnt.toString());

		   saleh_db =this.getWritableDatabase();

		   ContentValues cvreserv = contnt;
		   saleh_db.insert(table, null, cvreserv);

		    saleh_db.close();

			return true;

	}

	public int get_max_id(String MY_QUERY) {
		int ID=1111111;
		try {
			//Log.w("maxxxxxxxxxx",MY_QUERY);
			//final String MY_QUERY = "SELECT MAX(id) FROM " + table_name;
			saleh_db =this.getReadableDatabase();
			Cursor cur = saleh_db.rawQuery(MY_QUERY, null);
			cur.moveToFirst();
			 ID = cur.getInt(0);
			cur.close();
			saleh_db.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ID;
		}


	public Cursor getPerson(String tbl_name){
		
         saleh_db = this.getReadableDatabase();
        //String sql = "SELECT * FROM order_table WHERE id="+id+";";
        String sql = "SELECT * FROM "+tbl_name+";";
        Cursor c = saleh_db.rawQuery(sql, null);

        return c;
    }

	public String get_json_from_db(String sqlQuery, String jsn_name )
	{
        //Log.w("sqlQuery=", sqlQuery);
		saleh_db = this.getReadableDatabase();
		Cursor cursor = saleh_db.rawQuery(sqlQuery, null);

		JSONArray resultSet     = new JSONArray();
		if(cursor != null)
		{
			cursor.moveToFirst();
			while (cursor.isAfterLast() == false) {

				int totalColumn = cursor.getColumnCount();
				JSONObject rowObject = new JSONObject();

				for( int i=0 ;  i< totalColumn ; i++ )
				{
					if( cursor.getColumnName(i) != null )
					{
						try
						{
							if( cursor.getString(i) != null )
							{
								// Log.d("TAG_NAME", cursor.getString(i) );
								rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
							}
							else
							{
								rowObject.put( cursor.getColumnName(i) ,  "0" );
							}
						}
						catch( Exception e )
						{
							//Log.d(jsn_name, e.getMessage()  );
						}
					}
				}
				resultSet.put(rowObject);

				cursor.moveToNext();
			}}

		else
		{
			resultSet=null;
		}

		//Log.e(jsn_name, resultSet.toString() );
		cursor.close();

		return resultSet.toString();

	}





}