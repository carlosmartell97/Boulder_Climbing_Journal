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
        val COL_POINTS = "POINTS"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME ("+
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_GRADE TEXT,"+
                "$COL_DESCRIPTION TEXT,"+
                "$COL_NOTES TEXT,"+
                "$COL_POINTS INTEGER)")
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
        contentValues.put(COL_POINTS, loggedClimb.points)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateLoggedClimb(loggedClimb: LoggedClimb): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GRADE, loggedClimb.grade)
        contentValues.put(COL_DESCRIPTION, loggedClimb.description)
        contentValues.put(COL_NOTES, loggedClimb.notes)
        contentValues.put(COL_POINTS, loggedClimb.points)
        return db.update(TABLE_NAME, contentValues, "$COL_ID = ?", arrayOf(loggedClimb.id.toString()))
    }

    fun deleteLoggedClimb(loggedClimb: LoggedClimb) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID = ?", arrayOf(loggedClimb.id.toString()))
        //db.close()
    }

    fun getAllLoggedClimbs(): Array<ArrayList<LoggedClimb>> {
        var loggedClimbs: Array<ArrayList<LoggedClimb>> = arrayOf(
                ArrayList<LoggedClimb>(),   // VB
                ArrayList<LoggedClimb>(),   // V0
                ArrayList<LoggedClimb>(),   // V1
                ArrayList<LoggedClimb>(),   // V2
                ArrayList<LoggedClimb>(),   // V3
                ArrayList<LoggedClimb>(),   // V4
                ArrayList<LoggedClimb>(),   // V5
                ArrayList<LoggedClimb>(),   // V6
                ArrayList<LoggedClimb>(),   // V7
                ArrayList<LoggedClimb>(),   // V8
                ArrayList<LoggedClimb>(),   // V9
                ArrayList<LoggedClimb>()    // V10
        )
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while(cursor.moveToNext()){
            val grade: String = cursor.getString(1)
            when(grade){
                "VB" -> loggedClimbs[0].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V0" -> loggedClimbs[1].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V1" -> loggedClimbs[2].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V2" -> loggedClimbs[3].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V3" -> loggedClimbs[4].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V4" -> loggedClimbs[5].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V5" -> loggedClimbs[6].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V6" -> loggedClimbs[7].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V7" -> loggedClimbs[8].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V8" -> loggedClimbs[9].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                "V9" -> loggedClimbs[10].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))
                else -> loggedClimbs[11].add(LoggedClimb(cursor.getInt(0),grade,cursor.getString(2),cursor.getString(3),cursor.getInt(4)))   // V10
            }
        }
        //db.close()
        return loggedClimbs
    }
}