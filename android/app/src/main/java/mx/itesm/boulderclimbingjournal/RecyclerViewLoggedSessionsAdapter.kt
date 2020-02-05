package mx.itesm.boulderclimbingjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoggedSessionsAdapter(loggedSessions: ArrayList<LoggedSession>): RecyclerView.Adapter<RecyclerViewLoggedSessionsAdapter.ViewHolder>() {

    var loggedSessions: ArrayList<LoggedSession> = ArrayList<LoggedSession>()

    init {
        this.loggedSessions = loggedSessions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_logged_session, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(loggedSessions[position])
    }

    override fun getItemCount(): Int {
        return loggedSessions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dateTextView: TextView? = null
        private var locationTextView: TextView? = null
        private var pointsTextView: TextView? = null
        private var climbsTextView: TextView? = null

        init {
            dateTextView = itemView.findViewById(R.id.loggedSessionDateTextView)
            locationTextView = itemView.findViewById(R.id.loggedSessionLocationTextView)
            pointsTextView = itemView.findViewById(R.id.loggedSessionPointsTextView)
            climbsTextView = itemView.findViewById(R.id.loggedSessionClimbsTextView)
        }

        fun bind(loggedSession: LoggedSession){
            dateTextView?.setText(loggedSession.date?.substring(0, 13))
            locationTextView?.setText(loggedSession.location)
            pointsTextView?.setText(loggedSession.points.toString()+" points")
            climbsTextView?.setText(loggedSession.climbs.toString()+" climbs")
        }
    }
}