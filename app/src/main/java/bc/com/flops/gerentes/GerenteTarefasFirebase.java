package bc.com.flops.gerentes;

import android.support.annotation.NonNull;
import android.util.Log;
import bc.com.flops.StateChanged;
import bc.com.flops.Tarefa;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenteTarefasFirebase implements GerenteTarefas {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static final GerenteTarefasFirebase instance = new GerenteTarefasFirebase();
    private Map<String, Tarefa> tarefas = new HashMap<>();
    private List<StateChanged> ouvidos = new ArrayList<>();

    private GerenteTarefasFirebase() {
        DatabaseReference reference = db.getReference("/tarefas/usuario/");
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Tarefa> map = new HashMap<>();
                for(DataSnapshot x: dataSnapshot.getChildren()) {
                    Tarefa y = x.getValue(Tarefa.class);
                    map.put(y.getNome(), y);
                }
                GerenteTarefasFirebase.instance.tarefas = map;
                GerenteTarefasFirebase.instance.notifyOuvidos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(vel);
    }

    public static GerenteTarefasFirebase getInstance() {
        return GerenteTarefasFirebase.instance;
    }

    @Override
    public boolean cadastrarTarefa(@NotNull Tarefa tarefa) {
        if (!tarefas.containsKey(tarefa.getNome())) {

            tarefas.put(tarefa.getNome(), tarefa);

            DatabaseReference reference = db.getReference("/tarefas/usuario/");
            reference.setValue(tarefas);
            notifyOuvidos();
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public Tarefa buscarTarefa(@NotNull String nome) {
        return tarefas.get(nome);
    }

    @Override
    public boolean removerTarefa(@NotNull String nome) {
        if(tarefas.containsKey(nome)) {
            tarefas.remove(nome);
            DatabaseReference reference = db.getReference("/tarefas/usuario/");
            reference.setValue(tarefas);
            notifyOuvidos();
            return true;
        }
        return false;
    }

    @Override
    public int quantTarefas() {
        return 0;
    }

    @Override
    public void cadastraListener(@NotNull StateChanged<List<Tarefa>> inter) {
        ouvidos.add(inter);
    }

    private void notifyOuvidos() {
        for(StateChanged ouvido: ouvidos) {
            ouvido.onChange(new ArrayList<>(tarefas.values()));
        }
    }

    @Override
    public boolean alteraTarefa(@NotNull String nome, @NotNull Tarefa tarefa) {
        if (tarefas.containsKey(nome)) {
            Log.v("Alterando Tarefa", "Nome Antigo:" + nome + " Nome Novo: " + tarefa.getNome());
            DatabaseReference reference = db.getReference("/tarefas/usuario/");
            tarefas.remove(nome);
            reference.setValue(tarefas);
            tarefas.put(tarefa.getNome(), tarefa);
            reference.setValue(tarefas);
            notifyOuvidos();
            return true;
        }
        return false;
    }
}
