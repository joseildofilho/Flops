package bc.com.flops.gerentes

import android.util.Log
import bc.com.flops.StateChanged
import bc.com.flops.Tarefa
import bc.com.flops.fabricas.FabricaTarefa


object GerenteTarefasMemoria:GerenteTarefas {

    private val listaOrelhas = mutableListOf<StateChanged<Tarefa>>()
    private val setDeTarefas = hashMapOf<String, Tarefa>()

    override fun quantTarefas(): Int = setDeTarefas.count()

    override fun buscarTarefa(nome: String): Tarefa {
        if (setDeTarefas[nome] != null) {
            return setDeTarefas[nome]!!
        }
        return FabricaTarefa.tarefaEmBranco()
    }

    override fun removerTarefa(nome: String): Boolean {
        if (setDeTarefas[nome] != null) {
            setDeTarefas.remove(nome)
            notify()
            Log.v("GerenteTarefas", "notificando")
            return true
        }
        return false
    }

    override fun cadastrarTarefa(tarefa: Tarefa): Boolean {
        if (setDeTarefas[tarefa.nome] != tarefa) {
            setDeTarefas[tarefa.nome] = tarefa
            notify()
            Log.v("GerenteTarefas", "notificando")
            return true
        }
        return false
    }

    override fun cadastraListener(inter: StateChanged<Tarefa>) {
        listaOrelhas.add(inter)
    }

    internal fun notify() {
        this.listaOrelhas.forEach {it.onChange(setDeTarefas.values.toList())}
    }

}

