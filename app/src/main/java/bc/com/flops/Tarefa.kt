package bc.com.flops

import android.util.Log
import java.util.*


/**
 * Classe Tarefa
 *
 * Esta classe é responsavel pela entidade tarefa
 * Parametros:
 *      id: Valor inteiro, deve ser unico em todo o sistema
 *      nome: nome dado pelo usuario a tarefa
 *      dataCriacao: quando a tarefa foi criada pelo usuario
 *      progresso: o progresso atual da tarefa TODO: este atributo ainda não está pronto
 *      descrição: A descrição do usuario sobre sua tarefa
 *      prioridade:
 * */

data class Tarefa(

    var id:Int,
    var nome:String,
    val dataCriacao: Date,
    var progresso:Int,
    var descricao:String,
    var prioridade:Int

) {
    var tarefaTemporal:TarefaTemporal? = null
    private val tarefas: HashSet<Tarefa> = hashSetOf()

    constructor() : this(0, "", Date(), 0, "", 0)

    fun subTarefas(): Array<Tarefa> {
        return tarefas.toTypedArray()
    }

    fun isTemporal(): Boolean {
        return tarefaTemporal != null
    }

    fun addSubTarefa(tarefa:Tarefa) {
        tarefas.add(tarefa)
    }

    fun removeSubTarefa(id:Int) =
        this.tarefas
            .filter { it.id == id }
            .forEach {
                tarefas.remove(it)
            }

    fun calculaProgresso() {
        Log.v("Tarefa","Tarefa: ${this.nome} Calculando progresso, Progresso atual: ${this.progresso}")
        if (tarefaTemporal != null) {
            Log.v("Tarefa", "Alterando valor de progresso")
            this.progresso = ((tarefaTemporal!!.tempoDecorrido.toFloat() / tarefaTemporal!!.tempoEsperado) * 100).toInt()
        }
        Log.v("Tarefa","Tarefa: ${this.nome} Progresso apos calculo: ${this.progresso}")
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tarefa

        if (nome != other.nome) return false

        return true
    }
}