package mx.itesm.boulderclimbingjournal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "bcj_logged_data.db"
        val DATABASE_VERSION = 1

        val TABLE_NAME_CLIMB = "Logged_Climb"
        val TABLE_NAME_SESSION = "Logged_Session"
        val TABLE_NAME_SESSION_CLIMB = "Session_Climb"

        val COL_CLIMB_ID = "ID"
        val COL_CLIMB_GRADE = "GRADE"
        val COL_CLIMB_DESCRIPTION = "DESCRIPTION"
        val COL_CLIMB_NOTES = "NOTES"
        val COL_CLIMB_POINTS = "POINTS"

        val COL_SESSION_ID = "ID"
        val COL_SESSION_DATE = "DATE"
        val COL_SESSION_LOCATION = "LOCATION"
        val COL_SESSION_NOTES = "NOTES"
        val COL_SESSION_POINTS = "POINTS"
        val COL_SESSION_CLIMBS = "CLIMBS"

        val COL_SESSION_CLIMB_ID = "ID"
        val COL_SESSION_CLIMB_SESSION_ID = "SESSION_ID"
        val COL_SESSION_CLIMB_CLIMB_ID = "CLIMB_ID"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME_CLIMB ("+
                "$COL_CLIMB_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_CLIMB_GRADE TEXT,"+
                "$COL_CLIMB_DESCRIPTION TEXT,"+
                "$COL_CLIMB_NOTES TEXT,"+
                "$COL_CLIMB_POINTS INTEGER)")
        db.execSQL("CREATE TABLE $TABLE_NAME_SESSION ("+
                "$COL_SESSION_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_SESSION_DATE DATETIME,"+
                "$COL_SESSION_LOCATION TEXT,"+
                "$COL_SESSION_NOTES TEXT,"+
                "$COL_SESSION_POINTS INTEGER,"+
                "$COL_SESSION_CLIMBS INTEGER)")
        db.execSQL("CREATE TABLE $TABLE_NAME_SESSION_CLIMB ("+
                "$COL_SESSION_CLIMB_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_SESSION_CLIMB_SESSION_ID INTEGER,"+
                "$COL_SESSION_CLIMB_CLIMB_ID INTEGER,"+
                "FOREIGN KEY($COL_SESSION_CLIMB_SESSION_ID) REFERENCES $TABLE_NAME_SESSION($COL_SESSION_ID),"+
                "FOREIGN KEY($COL_SESSION_CLIMB_CLIMB_ID) REFERENCES $TABLE_NAME_CLIMB($COL_CLIMB_ID))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CLIMB)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SESSION)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SESSION_CLIMB)
        onCreate(db)
    }

    fun addLoggedClimb(loggedClimb: LoggedClimb): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_CLIMB_GRADE, loggedClimb.grade)
        contentValues.put(COL_CLIMB_DESCRIPTION, loggedClimb.description)
        contentValues.put(COL_CLIMB_NOTES, loggedClimb.notes)
        contentValues.put(COL_CLIMB_POINTS, loggedClimb.points)
        return db.insert(TABLE_NAME_CLIMB, null, contentValues)
    }

    fun addLoggedSession(loggedSession: LoggedSession, climbIds: ArrayList<Long>): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_SESSION_DATE, loggedSession.date)
        contentValues.put(COL_SESSION_LOCATION, loggedSession.location)
        contentValues.put(COL_SESSION_NOTES, loggedSession.notes)
        contentValues.put(COL_SESSION_POINTS, loggedSession.points)
        contentValues.put(COL_SESSION_CLIMBS, loggedSession.climbs)
        val sessionId: Long = db.insert(TABLE_NAME_SESSION, null, contentValues)
        for(climbId:Long in climbIds){
            addClimbSession(climbId, sessionId)
        }
        return sessionId
    }

    private fun addClimbSession(climbId: Long, sessionId: Long): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_SESSION_CLIMB_CLIMB_ID, climbId)
        contentValues.put(COL_SESSION_CLIMB_SESSION_ID, sessionId)
        return db.insert(TABLE_NAME_SESSION_CLIMB, null, contentValues)
    }

    fun updateLoggedClimb(loggedClimb: LoggedClimb): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_CLIMB_GRADE, loggedClimb.grade)
        contentValues.put(COL_CLIMB_DESCRIPTION, loggedClimb.description)
        contentValues.put(COL_CLIMB_NOTES, loggedClimb.notes)
        contentValues.put(COL_CLIMB_POINTS, loggedClimb.points)
        return db.update(TABLE_NAME_CLIMB, contentValues, "${COL_CLIMB_ID}_ID = ?", arrayOf(loggedClimb.id.toString()))
    }

    fun updateLoggedSession(loggedSession: LoggedSession): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_SESSION_DATE, loggedSession.date)
        contentValues.put(COL_SESSION_LOCATION, loggedSession.location)
        contentValues.put(COL_SESSION_NOTES, loggedSession.notes)
        contentValues.put(COL_SESSION_POINTS, loggedSession.points)
        contentValues.put(COL_SESSION_CLIMBS, loggedSession.climbs)
        return db.update(TABLE_NAME_SESSION, contentValues, "${COL_SESSION_ID}_ID = ?", arrayOf(loggedSession.id.toString()))
    }

    fun deleteLoggedClimb(loggedClimbId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME_CLIMB,"$COL_CLIMB_ID = ?", arrayOf(loggedClimbId.toString()))
        //db.close()
    }

    fun deleteLoggedSession(loggedSession: LoggedSession) {
        val db = this.writableDatabase
        /*for(climbmId:Long in sessionAllClimbIds){  //  TODO: delete all climbs for this session
            deleteLoggedClimb(climbId)
        }*/
        db.delete(TABLE_NAME_SESSION,"$COL_SESSION_ID = ?", arrayOf(loggedSession.id.toString()))
        //db.close()
    }

    fun getAllLoggedSessions(): ArrayList<LoggedSession> {
        var loggedSessions: ArrayList<LoggedSession> = ArrayList<LoggedSession>()
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_SESSION", null)
        while(cursor.moveToNext()){
            loggedSessions.add(
                    LoggedSession(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5)
                    )
            )
        }
        //db.close()
        return loggedSessions
    }
}