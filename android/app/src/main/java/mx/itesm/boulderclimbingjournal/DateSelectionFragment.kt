package mx.itesm.boulderclimbingjournal

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class DateSelectionFragment(val clickedTodayYes: Boolean) : Fragment() {
    var containerContext: Context? = null
    var dateSelectionButton: Button? = null
    var showDialogDatePicker: Boolean = false

    init {
        this.showDialogDatePicker = !clickedTodayYes
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.containerContext = container?.context
        val view: View = inflater.inflate(R.layout.fragment_date_selection, container, false)
        this.dateSelectionButton = view.findViewById<Button>(R.id.button_add_session_date_selection)

        (dateSelectionButton as Button).text = dateToText(Calendar.getInstance().time)
        (dateSelectionButton as Button).setOnClickListener{
            showDatePickerDialog()
        }

        if(showDialogDatePicker){ showDatePickerDialog() }

        return view
    }

    private fun showDatePickerDialog(){
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(containerContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(year, monthOfYear, dayOfMonth)
            (dateSelectionButton as Button).text = dateToText(cal.time)
        }, year, month, day)
        dpd.datePicker.maxDate = cal.timeInMillis
        dpd.show()
    }

    private fun dateToText(date: Date): String{
        return SimpleDateFormat("MMM dd, yyyy").format(date)
    }
}
