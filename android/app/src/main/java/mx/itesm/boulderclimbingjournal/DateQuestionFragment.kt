package mx.itesm.boulderclimbingjournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class DateQuestionFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_date_question, container, false)
        val buttonTodayYes: ImageView = view.findViewById(R.id.button_add_session_date_question_yes)
        val buttonTodayNo: ImageView = view.findViewById(R.id.button_add_session_date_question_no)

        buttonTodayYes.setOnClickListener{
            (activity as AddSessionActivity).loadDateSelectionFragmentTodayYes()
        }
        buttonTodayNo.setOnClickListener{
            (activity as AddSessionActivity).loadDateSelectionFragmentTodayNo()
        }

        return view
    }
}
