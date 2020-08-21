package com.emproducciones.listapreciosalgunlugar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;

public class inicioFragment extends Fragment {

    private vMProducto vMPro;
    static int porcentaje=0;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        btn=view.findViewById(R.id.btnScan);
        vMPro = new vMProducto();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerPorcentaje();
    }

    private void obtenerPorcentaje(){
        vMPro.getPorcentaje().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer porcen) {
                porcentaje = porcen;
                if(porcentaje!=0){
                    btn.setEnabled(true);
                    btn.setText(getResources().getText(R.string.consultar_precio));
                    evtnBtn();
                }
            }
        });
    }

    private void evtnBtn() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        });
    }
}