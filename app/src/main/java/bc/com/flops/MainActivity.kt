package bc.com.flops

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        btn_cria_tarefa.setOnClickListener {
            if (fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).isVisible)
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.GONE
            else
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.VISIBLE
        }

        btn_preferencias.setOnClickListener {
            startActivity<LoginActivity>()
        }

        btn_preferencias.setOnLongClickListener {
            FirebaseAuth.getInstance().signOut()
            toast("Deslogado")
            true
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("menssage")

        myRef.setValue("test")
        val reference = database.getReference("/tarefas/usuario/");
        Log.v("FirebaseGerenteTarefas", reference.getParent().toString());

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basic, menu)
        return true
    }
}


