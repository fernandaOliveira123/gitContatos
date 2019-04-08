package br.senai.sp.agendadecontatos.agendadecontatos;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import br.senai.sp.agendadecontatos.R;
import br.senai.sp.agendadecontatos.modelo.Contato;
import br.senai.sp.conversores.Imagem;

public class CadastroContatoHelper {


    private EditText txtNome;
    private EditText txtEndereco;
    private  EditText txtTelefone;
    private EditText txtEmail;
    private  EditText txtLinkedin;
    private ImageView foto;

    private TextInputLayout txtLayoutNome;
    private TextInputLayout txtLayoutEndereco;
    private  TextInputLayout txtLayoutTelefone;
    private TextInputLayout txtLayoutEmail;
    private TextInputLayout txtLayoutLinkedin;

    private Contato contato;

    public CadastroContatoHelper(CadastroContatoActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        foto = activity.findViewById(R.id.imageviewcontato);

        txtLayoutNome = activity.findViewById(R.id.layout_txt_nome);
        txtLayoutEndereco = activity.findViewById(R.id.layout_txt_endereco);
        txtLayoutTelefone = activity.findViewById(R.id.layout_txt_telefone);
        txtLayoutEmail = activity.findViewById(R.id.layout_txt_email);
        txtLayoutLinkedin = activity.findViewById(R.id.layout_txt_linkedin);

        contato = new Contato();
    }

    public Contato getContato() {

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());

        Bitmap bm = ((BitmapDrawable)foto.getDrawable()).getBitmap();
        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bm,300,300,true);


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapReduzido.compress(Bitmap.CompressFormat.PNG, 0,byteArrayOutputStream);
        byte[] fotoArray = byteArrayOutputStream.toByteArray();

        contato.setFoto_contato(fotoArray);


        return contato;
    }

    public void preencherFormulario(Contato contato){
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());

        if(contato.getFoto_contato()!=null){
            foto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto_contato()));
        }

        this.contato = contato;
    }


    public boolean validar(){
        boolean validado = true;
        if (txtNome.getText().toString().isEmpty()){
            txtLayoutNome.setErrorEnabled(true);
            txtLayoutNome.setError("por favor digite o seu nome");
            validado = false;
        }else{
            txtLayoutNome.setErrorEnabled(false);
        }

        if (txtEndereco.getText().toString().isEmpty()){
            txtLayoutEndereco.setErrorEnabled(true);
            txtLayoutEndereco.setError("por favor digite o seu endereço");
            validado = false;
        }else{
            txtLayoutEndereco.setErrorEnabled(false);
        }

        if (txtTelefone.getText().toString().isEmpty()){
            txtLayoutTelefone.setErrorEnabled(true);
            txtLayoutTelefone.setError("por favor digite o seu telefone");
            validado = false;
        }else{
            txtLayoutTelefone.setErrorEnabled(false);
        }

        if (txtEmail.getText().toString().isEmpty()){
            txtLayoutEmail.setErrorEnabled(true);
            txtLayoutEmail.setError("por favor digite o seu email");
            validado = false;
        }else{
            txtLayoutEmail.setErrorEnabled(false);
        }

        if (txtLinkedin.getText().toString().isEmpty()){
            txtLayoutLinkedin.setErrorEnabled(true);
            txtLayoutLinkedin.setError("por favor digite o seu linkedin");
            validado = false;
        }else{
            txtLayoutLinkedin.setErrorEnabled(false);
        }

        return validado;
    }
}
