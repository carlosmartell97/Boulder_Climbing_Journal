package mx.itesm.boulderclimbingjournal

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

class AddClimbActivity : AppCompatActivity() {

    var vbButton: ImageView? = null
    var v0Button: ImageView? = null
    var v1Button: ImageView? = null
    var v2Button: ImageView? = null
    var v3Button: ImageView? = null
    var v4Button: ImageView? = null
    var v5Button: ImageView? = null
    var v6Button: ImageView? = null
    var v7Button: ImageView? = null
    var v8Button: ImageView? = null
    var v9Button: ImageView? = null
    var v10Button: ImageView? = null
    var helpButton: ImageView? = null
    var onsightButton: ImageView? = null
    var flashButton: ImageView? = null
    var attemptsButton: ImageView? = null
    var repeatButton: ImageView? = null
    var onsightText: TextView? = null
    var flashText: TextView? = null
    var attemptsText: TextView? = null
    var repeatText: TextView? = null
    var climbNotes: EditText? = null
    var saveClimbButton: Button? = null

    var previousGradeDrawableIndex: Int = -1
    var previousGradeButton: ImageView? = null
    var previousDescriptionDrawable: Int = -1
    var previousDescriptionButton: ImageView? = null
    var previousDescriptionText: TextView? = null

    val grades = arrayOf("VB", "V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10")
    val descriptions = arrayOf("onsight", "flash", "attempts", "repeat")
    val gradePoints: Array<Int> = arrayOf(
            //                      onsight     flash   attempts    repeat
            3,  // VB   -@              4           4       3           3
            3,  // V0   -@              4           4       3           3
            4,  // V1   -@@             5           5       4           4
            6,  // V2   -@@@            8           8       7           6
            8,  // V3   -@@@            10          10      9           8
            11, // V4   -@@@@           14          14      12          11
            14, // V5   -@@@@           18          18      15          14
            18, // V6   -@@@@@          23          23      20          18
            22, // V7   -@@@@@          29          28      24          22
            27, // V8   -@@@@@@         35          34      30          27
            33, // V9   -@@@@@@@        43          41      36          33
            40  // V10  -@@@@@@@@       52          50      44          40
    )
    val gradeMultipliers: Array<Double> = arrayOf(
            1.3,    // onsight
            1.25,   // flash
            1.1,    // attempts
            1.0     // repeat
    )
    var selectedGradeIndex: Int = -1
    var selectedAscentDescriptionIndex: Int = -1
    var loggedClimbPoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_climb)

        setReferences()
        setListeners()
    }

    private fun setReferences() {
        vbButton = findViewById(R.id.vb_logo)
        v0Button = findViewById(R.id.v0_logo)
        v1Button = findViewById(R.id.v1_logo)
        v2Button = findViewById(R.id.v2_logo)
        v3Button = findViewById(R.id.v3_logo)
        v4Button = findViewById(R.id.v4_logo)
        v5Button = findViewById(R.id.v5_logo)
        v6Button = findViewById(R.id.v6_logo)
        v7Button = findViewById(R.id.v7_logo)
        v8Button = findViewById(R.id.v8_logo)
        v9Button = findViewById(R.id.v9_logo)
        v10Button = findViewById(R.id.v10_logo)
        helpButton = findViewById(R.id.addClimbHelpButton)
        onsightButton = findViewById(R.id.onsight_logo)
        flashButton = findViewById(R.id.flash_logo)
        attemptsButton = findViewById(R.id.attempts_logo)
        repeatButton = findViewById(R.id.repeat_logo)
        onsightText = findViewById(R.id.onsight_text)
        flashText = findViewById(R.id.flash_text)
        attemptsText = findViewById(R.id.attempts_text)
        repeatText = findViewById(R.id.repeat_text)
        climbNotes = findViewById(R.id.editTextClimbNotes)
        saveClimbButton = findViewById(R.id.saveClimbButton)
    }

    private fun setListenerGrade(gradeButton: ImageView?, gradeIndex: Int, drawableSelected: Int, drawableBefore: Int) {
        gradeButton?.setOnClickListener{
            selectGradeButton(gradeButton, gradeIndex, drawableSelected, drawableBefore)
        }
    }

    private fun setListenerAscentDescription(descriptionLogoButton: ImageView?, descriptionText: TextView?, descriptionIndex: Int, drawableSelected: Int, drawableBefore: Int) {
        descriptionLogoButton?.setOnClickListener{
            selectAscentDescription(descriptionLogoButton, descriptionIndex, descriptionText, drawableSelected, drawableBefore)
        }
        descriptionText?.setOnClickListener{
            selectAscentDescription(descriptionLogoButton, descriptionIndex, descriptionText, drawableSelected, drawableBefore)
        }
    }

    private fun setListeners() {
        setListenerGrade(vbButton,0, R.drawable.ic_vb, R.drawable.ic_vb_before)
        setListenerGrade(v0Button,1, R.drawable.ic_v0, R.drawable.ic_v0_before)
        setListenerGrade(v1Button,2, R.drawable.ic_v1, R.drawable.ic_v1_before)
        setListenerGrade(v2Button,3, R.drawable.ic_v2, R.drawable.ic_v2_before)
        setListenerGrade(v3Button,4, R.drawable.ic_v3, R.drawable.ic_v3_before)
        setListenerGrade(v4Button,5, R.drawable.ic_v4, R.drawable.ic_v4_before)
        setListenerGrade(v5Button,6, R.drawable.ic_v5, R.drawable.ic_v5_before)
        setListenerGrade(v6Button,7, R.drawable.ic_v6, R.drawable.ic_v6_before)
        setListenerGrade(v7Button,8, R.drawable.ic_v7, R.drawable.ic_v7_before)
        setListenerGrade(v8Button,9, R.drawable.ic_v8, R.drawable.ic_v8_before)
        setListenerGrade(v9Button,10, R.drawable.ic_v9, R.drawable.ic_v9_before)
        setListenerGrade(v10Button,11, R.drawable.ic_v10, R.drawable.ic_v10_before)
        setListenerAscentDescription(onsightButton, onsightText, 0, R.drawable.ic_onsight, R.drawable.ic_onsight_before)
        setListenerAscentDescription(flashButton, flashText, 1, R.drawable.ic_flash, R.drawable.ic_flash_before)
        setListenerAscentDescription(attemptsButton, attemptsText, 2, R.drawable.ic_attempts, R.drawable.ic_attempts_before)
        setListenerAscentDescription(repeatButton, repeatText, 3, R.drawable.ic_repeat, R.drawable.ic_repeat_before)
        helpButton?.setOnClickListener{
            var alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            alertBuilder.setTitle("Ascent descriptions")
            alertBuilder.setMessage("onsight: reaching the top on your first attempt by yourself, without any prior tips, information, nor seeing someone else complete it before you.\n\n"
                                +"flash: reaching the top on your first attempt, with some information beforehand (tips, seeing someone else complete it before you, etc).\n\n"
                                +"attempts: reaching the top in more than one attempt.\n\n"
                                +"repeat: ascents you had already completed before some other day.")
            alertBuilder.show()
        }
        saveClimbButton?.setOnClickListener{
            Log.e("TAG", "add climb button clicked")
            if(loggedClimbPoints != 0){     // if both grade and description have been selected
                val returnIntent: Intent = Intent()
                returnIntent.putExtra(
                        "loggedClimb",
                        LoggedClimb(grades[selectedGradeIndex], descriptions[selectedAscentDescriptionIndex], climbNotes?.text.toString(), loggedClimbPoints)
                )
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            } else {
                Toast.makeText(this, "please select both grade and ascent description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectGradeButton(selectedGrade: ImageView?, gradeIndex: Int, selectedDrawable: Int, previousGrade: Int) {
        if(previousGradeDrawableIndex != -1){
            previousGradeButton?.setImageDrawable(ContextCompat.getDrawable(this, previousGradeDrawableIndex))
        }
        selectedGrade?.setImageDrawable(ContextCompat.getDrawable(this, selectedDrawable))
        previousGradeButton = selectedGrade
        previousGradeDrawableIndex = previousGrade
        selectedGradeIndex = gradeIndex
        if(selectedAscentDescriptionIndex != -1){   // if an ascent description has also been selected
            updateClimbPoints()
        }
    }

    private fun selectAscentDescription(selectedAscentDescription: ImageView?, descriptionIndex: Int, selectedAscentDescriptionText: TextView?, selectedDrawable: Int, previousDescription: Int) {
        if(previousDescriptionDrawable != -1) {
            previousDescriptionButton?.setImageDrawable(ContextCompat.getDrawable(this, previousDescriptionDrawable))
            previousDescriptionText?.setTextColor(ContextCompat.getColor(this, R.color.textColorSecondary))
            previousDescriptionText?.setTypeface(null, Typeface.NORMAL)
        }
        selectedAscentDescription?.setImageDrawable(ContextCompat.getDrawable(this, selectedDrawable))
        selectedAscentDescriptionText?.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary))
        selectedAscentDescriptionText?.setTypeface(null, Typeface.BOLD)
        previousDescriptionButton = selectedAscentDescription
        previousDescriptionDrawable = previousDescription
        previousDescriptionText = selectedAscentDescriptionText
        selectedAscentDescriptionIndex = descriptionIndex
        if(selectedGradeIndex != -1){   // if a grade has also been selected
            updateClimbPoints()
        }
    }

    private fun updateClimbPoints(){
        Log.e("TAG", "updateClimbPoints() grade:$selectedGradeIndex  description:$selectedAscentDescriptionIndex")
        val result: Double = gradePoints[selectedGradeIndex]*gradeMultipliers[selectedAscentDescriptionIndex]
        Log.e("TAG", "current points: "+result)
        loggedClimbPoints = (gradePoints[selectedGradeIndex]*gradeMultipliers[selectedAscentDescriptionIndex]).roundToInt()
    }
}
