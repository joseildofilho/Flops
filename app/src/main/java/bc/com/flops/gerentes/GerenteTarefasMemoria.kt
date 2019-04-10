package bc.com.flops.gerentes

import bc.com.flops.Tarefa
import bc.com.flops.fabricas.FabricaTarefa


class GerenteTarefasMemoria:GerenteTarefas {

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
            return true
        }
        return false
    }

    override fun cadastrarTarefa(tarefa: Tarefa): Boolean {
        if (setDeTarefas[tarefa.nome] != tarefa) {
            setDeTarefas[tarefa.nome] = tarefa
            return true
        }
        return false
    }

}