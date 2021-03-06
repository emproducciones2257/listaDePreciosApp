package com.emproducciones.listapreciosalgunlugar.ui.dashboard.UIBusquedaEscaneo.UI;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.emproducciones.listapreciosalgunlugar.R;
import com.emproducciones.listapreciosalgunlugar.ui.dashboard.UIBusquedaEscaneo.Evtn.evtnClickItemLista;
import com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.proPreCloud;
import java.util.ArrayList;


public class BusquedaCodBarraViewModel extends RecyclerView.Adapter<BusquedaCodBarraViewModel.ViewHolder> {

    private Context ctx;
    private ArrayList<proPreCloud> dtosNube;
    private evtnClickItemLista evtnClik;

    public BusquedaCodBarraViewModel(Context ctx, ArrayList<proPreCloud> dtosNube, evtnClickItemLista evtnClik) {
        this.ctx = ctx;
        this.dtosNube = dtosNube;
        this.evtnClik = evtnClik;
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

        holder.cantidadEscaneado.setText(""+dtosNube.get(position).getCantidad());

        if(dtosNube.get(position).getProducto().getDtosExtras().length()<21){
            holder.txtNombreEscaneado.setText(dtosNube.get(position).getProducto().getDtosExtras());
        }else holder.txtNombreEscaneado.setText(dtosNube.get(position).getProducto().getDtosExtras().substring(0,20));

        holder.txtPrecioEscaneado.setText("$ "+ (dtosNube.get(position).getPrecio().getPrecio())*dtosNube.get(position).getCantidad());

        holder.itemView.setOnClickListener(view -> evtnClik.eventoItemClick(dtosNube.get(position).getProducto().getDtosExtras()));
    }

    @Override
    public int getItemCount() {
        if (dtosNube==null)
        return 0;
        else return dtosNube.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreEscaneado;
        TextView cantidadEscaneado;
        TextView txtPrecioEscaneado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreEscaneado = itemView.findViewById(R.id.txtNombreEscaneado);
            cantidadEscaneado = itemView.findViewById(R.id.cantidadEscaneado);
            txtPrecioEscaneado = itemView.findViewById(R.id.txtPrecioEscaneado);
        }
    }
}
