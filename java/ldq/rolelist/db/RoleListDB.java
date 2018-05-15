package ldq.rolelist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;

import ldq.rolelist.model.Role;

public class RoleListDB {
    private static final String DB_NAME = "role_list";
    private static final int VERSION = 1;
    private static RoleListDB roleListDB;
    private SQLiteDatabase db;

    private RoleListDB(Context context) {
        RoleListDBHelper dbHelper = new RoleListDBHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getReadableDatabase();
    }

    public synchronized static RoleListDB getInstance(Context context) {
        if(roleListDB == null) {
            roleListDB = new RoleListDB(context);
        }
        return roleListDB;
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select name from Role",null);
        if(cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("name")).trim());
            } while(cursor.moveToNext());
        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }

    public ArrayList<String> getNamesByTag(String tag) {
        HashSet<String> set = new HashSet<String>();
        Cursor cursor = db.rawQuery("select name from Tag where name like ? or tag like ?",
                new String [] {"%"+tag+"%","%"+tag+"%"});
        if(cursor.moveToFirst()) {
            do {
                set.add(cursor.getString(cursor.getColumnIndex("name")).trim());
            } while(cursor.moveToNext());
        }
        ArrayList<String> list = new ArrayList<String>();
        for(String name : set) {
            list.add(name);
        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }

    public Role getRoleByName(String name) {
        Role role = new Role();
        Cursor cursor = db.rawQuery("select * from Role where name='"+name+"'",null);
        if(cursor.moveToFirst())
        {
            role.name = cursor.getString(cursor.getColumnIndex("name")).trim();
            role.romaji = cursor.getString(cursor.getColumnIndex("romaji")).trim();
            role.sex = cursor.getString(cursor.getColumnIndex("sex")).trim();
            role.blood = cursor.getString(cursor.getColumnIndex("blood")).trim();
            role.birthday = cursor.getString(cursor.getColumnIndex("birthday")).trim();
            role.note = cursor.getString(cursor.getColumnIndex("note")).trim();
        }
        if(cursor != null) {
            cursor.close();
        }
        return role;
    }

    public ArrayList<Role> getRolesByBirthDay(String birthday) {
        ArrayList<Role> list = new ArrayList<Role>();
        Cursor cursor = db.rawQuery("select * from Role where birthday='"+birthday+"'",null);
        if(cursor.moveToFirst())
        {
            do {
                Role role = new Role();
                role.name = cursor.getString(cursor.getColumnIndex("name")).trim();
                role.romaji = cursor.getString(cursor.getColumnIndex("romaji")).trim();
                role.sex = cursor.getString(cursor.getColumnIndex("sex")).trim();
                role.blood = cursor.getString(cursor.getColumnIndex("blood")).trim();
                role.birthday = cursor.getString(cursor.getColumnIndex("birthday")).trim();
                role.note = cursor.getString(cursor.getColumnIndex("note")).trim();
                list.add(role);
            } while(cursor.moveToNext());

        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }
}
