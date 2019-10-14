package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE = "creative.db";

    public DBHandler(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_table_artist = "CREATE TABLE " + ArtistsMaster.Artists.Table_Name_ArtistDetails +
                "(" + ArtistsMaster.Artists._ID + " INTEGER PRIMARY KEY," +
                ArtistsMaster.Artists.Column_ArtistName + " TEXT);";

        sqLiteDatabase.execSQL(create_table_artist);

        String create_table_photo = "CREATE TABLE " + ArtistsMaster.Photographs.Table_Name_PhotographDetails +
                "(" + ArtistsMaster.Photographs._ID + " INTEGER PRIMARY KEY," +
                ArtistsMaster.Photographs.Column_PhotographName + " TEXT," +
                ArtistsMaster.Photographs.Column_ArtistId + " INTEGER REFERENCES " + ArtistsMaster.Artists.Table_Name_ArtistDetails + "("
                + ArtistsMaster.Artists._ID + ")," + ArtistsMaster.Photographs.Column_PhotographCategory + " TEXT," +
                ArtistsMaster.Photographs.Column_Image + " BITMAP);";

        sqLiteDatabase.execSQL(create_table_photo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addPhotos(String photoName, int artistID, String category, byte[] image){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ArtistsMaster.Photographs.Column_PhotographName, photoName);
        contentValues.put(ArtistsMaster.Photographs.Column_ArtistId, artistID);
        contentValues.put(ArtistsMaster.Photographs.Column_PhotographCategory, category);
        contentValues.put(ArtistsMaster.Photographs.Column_Image, image);

        long res = db.insert(ArtistsMaster.Photographs.Table_Name_PhotographDetails, null, contentValues);

        if(res > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addArtist(String artistName){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ArtistsMaster.Artists.Column_ArtistName, artistName);

        long res = db.insert(ArtistsMaster.Artists.Table_Name_ArtistDetails, null, contentValues);

        if(res > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteDetails(String tableName, String columnName, String name){
        SQLiteDatabase db = getReadableDatabase();

        String whereClause = "";
        String whereArgs[] = {name};

        if(columnName.equals(ArtistsMaster.Artists.Table_Name_ArtistDetails)){
            whereClause = ArtistsMaster.Artists.Column_ArtistName + " = ?";
        }else if(columnName.equals(ArtistsMaster.Photographs.Column_PhotographName)){
            whereClause = ArtistsMaster.Photographs.Column_PhotographName + " = ?";
        }

        if(tableName.equals(ArtistsMaster.Artists.Table_Name_ArtistDetails)){
            long res = db.delete(ArtistsMaster.Artists.Table_Name_ArtistDetails, whereClause, whereArgs);

            if(res > 0){
                return true;
            }
            else {
                return false;
            }
        }
        else if(tableName.equals(ArtistsMaster.Photographs.Table_Name_PhotographDetails)){
            long res = db.delete(ArtistsMaster.Photographs.Table_Name_PhotographDetails, whereClause, whereArgs);

            if(res > 0){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public ArrayList<String> loadArtist(){
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {ArtistsMaster.Artists.Column_ArtistName};
        String sortBy = ArtistsMaster.Artists._ID;

        Cursor cursor = db.query(ArtistsMaster.Artists.Table_Name_ArtistDetails, columns, null, null, null, null, sortBy);

        ArrayList<String> names = new ArrayList<>();

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ArtistsMaster.Artists.Column_ArtistName));
            names.add(name);
        }

        return names;
    }

    public ArrayList<byte[]> searchPhotograph(String artistName){
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {ArtistsMaster.Photographs.Column_Image};
        String sortBy = ArtistsMaster.Artists._ID;

        Cursor cursor = db.query(ArtistsMaster.Photographs.Table_Name_PhotographDetails, columns, null, null, null, null, sortBy);

        ArrayList<byte[]> images = new ArrayList<>();

        while (cursor.moveToNext()){
            byte[] image;
            image = cursor.getBlob(cursor.getColumnIndexOrThrow(ArtistsMaster.Photographs.Column_Image));
            images.add(image);
        }

        return images;
    }
}
