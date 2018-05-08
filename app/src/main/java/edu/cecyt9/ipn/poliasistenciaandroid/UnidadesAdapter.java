package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by Caleb on 07/05/2018.
 */

public class UnidadesAdapter extends BaseAdapter {

    private Context contexto;
    private ArrayList<Unidad> unidades;

    public UnidadesAdapter(Context contexto, ArrayList<Unidad> unidades) {
        this.contexto = contexto;
        this.unidades = unidades;
    }

    @Override
    public int getCount() {
        return unidades.size();
    }

    @Override
    public Object getItem(int i) {
        return unidades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String obtenerId(int i){
        return  unidades.get(i).getId();
    }

    public String obtenerGrupo(int i){
        return unidades.get(i).getGrupo();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TwoLineListItem twoLineListItem;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) view;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(unidades.get(i).getUnidad());
        text2.setText("" + unidades.get(i).getGrupo());

        return twoLineListItem;
    }
}
