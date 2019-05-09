package bc.com.flops.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.TextView
import bc.com.flops.R
import bc.com.flops.StateChanged
import bc.com.flops.Tarefa

/**
 * Lista Tarefas Adapter
 *
 * Classe responsavel por gerenciar o que está sendo mostrado da view lista_tarefas
 * */

class ListaTarefasAdapter(private val ctx: Context): BaseExpandableListAdapter(), StateChanged<List<Tarefa>> {

    override fun onChange(list: List<Tarefa>) {
        notifyDataSetChanged()
        this.tituloListas = list.toMutableList()

    }

    private var tituloListas = mutableListOf<Tarefa>()

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(tarefa: Int, last: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if (view == null) view = LayoutInflater.from(ctx).inflate(R.layout.header_lista_tarefas, parent, false)

        val titulo = view?.findViewById<TextView>(R.id.header_txt_titulo)
        val progresso = view?.findViewById<TextView>(R.id.header_txt_progresso)
        val done = view?.findViewById<CheckBox>(R.id.header_check)

        titulo?.text = tituloListas[tarefa].nome
        progresso?.text = tituloListas[tarefa].progresso.toString() + "%"

        done!!.isChecked = tituloListas[tarefa].progresso == 100 && !done!!.isChecked

        return view!!
    }

    override fun getChildView(tarefa: Int, sub: Int, lastTarefa: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) view = LayoutInflater.from(ctx).inflate(R.layout.item_lista_tarefa, parent, false)

        val txt_item = view?.findViewById<TextView>(R.id.item_lista_tarefa_txt_nome)

        txt_item?.text = "Funciona"

        return view!!
    }

    fun add(taf: Tarefa): Boolean {
        return tituloListas.add(taf)
    }

    override fun getChildrenCount(p0: Int): Int = this.tituloListas[p0].subTarefas().size

    override fun getChild(posicaoTitulo: Int, posicaoGrupo: Int): Tarefa
            = this.tituloListas[posicaoTitulo].subTarefas()[posicaoGrupo]

    override fun getGroupId(p0: Int): Long = p0.toLong()


    override fun getChildId(p0: Int, p1: Int): Long = p1.toLong()

    override fun getGroupCount(): Int = tituloListas.size

    override fun getGroup(p0: Int): Tarefa = tituloListas[p0]


}