package bc.com.flops.fabricas;

import bc.com.flops.Tarefa;

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
        return new Tarefa(0, "", new Date(), 0, "", 0);
    }
}
