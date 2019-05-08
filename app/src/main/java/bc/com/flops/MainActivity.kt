package bc.com.flops

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import bc.com.flops.adapters.ListaTarefasAdapter
import bc.com.flops.gerentes.GerenteTarefas
import bc.com.flops.gerentes.GerenteTarefasMemoria
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_lista_tarefas.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var gerenteTarefas: GerenteTarefas
        gerenteTarefas = GerenteTarefasMemoria

        val adapter = ListaTarefasAdapter(this)
        gerenteTarefas.cadastraListener(adapter)
        lista_tarefas!!.setAdapter(adapter)

        action_btn_cria_tarefa.setOnClickListener {
            if (fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).isVisible)
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.GONE
            else
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.VISIBLE
        }

        button.setOnClickListener {

            startActivity<Cronometro>("tarefa" to "Programar o Aplicativo")

        }

   }
}


