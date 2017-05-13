package pl.edu.amu.wmi.oblicze.konferika.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private String DB_PATH;
    private static final int DATABASE_VERSION = 1;

    private static String DB_NAME = "ref.db";
    public SQLiteDatabase myDataBase;


    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = this.getWritableDatabase().getPath();
        this.mycontext = context;
        //boolean dbexist = checkdatabase();
        //if (dbexist) {
        if(isTableExists("Break")){
            opendatabase();
//            Log.v("DB: ", "Break exist");
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        //boolean dbexist = checkdatabase();
      //  if (!dbexist) {
        if(!isTableExists("Break")){
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            //DB_PATH = this.getWritableDatabase().getPath();
            String myPath = DB_PATH;// + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH; //+ DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH;//+ DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
       // myDataBase = SQLiteDatabase.openOrCreateDatabase(mypath, null, null);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void dropAllTables(){
        // query to obtain the names of all tables in your database
        Cursor c = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

// iterate over the result set, adding every table name to a list
        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

// call DROP TABLE on every table name
        for (String table : tables) {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            myDataBase.execSQL(dropQuery);
        }
    }

    public boolean isTableExists(String tableName) {
        /*if(openDb) {
            if(myDataBase == null || !myDataBase.isOpen()) {
                myDataBase = getReadableDatabase();
            }

            if(!myDataBase.isReadOnly()) {
                myDataBase.close();
                myDataBase = getReadableDatabase();
            }
        }
*/
        Cursor cursor = getWritableDatabase().rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}