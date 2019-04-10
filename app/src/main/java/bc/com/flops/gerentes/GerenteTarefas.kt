package bc.com.flops.gerentes

import bc.com.flops.Tarefa

interface GerenteTarefas {

    /**
     * cadastrar Tarefa
     *  Cadastrar a tarefa no sistema, e retorna se a operação foi concuida com sucesso
     *
     * */
    fun cadastrarTarefa(tarefa: Tarefa):Boolean

    /**
     * buscarTarefa
     * Retorna Tarefa caso ela esteja cadastrada,
     * caso a tarefa não esteja cadastrada será retornada
     * uma tarefa em branco
     *
     * */
    fun buscarTarefa(nome:String): Tarefa


    /**
     * Tenta Remover uma tarefa
     * Retorna true se bem sucedido
     * */
    fun removerTarefa(nome:String): Boolean

    /**
     * quant Tarefas
     * Retorna a quantidade de tarefas cadastradas
     * */
    fun quantTarefas(): Int

}