package bc.com.flops

import bc.com.flops.fabricas.FabricaTarefa
import bc.com.flops.gerentes.GerenteTarefas
import bc.com.flops.gerentes.GerenteTarefasFirebase
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

class GerenteTarefasFirebaseTest {

    private lateinit var gerenteTarefas: GerenteTarefas
    private lateinit var tarefa:Tarefa
    private lateinit var tarefa2:Tarefa
    @Before
    fun setUp() {
        gerenteTarefas = GerenteTarefasFirebase.getInstance()

        tarefa = FabricaTarefa.tarefaEmBranco()
        tarefa.nome = "Test1"

        tarefa2 = FabricaTarefa.tarefaEmBranco()
        tarefa2.nome = "Test2"
    }

    @Test
    fun cadastraUmaTarefa() {
        assertTrue(gerenteTarefas.cadastrarTarefa(tarefa))
    }

    @Test
    fun cadastrarDuasTarefasRepetidas() {
        assertTrue(gerenteTarefas.cadastrarTarefa(tarefa))
        assertFalse(gerenteTarefas.cadastrarTarefa(tarefa))
    }

    @Test
    fun cadastrarDuasTarefasDiferentes() {
        assertTrue(gerenteTarefas.cadastrarTarefa(tarefa))
        assertTrue(gerenteTarefas.cadastrarTarefa(tarefa2))
    }

    @Test
    fun buscarUmaTarefa() {
        gerenteTarefas.cadastrarTarefa(tarefa)

        assertEquals(gerenteTarefas.buscarTarefa(tarefa.nome), tarefa)
    }

    @Test
    fun buscarDuasTarefas() {
        gerenteTarefas.cadastrarTarefa(tarefa)
        gerenteTarefas.cadastrarTarefa(tarefa2)

        assertEquals(gerenteTarefas.buscarTarefa(tarefa.nome), tarefa)
        assertEquals(gerenteTarefas.buscarTarefa(tarefa2.nome), tarefa2)
    }

    @Test
    fun removeTarefa() {
        gerenteTarefas.cadastrarTarefa(tarefa)

        assertTrue(gerenteTarefas.removerTarefa(tarefa.nome))
    }

    @Test
    fun removeTarefaRemovida() {
        gerenteTarefas.cadastrarTarefa(tarefa)

        assertTrue(gerenteTarefas.removerTarefa(tarefa.nome))
        assertFalse(gerenteTarefas.removerTarefa(tarefa.nome))
    }

    @Test
    fun removeTarefaDeGerenteVazio() {
        assertFalse(gerenteTarefas.removerTarefa(tarefa.nome))
    }

    @Test
    fun quantidadeTarefas() {
        gerenteTarefas.cadastrarTarefa(tarefa)

        assertEquals(gerenteTarefas.quantTarefas(), 1)

        gerenteTarefas.cadastrarTarefa(tarefa)

        assertEquals(gerenteTarefas.quantTarefas(), 1)

        gerenteTarefas.cadastrarTarefa(tarefa2)

        assertEquals(gerenteTarefas.quantTarefas(), 2)

        gerenteTarefas.cadastrarTarefa(tarefa2)

        assertEquals(gerenteTarefas.quantTarefas(), 2)
    }
}