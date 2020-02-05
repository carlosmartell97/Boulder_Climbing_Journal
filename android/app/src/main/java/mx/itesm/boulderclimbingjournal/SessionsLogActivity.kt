package mx.itesm.boulderclimbingjournal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SessionsLogActivity : AppCompatActivity() {

    var recyclerViewLoggedSessions: RecyclerView? = null
    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions_log)

        setReferences()

        recyclerViewLoggedSessions?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewLoggedSessions?.adapter = RecyclerViewLoggedSessionsAdapter(dbHelper.getAllLoggedSessions())
        recyclerViewLoggedSessions?.addItemDecoration(DividerItemDecoration(recyclerViewLoggedSessions?.context, DividerItemDecoration.VERTICAL))
    }

    private fun setReferences(){
        recyclerViewLoggedSessions = findViewById(R.id.sessionsLogRecyclerView)
    }
}
