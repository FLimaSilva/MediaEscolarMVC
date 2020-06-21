package android.curso.mediaescolarmvc.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.curso.mediaescolarmvc.datamodel.MediaEscolarDataModel;
import android.curso.mediaescolarmvc.model.MediaEscolar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class DataSource extends SQLiteOpenHelper{

    private static final String DB_NAME = "media_escolar.sqlite";
    private static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;


    public DataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(MediaEscolarDataModel.criarTabela());
        } catch (Exception e){
            Log.e("Media","DB---> ERRO: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String tabela, ContentValues dados){

        boolean sucesso = true;

        try {
            sucesso = db.insert(tabela,null,dados) > 0;
        } catch (Exception e){
            sucesso = false;
        }

        return sucesso;
    }

    public boolean deletar(String tabela, int id){
        boolean sucesso = true;

        sucesso = db.delete(tabela,"id=?",
                new String[]{Integer.toString(id)}) > 0;

        return sucesso;
    }

    public boolean alterar(String tabela, ContentValues dados){
        boolean sucesso = true;

        int id = dados.getAsInteger("id");

        sucesso = db.update(tabela, dados,"id=?",
                new String[]{Integer.toString(id)}) > 0;

        return sucesso;
    }

    public List<MediaEscolar> getAllMediaEscolar(){

        MediaEscolar obj;

        List<MediaEscolar> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + MediaEscolarDataModel.getTABELA() + " ORDER BY materia";

        cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){

            do {

                obj = new MediaEscolar();

                obj.setId(cursor.getInt(cursor.getColumnIndex(MediaEscolarDataModel.getId())));
                obj.setMateria(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getMateria())));
                obj.setSituacao(cursor.getString(cursor.getColumnIndex(MediaEscolarDataModel.getSituacao())));

                lista.add(obj);
            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;

    }

    public void backupBancoDeDados (){
        //Log.i("Escrita","Arquivo criado com sucesso!!1");

        File sd; //Caminho de destino do bd - Download
        File data; //Caminho de origem - data/data/pacote/db_name

        File arquivoBancoDeDados; //Nome do banco de dados
        File arquivoBackupBancoDeDados; //Nome do arquivo de backup

        FileChannel origem; // Leitura do arquivo original
        FileChannel destino; // Gravação do arquivo de destino com backup

        try {
            //Log.i("Escrita","Arquivo criado com sucesso!!2");

            sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            data = Environment.getDataDirectory();

            //Log.v("DB","SD - " + sd.getAbsolutePath());
            //Log.v("DB","DATA - " + data.getAbsolutePath());

            if (sd.canWrite()){
                //Log.i("Escrita","Arquivo criado com sucesso!!3");
                String nomeDoBancoDeDados =
                        "//data//android.curso.mediaescolarmvc//databases/" + DB_NAME;

                String nomeDoArquivoDeBackup =
                        "bkp_" + DB_NAME;

                arquivoBancoDeDados = new File(data,nomeDoBancoDeDados);
                arquivoBackupBancoDeDados = new File(sd,nomeDoArquivoDeBackup);

                if (arquivoBancoDeDados.exists()){

                    origem = new FileInputStream(arquivoBancoDeDados).getChannel();
                    destino = new FileOutputStream(arquivoBackupBancoDeDados).getChannel();

                    destino.transferFrom(origem,0,origem.size());
                    origem.close();
                    destino.close();
                    //Log.i("Escrita", "Arquivo criado com sucesso!!4");
                    //if (arquivoBackupBancoDeDados.exists()) {
                        //Log.i("Escrita", "Arquivo criado com sucesso!!5");
                    //}
                }
            }


        } catch (Exception e){
            Log.e("DB","Erro: " + e.getMessage());
        }

    }
}
