package mx.itesm.boulderclimbingjournal

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    var pointsTextview: TextView? = null

    internal var dbHelper = DatabaseHelper(this)
    var points: Int = 0

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
            startActivityForResult(Intent(this, AddClimbActivity::class.java), 1)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && data!=null){
            val loggedClimb: LoggedClimb = data.getSerializableExtra("loggedClimb") as LoggedClimb
            dbHelper.addLoggedClimb(loggedClimb)
            points += loggedClimb.points
            refreshLoggedClimbs()
        } else {
            Toast.makeText(this, "adding climb cancelled or something went wrong...", Toast.LENGTH_SHORT).show()
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
