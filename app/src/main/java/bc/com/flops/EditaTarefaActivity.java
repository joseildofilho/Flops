package bc.com.flops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import bc.com.flops.fabricas.FabricaTarefa;
import bc.com.flops.gerentes.GerenteTarefas;
import bc.com.flops.gerentes.GerenteTarefasFirebase;

import java.util.concurrent.TimeUnit;

public class EditaTarefaActivity extends AppCompatActivity {

    private GerenteTarefas gt = GerenteTarefasFirebase.getInstance();
    private EditText edit_tarefa_nome;
    private EditText descricao;
    private SeekBar prioridade;

    private TimePicker timepicker_cadastra_tempo;

    private Button btn_salvar;

    private Button btn_delete;

    private ListView lst_subTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_tarefa);

        edit_tarefa_nome = this.findViewById(R.id.txt_edit_tarefa_nome);
        descricao = this.findViewById(R.id.edit_txt_descricao);
        prioridade = this.findViewById(R.id.edit_seek_prioridade);

        timepicker_cadastra_tempo = this.findViewById(R.id.edit_timePicker);
        btn_salvar = this.findViewById(R.id.edit_btn_salvar);
        btn_delete = this.findViewById(R.id.edit_btn_delete);

        lst_subTarefas = this.findViewById(R.id.edit_list_tarefas);

        timepicker_cadastra_tempo.setIs24HourView(true);

        String tarefa_nome = getIntent().getExtras().getString("tarefa");

        final Tarefa tarefa = gt.buscarTarefa(tarefa_nome);

        edit_tarefa_nome.setText(tarefa.getNome());
        descricao.setText(tarefa.getDescricao());
        prioridade.setProgress(tarefa.getPrioridade());

        btn_config_cadastro(tarefa);
        btn_config_salvar(tarefa);
        btn_config_delete(tarefa);

    }

    private void btn_config_delete(final Tarefa tarefa) {
        btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.removerTarefa(tarefa.getNome());
                finish();
            }
        });
    }

    private void btn_config_salvar(final Tarefa tarefa) {
        btn_salvar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = tarefa.getNome();

                tarefa.setNome(edit_tarefa_nome.getText().toString());
                tarefa.setDescricao(descricao.getText().toString());
                tarefa.setPrioridade(prioridade.getProgress());

                gt.alteraTarefa(nome, tarefa);
            }
        });

    }

    public void btn_config_cadastro(final Tarefa tarefa) {
        timepicker_cadastra_tempo.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                long x = TimeUnit.HOURS.toMillis(i) + TimeUnit.MINUTES.toMillis(i1);
                if (x > 1000) {
                    TarefaTemporal tt = FabricaTarefa.tarefaTemporalEmBranco();
                    tt.setTempoEsperado(x);
                    tarefa.setTarefaTemporal(tt);
                    tarefa.calculaProgresso();
                } else {
                    Toast
                            .makeText(getApplicationContext(), "Coloque um tempo razoavel", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
    }

}

