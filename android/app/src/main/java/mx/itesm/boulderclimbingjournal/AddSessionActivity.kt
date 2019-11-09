package mx.itesm.boulderclimbingjournal

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AddSessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_session)

        var button1: Button = findViewById<Button>(R.id.uno)
        var button2: Button = findViewById<Button>(R.id.dos)
        button1.setOnClickListener{
            loadDateQuestionFragment()
        }
        button2.setOnClickListener{
            loadDateSelectionFragmentTodayYes()
        }

        loadDateQuestionFragment()
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
