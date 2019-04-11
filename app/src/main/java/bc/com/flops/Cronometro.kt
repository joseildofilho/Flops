package bc.com.flops

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cronometro.*
import kotlinx.android.synthetic.main.content_cronometro.*

/**
 * Clsse Cronometro
 *
 * Responsavel pela View do cronometro e tambem por contar o tempo
 *
 * */

class Cronometro : AppCompatActivity() {

    private var contando = false
    private var tempoPausa = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupBotoes()

        txt_tarefa_cronometro.text = intent.getStringExtra("tarefa")
    }

    fun setupBotoes() {
        btn_cronometro.setOnClickListener {
            contando = if (contando) {
                tempoPausa = cronometro.base - SystemClock.elapsedRealtime()
                cronometro.stop()
                false
            } else {
                cronometro.base = SystemClock.elapsedRealtime() + tempoPausa
                cronometro.start()
                true
            }
        }
    }

}
