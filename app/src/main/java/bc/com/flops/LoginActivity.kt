package bc.com.flops

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            toast("Voce já está logado")
            finish()
        }

        login_anonimo.setOnClickListener {
            auth.signInAnonymously().addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    toast("parabens voce está logado")
                    finish()
                } else {
                    toast("Algo de errado aconteceu tente mais tarde")
                }
            }
        }

        login_btn_email.setOnClickListener {
            val email = login_email.text.toString()
            val senha = login_senha.text.toString()
            if (senha.length < 6)
                toast("Tamanho da senha no minimo 6")
            else
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
                task ->
                if(task.isSuccessful) {
                    toast("Conta Criado Com Sucesso")
                } else {
                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                        task ->
                        if (task.isSuccessful) {
                            toast("Logado Com Sucesso")
                            finish()
                        } else if(task.isCanceled) {
                            toast("Error, por favor tente mais tarde")
                        }
                    }
                }
            }
        }
    }
}
