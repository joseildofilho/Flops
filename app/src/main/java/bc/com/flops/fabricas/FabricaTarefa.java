package bc.com.flops.fabricas;

import bc.com.flops.Tarefa;
import bc.com.flops.TarefaTemporal;

import java.util.Date;


/**
 * Classe que implementa o padrão factory
 * Cria Classes tarefa
 * */
public class FabricaTarefa {

    public FabricaTarefa() throws Exception {
        throw new Exception("Não instancie está classe");
    }

    public static Tarefa tarefaEmBranco() {
        return new Tarefa();
    }

    public static TarefaTemporal tarefaTemporalEmBranco() {
        return new TarefaTemporal(new Date(), 0L,0L);
    }

    public static Tarefa tarefaEmBrancoComSubTarefa() {
        Tarefa t = new Tarefa();
        t.addSubTarefa(new Tarefa());
        return t;
    }
}
