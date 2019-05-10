package bc.com.flops

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import bc.com.flops.adapters.ListaTarefasAdapter
import bc.com.flops.gerentes.GerenteTarefas
import bc.com.flops.gerentes.GerenteTarefasFirebase
import org.jetbrains.anko.sdk27.coroutines.onItemLongClick
import org.jetbrains.anko.support.v4.startActivity

/**
 * Lista Tarefas Activity
 * Gere a lista tarefas
 * */
class ListaTarefasActivity: Fragment() {

    private val gerenteTarefas:GerenteTarefas = GerenteTarefasFirebase.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_lista_tarefas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v("Iniciando", "Lista tarefas iniciando")
        val adapter = ListaTarefasAdapter(this.context!!)
        val lista = view.findViewById<ExpandableListView>(R.id.lista_tarefas)
        gerenteTarefas.cadastraListener(adapter)

        lista.setAdapter(adapter)

        lista.onItemLongClick { parent, _, position, id ->
            val ev = parent as ExpandableListView
            val pos = ev.getExpandableListPosition(position)

            val itemType = ExpandableListView.getPackedPositionType(pos)
            val groupPos = ExpandableListView.getPackedPositionGroup(pos)
            val childPos = ExpandableListView.getPackedPositionChild(pos)

            if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                val id_tarefa = adapter.getGroup(groupPos).nome
                startActivity<EditaTarefaActivity>("tarefa" to id_tarefa)
            }
        }

    }
}

