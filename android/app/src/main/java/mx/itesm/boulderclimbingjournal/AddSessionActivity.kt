package mx.itesm.boulderclimbingjournal

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    var notesEditText: EditText? = null
    var pointsTextview: TextView? = null
    var finishSessionButton: Button? = null

    internal var dbHelper = DatabaseHelper(this)
    var sessionDate: Date? = null
    var sessionLocation: String? = null
    var points: Int = 0
    var numClimbs: Int = 0
    var firstLoggedGym: Boolean = false
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_session)

        setReferences()

        recyclerViewLoggedClimbs?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(loggedClimbs)

        setCurrentDate()
        setListeners()
    }

    override fun onBackPressed() {
        if(numClimbs!=0 && points!=0){
            showGoBackUnsavedDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun setReferences(){
        recyclerViewLoggedClimbs = findViewById(R.id.recyclerViewLoggedClimbs)
        addClimbButton = findViewById(R.id.button_add_climb)
        addLocationButton = findViewById(R.id.button_location)
        dateSelectionButton = findViewById(R.id.button_add_session_date_selection)
        notesEditText = findViewById(R.id.editText_session_notes)
        pointsTextview = findViewById(R.id.textViewPoints)
        finishSessionButton = findViewById(R.id.button_finish_session)
    }
    private fun setListeners(){
        dateSelectionButton?.setOnClickListener {
            showDatePickerDialog()
        }
        val loggedGyms: ArrayList<LoggedGym> = dbHelper.getAllLoggedGyms()
        if(loggedGyms.isEmpty()){
            firstLoggedGym = true
            addLocationButton?.setOnClickListener {
                showFirstGymNameDialog()
            }
        } else {
            addLocationButton?.setOnClickListener {
                startActivityForResult(Intent(this, AddLocationActivity::class.java), 2)
            }
        }
        addClimbButton?.setOnClickListener {
            startActivityForResult(Intent(this, AddClimbActivity::class.java), 1)
        }
        finishSessionButton?.setOnClickListener {
            var climbIds: ArrayList<Long> = ArrayList<Long>()
            for(loggedClimbGrade:ArrayList<LoggedClimb> in loggedClimbs){
                for(loggedClimb:LoggedClimb in loggedClimbGrade){
                    climbIds.add(dbHelper.addLoggedClimb(loggedClimb))
                }
            }
            if(!climbIds.isEmpty() && points!=0){
                if(sessionLocation == null){
                    Toast.makeText(this, "you forgot to add a location", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.addLoggedSession(
                            LoggedSession(dateToTextComplete(sessionDate), sessionLocation.toString(), notesEditText?.text.toString(), points, numClimbs),
                            climbIds
                    )
                    if(firstLoggedGym){
                        dbHelper.addLoggedGym(
                                LoggedGym(sessionLocation.toString())
                        )
                    }
                    Toast.makeText(this, "session successfully saved!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "you haven't added any climbs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK && data!=null){
            numClimbs++
            val loggedClimb: LoggedClimb = data.getSerializableExtra("loggedClimb") as LoggedClimb
            points += loggedClimb.points
            val grade: String? = loggedClimb.grade
            when(grade){
                "VB" -> loggedClimbs[0].add(loggedClimb)
                "V0" -> loggedClimbs[1].add(loggedClimb)
                "V1" -> loggedClimbs[2].add(loggedClimb)
                "V2" -> loggedClimbs[3].add(loggedClimb)
                "V3" -> loggedClimbs[4].add(loggedClimb)
                "V4" -> loggedClimbs[5].add(loggedClimb)
                "V5" -> loggedClimbs[6].add(loggedClimb)
                "V6" -> loggedClimbs[7].add(loggedClimb)
                "V7" -> loggedClimbs[8].add(loggedClimb)
                "V8" -> loggedClimbs[9].add(loggedClimb)
                "V9" -> loggedClimbs[10].add(loggedClimb)
                else -> loggedClimbs[11].add(loggedClimb)   // V10
            }
            refreshLoggedClimbs()
        }
        else if(requestCode==2 && resultCode==Activity.RESULT_OK && data!=null){
            val gymName: String = data.getStringExtra("gymName")
            sessionLocation = gymName
            addLocationButton?.setText(gymName)
        }else {
            Toast.makeText(this, "adding climb/location cancelled or something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshLoggedClimbs() {
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(loggedClimbs)
        pointsTextview?.setText("${points.toString()} points")
    }

    private fun dateToText(date: Date?): String{
        return SimpleDateFormat("MMM dd, yyyy").format(date)
    }
    private fun dateToTextComplete(date: Date?): String{
        return SimpleDateFormat("MMM dd, yyyy HH:mm:ss").format(date)
    }
    private fun setCurrentDate(){
        val cal = Calendar.getInstance()
        dateSelectionButton?.setText(dateToText(cal.time))
        sessionDate = cal.time
    }
    private fun showDatePickerDialog(){
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(year, monthOfYear, dayOfMonth)
            sessionDate = cal.time
            dateSelectionButton?.setText(dateToText(sessionDate))
        }, year, month, day)
        dpd.datePicker.maxDate = cal.timeInMillis
        dpd.show()
    }
    private fun showFirstGymNameDialog(){
        val alert = AlertDialog.Builder(this)
        val gymNameView = layoutInflater.inflate(R.layout.gym_name_edit_text, null)
        val gymNameEditText: EditText = gymNameView.findViewById(R.id.gymNameEditText)
        if(sessionLocation != null){
            gymNameEditText.setText(sessionLocation)
        }
        alert.setView(gymNameView)
        alert.setPositiveButton("ADD"){_, _ ->
            val enteredGymName = gymNameEditText.text.toString()
            if(enteredGymName == ""){
                Toast.makeText(this, "enter a valid gym name", Toast.LENGTH_SHORT).show()
            } else {
                sessionLocation = enteredGymName
                addLocationButton?.setText(sessionLocation)
            }
        }
        alert.setNegativeButton("CANCEL", null)
        alert.show()
    }
    private fun showGoBackUnsavedDialog(){
        val alert = AlertDialog.Builder(this)
        alert.setMessage("You haven't saved this session yet, are you sure you want to leave?")
        alert.setPositiveButton("YES"){_, _ ->
            super.onBackPressed()
        }
        alert.setNegativeButton("NO", null)
        alert.show()
    }
}
