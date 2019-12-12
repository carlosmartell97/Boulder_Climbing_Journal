package mx.itesm.boulderclimbingjournal

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class AddSessionActivity : AppCompatActivity() {

    var recyclerViewLoggedClimbs: RecyclerView? = null
    var addClimbButton: Button? = null
    var addLocationButton: Button? = null
    var dateSelectionButton: Button? = null
    var pointsTextview: TextView? = null

    internal var dbHelper = DatabaseHelper(this)
    var points: Int = 0
    val gradePoints: Array<Int> = arrayOf(
                //                      onsight     flash   attempts    repeat
            3,  // VB   -@              4           4       3           3
            3,  // V0   -@              4           4       3           3
            4,  // V1   -@@             5           5       4           4
            6,  // V2   -@@@            8           8       7           6
            8,  // V3   -@@@            10          10      9           8
            11, // V4   -@@@@           14          14      12          11
            14, // V5   -@@@@           18          18      15          14
            18, // V6   -@@@@@          23          23      20          18
            22, // V7   -@@@@@          29          28      24          22
            27, // V8   -@@@@@@         35          34      30          27
            33, // V9   -@@@@@@@        43          41      36          33
            40  // V10  -@@@@@@@@       52          50      44          40
    )
    val gradeMultipliers: Array<Double> = arrayOf(
            1.3,    // onsight
            1.25,   // flash
            1.1,    // attempts
            1.0     // repeat
    )
    val grades = arrayOf("VB", "V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10")  // TODO: delete
    val descriptions = arrayOf("onsight", "flash", "attempts", "repeat")  // TODO: delete
    var indexGrade: Int = 0  // TODO: delete
    var indexDescription: Int = 0  // TODO: delete

    fun showDialog(title : String,Message : String){  // TODO: delete
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_session)

        setReferences()

        recyclerViewLoggedClimbs?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(dbHelper.getAllLoggedClimbs())

        setCurrentDate()
        setListeners()
    }

    private fun setReferences(){
        recyclerViewLoggedClimbs = findViewById(R.id.recyclerViewLoggedClimbs)
        addClimbButton = findViewById(R.id.button_add_climb)
        addLocationButton = findViewById(R.id.button_location)
        dateSelectionButton = findViewById(R.id.button_add_session_date_selection)
        pointsTextview = findViewById(R.id.textViewPoints)
    }
    private fun setListeners(){
        addClimbButton?.setOnClickListener {
            dbHelper.addLoggedClimb(
                    LoggedClimb(grades[indexGrade], descriptions[indexDescription], "notes")
            )
            points += (gradePoints[indexGrade]*gradeMultipliers[indexDescription]).roundToInt()   // TODO: delete
            indexGrade = (indexGrade+1)%grades.size  // TODO: delete
            indexDescription = (indexDescription+1)%descriptions.size  // TODO: delete
            refreshLoggedClimbs()
        }
        addLocationButton?.setOnClickListener {
            var loggedClimbs: Array<ArrayList<LoggedClimb>> = dbHelper.getAllLoggedClimbs()
            if(loggedClimbs.size == 0){
                showDialog("Error", "No Data Found")
            } else {
                val buffer = StringBuffer()
                for(i in 0..11){
                    for(loggedClimb:LoggedClimb in loggedClimbs[i]) {
                        buffer.append("ID: " + loggedClimb.id.toString() + "\n")
                        buffer.append("GRADE: " + loggedClimb.grade + "\n")
                        buffer.append("DESC: " + loggedClimb.description + "\n")
                        buffer.append("NOTES: " + loggedClimb.notes + "\n\n")
                    }
                }
                showDialog("Climbs Listing", buffer.toString())
            }
        }
        dateSelectionButton?.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun refreshLoggedClimbs() {
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(dbHelper.getAllLoggedClimbs())
        pointsTextview?.setText("${points.toString()} points")
    }

    private fun dateToText(date: Date): String{
        return SimpleDateFormat("MMM dd, yyyy").format(date)
    }
    private fun setCurrentDate(){
        val cal = Calendar.getInstance()
        dateSelectionButton?.setText(dateToText(cal.time))
    }
    private fun showDatePickerDialog(){
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(year, monthOfYear, dayOfMonth)
            dateSelectionButton?.setText(dateToText(cal.time))
        }, year, month, day)
        dpd.datePicker.maxDate = cal.timeInMillis
        dpd.show()
    }
}
