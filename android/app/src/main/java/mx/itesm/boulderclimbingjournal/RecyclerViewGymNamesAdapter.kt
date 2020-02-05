package mx.itesm.boulderclimbingjournal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewGymNamesAdapter(gymNames: ArrayList<String>, addLocationActivityContext: Context): RecyclerView.Adapter<RecyclerViewGymNamesAdapter.ViewHolder>() {

    var gymNames: ArrayList<String> = ArrayList<String>()
    var addLocationActivityContext: Context? = null

    init {
        this.gymNames = gymNames
        this.addLocationActivityContext = addLocationActivityContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_gym_name, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gymNames[position], addLocationActivityContext)
    }

    override fun getItemCount(): Int {
        return gymNames.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var gymNameTextView: TextView? = null

        init {
            gymNameTextView = itemView.findViewById(R.id.gymNameTextView)
        }

        fun bind(gymName: String, addLocationActivityContext: Context?){
            gymNameTextView?.setText(gymName)
            gymNameTextView?.setOnClickListener {
                (addLocationActivityContext as AddLocationActivity).gymNameSelected(gymName)
            }
        }
    }
}