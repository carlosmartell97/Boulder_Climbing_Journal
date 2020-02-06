package mx.itesm.boulderclimbingjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddLocationActivity : AppCompatActivity() {

    var recyclerViewGymNames: RecyclerView? = null
    var addGymButton: FloatingActionButton? = null
    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        setReferences()
        setListeners()

        recyclerViewGymNames?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewGymNames?.adapter = RecyclerViewGymNamesAdapter(dbHelper.getAllLoggedGyms(), this)
        recyclerViewGymNames?.addItemDecoration(DividerItemDecoration(recyclerViewGymNames?.context, DividerItemDecoration.VERTICAL))
    }

    private fun setReferences(){
        recyclerViewGymNames= findViewById(R.id.recyclerViewGymNames)
        addGymButton = findViewById(R.id.addGymButton)
    }

    private fun setListeners(){
        addGymButton?.setOnClickListener {
            showAddGymNameDialog()
        }
    }

    private fun refreshGymNames() {
        recyclerViewGymNames?.adapter = RecyclerViewGymNamesAdapter(dbHelper.getAllLoggedGyms(), this)
    }

    fun gymNameSelected(gymName: String){
        val returnIntent: Intent = Intent()
        returnIntent.putExtra("gymName", gymName)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun showAddGymNameDialog(){
        val alert = AlertDialog.Builder(this)
        val gymNameView = layoutInflater.inflate(R.layout.gym_name_edit_text, null)
        val gymNameEditText: EditText = gymNameView.findViewById(R.id.gymNameEditText)
        alert.setView(gymNameView)
        alert.setPositiveButton("ADD"){_, _ ->
            val enteredGymName: String = gymNameEditText.text.toString()
            if(enteredGymName == ""){
                Toast.makeText(this, "enter a valid gym name", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.addLoggedGym(
                        LoggedGym(enteredGymName)
                )
                refreshGymNames()
            }
        }
        alert.setNegativeButton("CANCEL", null)
        alert.show()
    }
}
