package com.emproducciones.listapreciosalgunlugar;

import android.content.Context;
import android.view.*;
import android.widget.*;
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
        holder.cantidadEscaneado.setText(""+position);
        holder.txtNombreEscaneado.setText(dtosNube.get(position).getProducto().getDtosExtras().substring(0,20));
        holder.txtPrecioEscaneado.setText("$ "+dtosNube.get(position).getPrecio().getPrecio());
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
        ImageView imgSumar,imgRestar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreEscaneado = itemView.findViewById(R.id.txtNombreEscaneado);
            cantidadEscaneado = itemView.findViewById(R.id.cantidadEscaneado);
            txtPrecioEscaneado = itemView.findViewById(R.id.txtPrecioEscaneado);
            imgSumar = itemView.findViewById(R.id.imgSumar);
            imgRestar = itemView.findViewById(R.id.imgRestar);

        }
    }
}
