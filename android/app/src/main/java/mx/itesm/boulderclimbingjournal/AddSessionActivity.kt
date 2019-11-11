package mx.itesm.boulderclimbingjournal

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddSessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_session)

        loadDateQuestionFragment()

        val recyclerViewLoggedClimbs: RecyclerView = findViewById(R.id.recyclerViewLoggedClimbs)
        recyclerViewLoggedClimbs.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RecyclerViewLoggedClimbsAdapter()
        recyclerViewLoggedClimbs.adapter = adapter
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
