package mx.itesm.boulderclimbingjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddLocationActivity : AppCompatActivity() {

    var recyclerViewGymNames: RecyclerView? = null
    var gymNames: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        setReferences()

        recyclerViewGymNames?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewGymNames?.adapter = RecyclerViewGymNamesAdapter(arrayListOf("YE Center", "Motion Boulder"), this) // TODO: retrieve gym names from DB
        recyclerViewGymNames?.addItemDecoration(DividerItemDecoration(recyclerViewGymNames?.context, DividerItemDecoration.VERTICAL))
    }

    private fun setReferences(){
        recyclerViewGymNames= findViewById(R.id.recyclerViewGymNames)
    }

    private fun refreshGymNames() {
        //recyclerViewGymNames?.adapter = RecyclerViewGymNamesAdapter(dbHelper.getAllGymNames())    // TODO
    }

    fun gymNameSelected(gymName: String){
        val returnIntent: Intent = Intent()
        returnIntent.putExtra("gymName", gymName)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
