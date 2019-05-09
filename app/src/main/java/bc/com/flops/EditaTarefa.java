package bc.com.flops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import bc.com.flops.gerentes.GerenteTarefas;
import bc.com.flops.gerentes.GerenteTarefasFirebase;

public class EditaTarefa extends AppCompatActivity {

    private GerenteTarefas gt = GerenteTarefasFirebase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_tarefa);

        String tarefa_nome = getIntent().getExtras().getString("tarefa");

        Tarefa tarefa = gt.buscarTarefa(tarefa_nome);

        EditText edit_tarefa_nome = this.findViewById(R.id.txt_edit_tarefa_nome);
        edit_tarefa_nome.setText(tarefa.getNome());
    }
}
