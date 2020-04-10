package in.irotech.sqlitestudentdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME="Student.db";
    private final static String TABLE_NAME="student_table";
    private final static String COL_1="Id";
    private final static String COL_2="Name";
    private final static String COL_3="Email";
    private final static String COL_4="CourseCount";


    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Email TEXT,CourseCount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String name,String email,String cc){
        SQLiteDatabase sql=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,cc);
        long result=sql.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean update(String id,String name,String email,String cc){
        SQLiteDatabase sql=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,cc);
        long result=sql.update(TABLE_NAME,contentValues,"Id=?",new String[]{id});
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getValue(String id){
        SQLiteDatabase sql=this.getReadableDatabase();
        Cursor cursor=sql.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Id='"+id+"'",null);
        return cursor;
    }

    public Cursor getComplete(){
        SQLiteDatabase sql=this.getReadableDatabase();
        Cursor cursor=sql.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public Integer delete(String id){
        SQLiteDatabase sql=this.getWritableDatabase();
        return sql.delete(TABLE_NAME,"Id=?",new String[]{id});
    }
}
