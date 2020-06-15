package com.emproducciones.listapreciosalgunlugar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.emproducciones.listapreciosalgunlugar.model.proPreCloud;
import java.util.ArrayList;


public class BusquedaCodBarraViewModel extends RecyclerView.Adapter<BusquedaCodBarraViewModel.ViewHolder> {

    private Context ctx;
    private ArrayList<proPreCloud> dtosNube;

    public BusquedaCodBarraViewModel(Context ctx, ArrayList<proPreCloud> dtosNube) {
        this.ctx = ctx;
        this.dtosNube = dtosNube;
    }

    @NonNull
    @Override
    public BusquedaCodBarraViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.mostrar_vtas,parent,false);

        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull BusquedaCodBarraViewModel.ViewHolder holder, int position) {
        holder.txtNombreEscaneado.setText(dtosNube.get(position).getProducto().getDtosExtras());
    }

    @Override
    public int getItemCount() {
        if (dtosNube==null)
        return 0;
        else return dtosNube.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreEscaneado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreEscaneado = itemView.findViewById(R.id.txtNombreEscaneado);
        }
    }
}
