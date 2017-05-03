package pl.edu.amu.wmi.oblicze.konferika.data;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by edyta on 27.12.16.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper {

//    private static final String DATABASE_NAME = "ref.db";
private static final String DATABASE_NAME = "pustaBaza.db";

    private static final int DATABASE_VERSION = 2;

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
