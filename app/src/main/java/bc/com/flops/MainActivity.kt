package bc.com.flops

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        btn_cria_tarefa.setOnClickListener {
            if (fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).isVisible)
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.GONE
            else
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.VISIBLE
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("menssage")

        myRef.setValue("test")
        val reference = database.getReference("/tarefas/usuario/");
        Log.v("FirebaseGerenteTarefas", reference.getParent().toString());

   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basic, menu)
        return true
    }
}


