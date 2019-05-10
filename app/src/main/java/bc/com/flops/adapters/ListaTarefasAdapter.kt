package bc.com.flops.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import bc.com.flops.Cronometro
import bc.com.flops.R
import bc.com.flops.StateChanged
import bc.com.flops.Tarefa
import bc.com.flops.fabricas.FabricaTarefa
import bc.com.flops.gerentes.GerenteTarefasFirebase
import org.jetbrains.anko.startActivity

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
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(tarefa: Int, last: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if (view == null) view = LayoutInflater.from(ctx).inflate(R.layout.header_lista_tarefas, parent, false)

        val pomodoro = view?.findViewById<ImageButton>(R.id.header_btn_pomodoro)
        if (pomodoro != null) {
            pomodoro.setFocusable(false)
        }

        val titulo = view?.findViewById<TextView>(R.id.header_txt_titulo)
        val progresso = view?.findViewById<TextView>(R.id.header_txt_progresso)
        val done = view?.findViewById<CheckBox>(R.id.header_check)

        pomodoro!!.setOnClickListener {
            ctx.startActivity<Cronometro>("tarefa" to "${titulo!!.text}")
        }

        titulo?.text = tituloListas[tarefa].nome
        progresso?.text = tituloListas[tarefa].progresso.toString() + "%"

        done!!.isChecked = tituloListas[tarefa].progresso == 100 && !done!!.isChecked

        var color = Unit

        val x = listOf<Int>(android.R.attr.state_checked, android.R.attr.state_checked)

        val temporal_pomodoro = view?.findViewById<ImageButton>(R.id.header_btn_pomodoro)
        if( temporal_pomodoro != null)
            if(tituloListas[tarefa].isTemporal()) {
                temporal_pomodoro.visibility = View.VISIBLE
            } else {
                temporal_pomodoro.visibility = View.GONE
            }

        return view!!
    }

    override fun getChildView(tarefa: Int, sub: Int, lastTarefa: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) view = LayoutInflater.from(ctx).inflate(R.layout.item_lista_tarefa, parent, false)

        val edittxt_item = view?.findViewById<TextView>(R.id.item_lista_tarefa_txt_nome)

        val subTarefa = tituloListas[tarefa].tarefas[sub]
        if (subTarefa.nome == "")
            edittxt_item!!.setText("Adicione Sua Tarefa Aqui")
        else
            edittxt_item!!.setText(subTarefa.nome)

        edittxt_item.setOnClickListener {
            showDialog(subTarefa, tituloListas[tarefa])
        }

        GerenteTarefasFirebase.getInstance().alteraTarefa(tituloListas[tarefa].nome, tituloListas[tarefa])

        return view!!
    }

    private fun showDialog(tarefa: Tarefa, tarefa2: Tarefa) {
        val li = LayoutInflater.from(ctx);
        val view = li.inflate(R.layout.input_sub_tarefa, null)
        val builder = AlertDialog.Builder(ctx)
        builder.setView(view)

        val x = view.findViewById<EditText>(R.id.input_edit_tarefa)
        x.setText(tarefa.nome)

            builder.setCancelable(false).setPositiveButton("OK", { dialogInterface, i ->
                tarefa.nome = x.text.toString()
                tarefa2.addSubTarefa(FabricaTarefa.tarefaEmBranco())
            }).setNegativeButton("Cancela", { dialogInterface, i ->
                dialogInterface.cancel()
            })
        val c = builder.create()
        c.show()

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