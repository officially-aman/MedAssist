package com.example.medassist.ui.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.medassist.R;
import com.example.medassist.data.Medicine;
import com.example.medassist.databinding.FragmentHomeBinding;
import com.example.medassist.databinding.FragmentResultBinding;
import com.example.medassist.ui.home.HomeViewModel;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ResultViewModel resultViewModel =
                new ViewModelProvider(this).get(ResultViewModel.class);

        binding = FragmentResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Medicine medicine = (Medicine) getArguments().getSerializable("medicine");

        // Set the title of the dialog to the Symptoms value of the Medicine

        binding.txtSymptoms.setText(medicine.getSymptoms());
        binding.txtMedicinesNames.setText(medicine.getMedicines());
        binding.txtAdviceName.setText(medicine.getAdvice());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}