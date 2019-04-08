package br.senai.sp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.agendadecontatos.R;
import br.senai.sp.agendadecontatos.modelo.Contato;
import br.senai.sp.conversores.Imagem;

public class ContatosAdapter extends BaseAdapter{

    private List<Contato>contatos;
    private Context context;

    public  ContatosAdapter(Context context, List<Contato>contatos){
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Contato contato = contatos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_lista_contatos, null);

        TextView txtNome = view.findViewById(R.id.txt_nome_list);
        txtNome.setText(contato.getNome());

        TextView txtTelefone = view.findViewById(R.id.txt_telefone_list);
        txtTelefone.setText(contato.getTelefone());

        ImageView foto = view.findViewById(R.id.image_contato);

        if (contato.getFoto_contato() != null) {
            foto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto_contato()));
        }


        return view;
    }
}
