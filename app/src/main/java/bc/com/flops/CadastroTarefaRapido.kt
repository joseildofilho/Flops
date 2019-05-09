package bc.com.flops

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bc.com.flops.fabricas.FabricaTarefa
import bc.com.flops.gerentes.GerenteTarefas
import bc.com.flops.gerentes.GerenteTarefasFirebase
import kotlinx.android.synthetic.main.activity_cadastro_tarefa_rapido.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 *
 * */

class CadastroTarefaRapido : Fragment() {

    private val gerenteTarefas:GerenteTarefas = GerenteTarefasFirebase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("Iniciando", "cadastroTarefaRapido")


   }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_cadastro_tarefa_rapido, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_cadastra_rapido.onClick {
            toast(txt_tarefa_rapida.text)
            val tarefa = FabricaTarefa.tarefaEmBranco()
            tarefa.nome = txt_tarefa_rapida.text.toString()
            gerenteTarefas.cadastrarTarefa(tarefa)
            Log.v("Gerente", "cadastrando")
        }

    }
}
