package bc.com.flops

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import bc.com.flops.fabricas.FabricaTarefa
import bc.com.flops.gerentes.GerenteTarefasFirebase
import kotlinx.android.synthetic.main.activity_cronometro.*
import kotlinx.android.synthetic.main.content_cronometro.*

/**
 * Clsse Cronometro
 *
 * Responsavel pela View do cronometro e tambem por contar o tempo
 *
 * */

class Cronometro : AppCompatActivity(),SensorEventListener  {

    private var contando = false
    private var byButton = false
    private var tempoPausa = 0L
    private val threshold = 7
    private val gerente = GerenteTarefasFirebase.getInstance()
    private var tarefa = FabricaTarefa.tarefaEmBranco()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupBotoes()
        configurarSensorGravidade()
        val tarefa_nome = intent.getStringExtra("tarefa")
        txt_tarefa_cronometro.text = tarefa_nome

        tarefa = gerente.buscarTarefa(tarefa_nome)
        tempoPausa = -tarefa.tarefaTemporal!!.tempoDecorrido
        Log.v("Cronometro", "Tempo de pausa: $tempoPausa")
        cronometro.base = SystemClock.elapsedRealtime() + tempoPausa

    }

    fun setupBotoes() {
        btn_cronometro.setOnClickListener {
            toggleCronometro()
            byButton = !byButton
       }
    }

    private fun toggleCronometro() {
        contando = if (contando) {
            tempoPausa = cronometro.base - SystemClock.elapsedRealtime()
            cronometro.stop()
            tarefa.tarefaTemporal!!.tempoDecorrido = -tempoPausa
            tarefa.calculaProgresso()
            gerente.alteraTarefa(tarefa.nome, tarefa)
            false
        } else {
            cronometro.base = SystemClock.elapsedRealtime() + tempoPausa
            cronometro.start()
            true
        }
    }

    private fun configurarSensorGravidade() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!byButton)
        if ((event!!.values[2] > this.threshold) && this.contando) {
            toggleCronometro()
        }
        else if ((event.values[2] < -this.threshold) && !this.contando) {
            toggleCronometro()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
