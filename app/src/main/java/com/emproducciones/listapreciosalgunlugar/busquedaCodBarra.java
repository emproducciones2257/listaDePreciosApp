package com.emproducciones.listapreciosalgunlugar;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;

    public static busquedaCodBarra newInstance() {
        return new busquedaCodBarra();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.busqueda_cod_barra_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BusquedaCodBarraViewModel.class);
        // TODO: Use the ViewModel
    }

}
