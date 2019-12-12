package mx.itesm.boulderclimbingjournal

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoggedClimbsAdapter(loggedClimbs: Array<ArrayList<LoggedClimb>>): RecyclerView.Adapter<RecyclerViewLoggedClimbsAdapter.ViewHolder>() {

    var loggedClimbs: Array<ArrayList<LoggedClimb>> = Array(12, { ArrayList<LoggedClimb>()})
    var sections: ArrayList<Int> = ArrayList<Int>()

    init {
        this.loggedClimbs = loggedClimbs
        for(i in 0..loggedClimbs.size-1){
            if(loggedClimbs[i].size != 0){
                sections.add(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_logged_climb_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, section: Int) {
        holder.bind(loggedClimbs[sections[section]])
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var climbsGradeCount: TextView? = null
        private var gradeImage: ImageView? = null
        private var descriptionImagesScroll: LinearLayout? = null

        init {
            climbsGradeCount = itemView.findViewById(R.id.climbsGradeCountText)
            gradeImage = itemView.findViewById(R.id.gradeLogo)
            descriptionImagesScroll = itemView.findViewById(R.id.logosHorizontal)
        }

        fun bind(loggedClimbs: ArrayList<LoggedClimb>){
            climbsGradeCount?.setText(loggedClimbs.size.toString())
            when(loggedClimbs[0].grade) {
                "VB" -> gradeImage?.setImageResource(R.drawable.ic_vb)
                "V0" -> gradeImage?.setImageResource(R.drawable.ic_v0)
                "V1" -> gradeImage?.setImageResource(R.drawable.ic_v1)
                "V2" -> gradeImage?.setImageResource(R.drawable.ic_v2)
                "V3" -> gradeImage?.setImageResource(R.drawable.ic_v3)
                "V4" -> gradeImage?.setImageResource(R.drawable.ic_v4)
                "V5" -> gradeImage?.setImageResource(R.drawable.ic_v5)
                "V6" -> gradeImage?.setImageResource(R.drawable.ic_v6)
                "V7" -> gradeImage?.setImageResource(R.drawable.ic_v7)
                "V8" -> gradeImage?.setImageResource(R.drawable.ic_v8)
                "V9" -> gradeImage?.setImageResource(R.drawable.ic_v9)
                else -> gradeImage?.setImageResource(R.drawable.ic_v10)
            }

            Log.d("TAG", loggedClimbs[0].grade+": "+loggedClimbs.size)
            descriptionImagesScroll?.removeAllViews()
            for(loggedClimb in loggedClimbs){
                val descriptionImageView: ImageView = ImageView(itemView.context)
                val layoutSize: Float = itemView.resources.getDimension(R.dimen.logged_climb_logo_size)
                descriptionImageView.layoutParams = LinearLayout.LayoutParams(layoutSize.toInt(), layoutSize.toInt())
                when(loggedClimb.description) {
                    "onsight" -> descriptionImageView.setImageResource(R.drawable.ic_onsight)
                    "flash" -> descriptionImageView.setImageResource(R.drawable.ic_flash)
                    "attempts" -> descriptionImageView.setImageResource(R.drawable.ic_attempts)
                    else -> descriptionImageView.setImageResource(R.drawable.ic_repeat)
                }
                descriptionImageView?.setOnClickListener {
                    val toast: Toast = Toast.makeText(itemView.context, loggedClimb.id.toString()+": "+loggedClimbs[0].grade+", "+loggedClimb.description+". "+loggedClimb.notes+".", Toast.LENGTH_SHORT)
                    toast.show()
                }
                descriptionImagesScroll?.addView(descriptionImageView)
            }
        }
    }
}