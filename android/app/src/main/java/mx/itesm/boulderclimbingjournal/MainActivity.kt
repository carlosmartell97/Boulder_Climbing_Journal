package mx.itesm.boulderclimbingjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val tag: String = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addSessionsButton: Button = findViewById(R.id.menu_add_session_button)
        val sessionsLogButton: Button = findViewById(R.id.menu_sessions_log_button)
        val statsButton: Button = findViewById(R.id.menu_stats_button)
        val gradeConversionButton: Button = findViewById(R.id.menu_grade_conversion_button)

        addSessionsButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddSessionActivity::class.java))
        })
        sessionsLogButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SessionsLogActivity::class.java))
        })
        statsButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        })
        gradeConversionButton.setOnClickListener(View.OnClickListener {
            Log.e(tag, "grade conversion button clicked.")
        })
    }
}
