package bc.com.flops

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        action_btn_cria_tarefa.setOnClickListener {
            if (fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).isVisible)
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.GONE
            else
                fragmentManager.findFragmentById(R.id.quick_cadastro_fragment).view.visibility = View.VISIBLE
        }

        button.setOnClickListener {

            startActivity<Cronometro>("tarefa" to "Programar o Aplicativo")

        }

   }
}


