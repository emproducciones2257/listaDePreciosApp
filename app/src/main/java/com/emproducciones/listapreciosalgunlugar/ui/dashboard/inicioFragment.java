package com.emproducciones.listapreciosalgunlugar.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.emproducciones.listapreciosalgunlugar.R;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;

public class inicioFragment extends Fragment {

    private vMProducto vMPro;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        iniVistas(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerPorcen();
    }

    private void obtenerPorcen (){
        vMPro.getPorcentaje().observe(getActivity(), porcen ->{
            if(porcen!=0){
                btn.setEnabled(true);
                btn.setText(getResources().getText(R.string.consultar_precio));
                evtnBtn();
            }
        });
    }
    private void evtnBtn() {
        btn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.navigation_home);
       });
    }

    private void iniVistas(View view) {
        btn=view.findViewById(R.id.btnScan);
        vMPro = new vMProducto();
    }
}
