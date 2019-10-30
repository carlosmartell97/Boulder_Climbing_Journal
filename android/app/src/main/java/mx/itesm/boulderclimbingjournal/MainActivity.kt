package mx.itesm.boulderclimbingjournal

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
            Log.e(tag, "add session button clicked.")
        })
        sessionsLogButton.setOnClickListener(View.OnClickListener {
            Log.e(tag, "sessions log button clicked.")
        })
        statsButton.setOnClickListener(View.OnClickListener {
            Log.e(tag, "stats button clicked.")
        })
        gradeConversionButton.setOnClickListener(View.OnClickListener {
            Log.e(tag, "grade conversion button clicked.")
        })
    }
}
