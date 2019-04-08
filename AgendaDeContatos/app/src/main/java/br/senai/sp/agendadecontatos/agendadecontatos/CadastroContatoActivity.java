package br.senai.sp.agendadecontatos.agendadecontatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.senai.sp.agendadecontatos.BuildConfig;
import br.senai.sp.agendadecontatos.R;
import br.senai.sp.agendadecontatos.dao.ContatoDAO;
import br.senai.sp.agendadecontatos.modelo.Contato;

public class CadastroContatoActivity extends AppCompatActivity {
    public static final int GALERIA_REQUEST = 2000;
    public static final int CAMERA_REQUEST = 666;
    private CadastroContatoHelper helper;
    private Button btnGaleria;
    private Button btnCamera;
    private ImageView imgFoto;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        helper = new CadastroContatoHelper(this);

        btnCamera = findViewById(R.id.btn_camera);
        btnGaleria = findViewById(R.id.btn_galeria);
        imgFoto = findViewById(R.id.imageviewcontato);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroContatoActivity.this, "chamando galeria", Toast.LENGTH_LONG).show();

                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                intentGaleria.setType("image/*");

                startActivityForResult(intentGaleria, GALERIA_REQUEST);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    String nomeArquivo = "IMG_" + System.currentTimeMillis() + ".JPG";

                    caminhoFoto = getExternalFilesDir(null) + nomeArquivo;
                    Log.d("NOME ARQUIVO", nomeArquivo);

                    File arquivoFoto = new File(caminhoFoto);

                    Uri fotoUri = FileProvider.getUriForFile(
                            CadastroContatoActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            arquivoFoto
                    );

                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                    startActivityForResult(intentCamera, CAMERA_REQUEST);
                }
        });

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato !=null){
            helper.preencherFormulario(contato);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            try{

                if (requestCode == GALERIA_REQUEST){
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    imgFoto.setImageBitmap(bitmap);
                }

                if(requestCode == CAMERA_REQUEST){
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    imgFoto.setImageBitmap(bitmapReduzido);
                }

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final  Contato contato = helper.getContato();
        final ContatoDAO dao = new ContatoDAO(this);

        switch (item.getItemId()){
            case R.id.menu_salvar:


                if(helper.validar() == true){
                    if(contato.getId()== 0) {
                    //dao.salvar(contato);
                        dao.salvar(contato);
                        Toast.makeText(this, contato.getNome() + " gravado com sucesso!", Toast.LENGTH_LONG).show();
                    }else{
                        dao.atualizar(contato);
                        Toast.makeText(this, contato.getNome() + " atualizado com sucesso!", Toast.LENGTH_LONG).show();
                    }
                    dao.close();
                    finish();
                }


                break;
            case  R.id.menu_del:

                if(contato.getId() == 0 ){
                    Toast.makeText(this, "não é possivel excluir um contato inexistente!", Toast.LENGTH_LONG).show();
                }else {
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Tem certeza que deseja deletar esse contato?")
                            .setPositiveButton("sim",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dao.excluir(contato);
                                            Toast.makeText(CadastroContatoActivity.this, contato.getNome() + "excluído com sucesso!", Toast.LENGTH_LONG).show();
                                            finish();
                                            dao.close();
                                        }

                                    }).setNegativeButton("não", null).show();
                }
                break;
            case R.id.menu_configuracao:
                Toast.makeText(CadastroContatoActivity.this,
                        "configurações", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(CadastroContatoActivity.this,
                        "nada", Toast.LENGTH_LONG).show();
                break;

        }



        return super.onOptionsItemSelected(item);
    }

}
