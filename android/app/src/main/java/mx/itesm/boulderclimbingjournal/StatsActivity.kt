package mx.itesm.boulderclimbingjournal
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class StatsActivity : AppCompatActivity() {

    private var pointsChart: LineChart? = null
    private var climbsChart: BarChart? = null
    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        setReferences()

        var loggedSessions: ArrayList<LoggedSession> = dbHelper.getAllLoggedSessions()
        setPointsChart(loggedSessions)
        setClimbsChart(loggedSessions)
    }

    private fun setReferences(){
        pointsChart = findViewById(R.id.pointsChart)
        climbsChart = findViewById(R.id.climbsChart)
    }
    
    private fun setPointsChart(loggedSessions: ArrayList<LoggedSession>){
        var sessionCounter = 0f
        var pointsLine: ArrayList<Entry> = arrayListOf()
        var xLabels: ArrayList<String> = arrayListOf()
        for(session: LoggedSession in loggedSessions){
            pointsLine.add(Entry(sessionCounter, session.points.toFloat()))
            xLabels.add(session.date.toString())
            sessionCounter++
        }

        var xAxis: XAxis? = pointsChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xLabels)
        pointsChart?.axisRight?.isEnabled = false

        var pointsDataset: LineDataSet = LineDataSet(pointsLine, "points")
        pointsDataset.valueTextSize = 10f
        pointsDataset.lineWidth = 3f
        pointsDataset.setDrawCircles(false)
        pointsDataset.setValueTextColor(ContextCompat.getColor(this, R.color.textColorPrimary))
        pointsDataset.valueFormatter = object : ValueFormatter() {
            override fun getPointLabel(entry: Entry?): String { return entry?.y?.toInt().toString() }
        }
        pointsDataset.setColor(ContextCompat.getColor(this, R.color.colorPrimary))

        pointsChart?.isDragEnabled()
        pointsChart?.setScaleEnabled(false)
        pointsChart?.data = LineData(pointsDataset)
        //lineChart?.invalidate()
        pointsChart?.animateX(500)
    }

    private fun setClimbsChart(loggedSessions: ArrayList<LoggedSession>){
        var xLabels: ArrayList<String> = arrayListOf()
        var sessionCounter = 0f
        var values: ArrayList<BarEntry> = arrayListOf()
        for(session: LoggedSession in loggedSessions){
            values.add(BarEntry(sessionCounter, floatArrayOf(
                    session.onsightClimbs.toFloat(),
                    session.flashClimbs.toFloat(),
                    session.attemptsClimbs.toFloat(),
                    session.repeatClimbs.toFloat()
            )))
            xLabels.add(session.date.toString())
            sessionCounter++
        }

        var xAxis: XAxis? = climbsChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xLabels)
        climbsChart?.axisRight?.isEnabled = false
        var legend = climbsChart?.legend
        var legenedEntries = arrayListOf<LegendEntry>()
        var climbsColors = intArrayOf(
                ContextCompat.getColor(this, R.color.colorMaterialBlue),
                ContextCompat.getColor(this, R.color.colorMaterialLightGreen),
                ContextCompat.getColor(this, R.color.colorMaterialAmber),
                ContextCompat.getColor(this, R.color.colorDeepPurpleLight)
        )
        legend?.setCustom(arrayOf(
                LegendEntry("Onsight", Legend.LegendForm.SQUARE, 8f, 8f, null, climbsColors[0]),
                LegendEntry("Flash", Legend.LegendForm.SQUARE, 8f, 8f, null, climbsColors[1]),
                LegendEntry("Attempts", Legend.LegendForm.SQUARE, 8f, 8f, null, climbsColors[2]),
                LegendEntry("Repeat", Legend.LegendForm.SQUARE, 8f, 8f, null, climbsColors[3])
        ))

        var climbsDataset: BarDataSet = BarDataSet(values, "ascent descriptions")
        climbsDataset.setColors(*climbsColors)
        var climbsBarData = BarData(climbsDataset)
        climbsChart?.data = climbsBarData
        climbsChart?.isDragEnabled()
        climbsChart?.setScaleEnabled(false)
        climbsChart?.animateY(500)
    }
}