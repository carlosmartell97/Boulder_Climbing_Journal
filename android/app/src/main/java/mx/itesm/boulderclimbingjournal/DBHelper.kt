package mx.itesm.boulderclimbingjournal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "logged_climbs.db"
        val TABLE_NAME = "Logged_Climb"
        val COL_ID = "ID"
        val COL_GRADE = "GRADE"
        val COL_DESCRIPTION = "DESCRIPTION"
        val COL_NOTES = "NOTES"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME ("+
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_GRADE TEXT,"+
                "$COL_DESCRIPTION TEXT,"+
                "$COL_NOTES TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addLoggedClimb(loggedClimb: LoggedClimb) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GRADE, loggedClimb.grade)
        contentValues.put(COL_DESCRIPTION, loggedClimb.description)
        contentValues.put(COL_NOTES, loggedClimb.notes)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateLoggedClimb(loggedClimb: LoggedClimb): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GRADE, loggedClimb.grade)
        contentValues.put(COL_DESCRIPTION, loggedClimb.description)
        contentValues.put(COL_NOTES, loggedClimb.notes)
        return db.update(TABLE_NAME, contentValues, "$COL_ID = ?", arrayOf(loggedClimb.id.toString()))
    }

    fun deleteLoggedClimb(loggedClimb: LoggedClimb) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID = ?", arrayOf(loggedClimb.id.toString()))
        //db.close()
    }

    fun getAllLoggedClimbs(): ArrayList<LoggedClimb> {
        var loggedClimbs: ArrayList<LoggedClimb> = ArrayList<LoggedClimb>()
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while(cursor.moveToNext()){
            loggedClimbs.add(
                    LoggedClimb(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            )
        }
        //db.close()
        return loggedClimbs
    }
}