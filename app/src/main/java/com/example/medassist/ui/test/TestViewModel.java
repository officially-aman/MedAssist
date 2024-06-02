package com.example.medassist.ui.test;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medassist.data.Medicine;
import com.example.medassist.data.adapter.MedicineAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestViewModel extends ViewModel {
    String TAG=TestViewModel.class.getName();
    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Medicine>> medicinesLiveData = new MutableLiveData<>();
    public LiveData<List<Medicine>> getMedicinesLiveData() {
        return medicinesLiveData;
    }
    public TestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getMedicines() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Medicine> medicineList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Medicine medicine = dataSnapshot.getValue(Medicine.class);
                    if (medicine != null) {
                        medicineList.add(medicine);
                    }
                }
                medicinesLiveData.postValue(medicineList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }
}