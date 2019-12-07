package mx.itesm.boulderclimbingjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoggedClimbsAdapter(loggedClimbs: ArrayList<LoggedClimb>): RecyclerView.Adapter<RecyclerViewLoggedClimbsAdapter.ViewHolder>() {

    var loggedClimbs: ArrayList<LoggedClimb> = ArrayList<LoggedClimb>()

    init {
        this.loggedClimbs = loggedClimbs
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_logged_climb_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(loggedClimbs[position])
    }

    override fun getItemCount(): Int {
        return loggedClimbs.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var gradeTextView: TextView? = null
        private var descriptionImage: ImageView? = null

        init {
            gradeTextView = itemView.findViewById(R.id.loggedClimbGradeText)
            descriptionImage = itemView.findViewById(R.id.descriptionLogo)
        }

        fun bind(loggedClimb: LoggedClimb){
            gradeTextView?.setText(loggedClimb.grade)
            when(loggedClimb.description) {
                "onsight" -> descriptionImage?.setImageResource(R.drawable.ic_onsight)
                "flash" -> descriptionImage?.setImageResource(R.drawable.ic_flash)
                "attempts" -> descriptionImage?.setImageResource(R.drawable.ic_attempts)
                else -> descriptionImage?.setImageResource(R.drawable.ic_repeat)
            }
        }
    }
}