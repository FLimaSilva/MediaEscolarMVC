package android.curso.mediaescolarmvc.view;

import android.content.Intent;
import android.curso.mediaescolarmvc.R;
import android.curso.mediaescolarmvc.controller.MediaEscolarController;
import android.curso.mediaescolarmvc.datasource.DataSource;
import android.curso.mediaescolarmvc.model.MediaEscolar;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MediaEscolar mediaEscolar = new MediaEscolar();

        //mediaEscolar.setMateria("Matemática");
        //mediaEscolar.setBimestre("1° Bimestre");
        //mediaEscolar.setSituacao("Reprovado");
        //mediaEscolar.setNotaProva(5);
        //mediaEscolar.setNotaMateria(4);
        //mediaEscolar.setMediaFinal(3);

        MediaEscolarController mediaEscolarController = new MediaEscolarController(getBaseContext());

        //mediaEscolarController.salvar(mediaEscolar);
        //mediaEscolar.setId(2);
        //mediaEscolarController.alterar(mediaEscolar);

        //mediaEscolar.setId(1);
        //mediaEscolarController.deletar(mediaEscolar);
        //mediaEscolar.setId(4);
        //mediaEscolarController.deletar(mediaEscolar);

        //mediaEscolarController.backupBancoDeDados();
        List<MediaEscolar> objetos = mediaEscolarController.listar();

        for (MediaEscolar obj:objetos) {
            Log.i("CRUD LISTAR", "ID: " + obj.getId() + " - Matéria: " + obj.getMateria() + " - Situação: " + obj.getSituacao());
        }

        apresentarTelaSplash();

    }


    private void apresentarTelaSplash(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                DataSource ds = new DataSource(getApplicationContext());

                Intent telaPrincipal
                        = new Intent(SplashActivity.this,
                        MainActivity.class);

                startActivity(telaPrincipal);

                finish();

            }
        },SPLASH_TIME_OUT);


    }
}
