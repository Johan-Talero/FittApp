package com.fitness.fittapp.UI.IMC

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.fitness.fittapp.Domain.Imc
import com.fitness.fittapp.UI.IMC.Register.RegisterIMCActivity
import com.fitness.fittapp.databinding.ActivityConsultImcactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import kotlin.math.roundToInt

@AndroidEntryPoint
class ConsultIMCActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsultImcactivityBinding
    private val consultIMCViewModel: ConsultIMCViewModel by viewModels()
    private var imcList = mutableListOf<Imc>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultImcactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId = intent.extras?.getString("userId")
        initUI(userId.toString())
    }

    private fun initUI(userId:String) {
        renderGraph(userId)
        initListeners(userId)
    }

    private fun renderGraph(userId: String) {
        lifecycleScope.launch {
            imcList = consultIMCViewModel.getIMCList(userId).toMutableList()
            if(imcList.isNotEmpty()){
                setGraph(imcList)
            }
        }
    }

    private fun setGraph(imc: List<Imc>) {
        val domain = mutableListOf<Number>()
        val weight = mutableListOf<Number>()

        domain.add(0)
        weight.add(0)

        for (element in imc){
            weight.add(element.imcData.toDouble())
        }

        for (i in 1..imc.size) {
            domain.add(i)
        }

        val weightList: Array<Number> = weight.toList().toTypedArray()
        val domainLabels: Array<Number> = domain.toList().toTypedArray()

        val series1: XYSeries = SimpleXYSeries(
            listOf(* weightList), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "IMC Progreso"
        )

        val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, Color.GRAY, null)
        series1Format.interpolationParams = CatmullRomInterpolator.Params(10,
            CatmullRomInterpolator.Type.Centripetal)

        binding.imcGraphic.addSeries(series1, series1Format)

        binding.imcGraphic.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format =
            object : Format() {

                override fun format(
                    obj: Any?,
                    toAppendTo: StringBuffer?,
                    pos: FieldPosition?
                ): StringBuffer {
                    val i = (obj as Number).toFloat().roundToInt()
                    return toAppendTo!!.append(domainLabels[i])
                }

                override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                    return null
                }

            }

    }


    private fun initListeners(userId: String) {
        binding.registerIMC.setOnClickListener {
            val intent = Intent(this, RegisterIMCActivity::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
        }
        binding.viewToolBar.back.setOnClickListener {
            finish()
        }
    }
}