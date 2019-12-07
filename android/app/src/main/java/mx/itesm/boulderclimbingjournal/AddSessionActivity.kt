package mx.itesm.boulderclimbingjournal

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddSessionActivity : AppCompatActivity() {

    var recyclerViewLoggedClimbs: RecyclerView? = null
    var addClimbButton: Button? = null
    var addLocationButton: Button? = null

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

        recyclerViewLoggedClimbs = findViewById(R.id.recyclerViewLoggedClimbs)
        addClimbButton = findViewById(R.id.button_add_climb)
        addLocationButton = findViewById(R.id.button_location)

        recyclerViewLoggedClimbs?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(dbHelper.getAllLoggedClimbs())

        loadDateQuestionFragment()

        addClimbButton?.setOnClickListener {
            dbHelper.addLoggedClimb(
                    LoggedClimb(grades[indexGrade], descriptions[indexDescription], "notes")
            )
            indexGrade = (indexGrade+1)%grades.size  // TODO: delete
            indexDescription = (indexDescription+1)%descriptions.size  // TODO: delete
            refreshLoggedClimbs()
        }
        addLocationButton?.setOnClickListener {
            var loggedClimbs: ArrayList<LoggedClimb> = dbHelper.getAllLoggedClimbs()
            if(loggedClimbs.size == 0){
                showDialog("Error", "No Data Found")
            } else {
                val buffer = StringBuffer()
                for(loggedClimb:LoggedClimb in loggedClimbs) {
                    buffer.append("ID: " + loggedClimb.id.toString() + "\n")
                    buffer.append("GRADE: " + loggedClimb.grade + "\n")
                    buffer.append("DESC: " + loggedClimb.description + "\n")
                    buffer.append("NOTES: " + loggedClimb.notes + "\n\n")
                }
                showDialog("Climbs Listing", buffer.toString())
            }
        }
    }

    private fun refreshLoggedClimbs() {
        recyclerViewLoggedClimbs?.adapter = RecyclerViewLoggedClimbsAdapter(dbHelper.getAllLoggedClimbs())
    }

    private fun loadDateQuestionFragment(){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentAddSessionDate, DateQuestionFragment() as Fragment)
        fragmentManager.commit()
    }
    fun loadDateSelectionFragmentTodayYes(){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentAddSessionDate, DateSelectionFragment(clickedTodayYes = true) as Fragment)
        fragmentManager.commit()
    }
    fun loadDateSelectionFragmentTodayNo(){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentAddSessionDate, DateSelectionFragment(clickedTodayYes = false) as Fragment)
        fragmentManager.commit()
    }
}
