package mx.itesm.boulderclimbingjournal

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddSessionActivity : AppCompatActivity() {

    var recyclerViewLoggedClimbs: RecyclerView? = null
    var addClimbButton: Button? = null
    var addLocationButton: Button? = null
    var dateSelectionButton: Button? = null

    internal var dbHelper = DatabaseHelper(this)
    val grades = arrayOf("VB", "V0", "V1", "V2", "V3", "V4", "V5", "V5", "V6", "V7", "V8", "V9", "V10")  // TODO: delete
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
    }
    private fun setListeners(){
        addClimbButton?.setOnClickListener {
            dbHelper.addLoggedClimb(
                    LoggedClimb(grades[indexGrade], descriptions[indexDescription], "notes")
            )
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
