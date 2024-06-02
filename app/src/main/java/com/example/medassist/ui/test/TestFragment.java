package com.example.medassist.ui.test;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medassist.R;
import com.example.medassist.data.Medicine;
import com.example.medassist.data.adapter.MedicineAdapter;
import com.example.medassist.databinding.FragmentHomeBinding;
import com.example.medassist.databinding.FragmentTestBinding;
import com.example.medassist.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment implements MedicineAdapter.OnItemClickListener {

    private FragmentTestBinding binding;
    TestViewModel testViewModel;
    private static final int SPEECH_REQUEST_CODE = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);
        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        testViewModel.getMedicines();
        testViewModel.getMedicinesLiveData().observe(requireActivity(), new Observer<List<Medicine>>() {
            @Override
            public void onChanged(List<Medicine> medicineList) {
                binding.btnSpeak.setVisibility(View.VISIBLE);
                MedicineAdapter adapter = new MedicineAdapter(medicineList, TestFragment.this::onItemClick);
                Log.e("medicineList ", medicineList.get(0).getMedicines());
                binding.recyclerview.setAdapter(adapter);
            }
        });

        binding.btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechRecognition();
            }
        });

        return root;
    }

    private void startSpeechRecognition() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://market.android.com/details?id=" + appPackageName));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the speech text
            ArrayList<String> speechText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
           // Toast.makeText(requireActivity(), "position " + speechText, Toast.LENGTH_LONG).show();

            // Search the spoken text and get the corresponding Medicines and Advice
            if (speechText != null && !speechText.isEmpty()) {
                String spokenText = speechText.get(0);

               // Toast.makeText(requireActivity(), "first  " + speechText.get(0) + " all " + spokenText, Toast.LENGTH_LONG).show();
                // Iterate over the Medicine items and search for the spoken text
                List<Medicine> medicineList = testViewModel.getMedicinesLiveData().getValue();
                if (medicineList != null) {
                    for (Medicine medicine : medicineList) {
                        if (medicine.getSymptoms().toLowerCase().contains(spokenText.toLowerCase())) {
                           // Toast.makeText(requireActivity(), "toLowerCase  " + medicine.getSymptoms().toLowerCase() + " spokenText.toLowerCase() " + spokenText.toLowerCase(), Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("medicine", medicine);
                            Navigation.findNavController(this.getView())
                                    .navigate(R.id.nav_result, bundle);
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onItemClick(Medicine medicine) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("medicine", medicine);
        Navigation.findNavController(this.getView())
                .navigate(R.id.nav_result, bundle);
        //Toast.makeText(requireActivity(), "position " + medicine.getMedicines(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}