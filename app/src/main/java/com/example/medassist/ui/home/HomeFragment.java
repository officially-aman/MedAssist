package com.example.medassist.ui.home;

import static com.example.medassist.MainActivity.navController;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.medassist.R;
import com.example.medassist.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_aboutus);
            }
        });
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_test);
            }
        });

        // Set up the onClickListener for btnTrain button in HomeFragment
        binding.btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Speak now");
                builder.setMessage("Please speak now to start voice recognition");
                // Set up the speech recognizer
                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext());
                Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                // Set up the listener for speech recognizer results
                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle bundle) {
                        // Show a message to the user that speech recognition is ready
                        Toast.makeText(requireActivity(), "Speech recognition is ready", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBeginningOfSpeech() {
                        // Show a message to the user that speech recognition has started
                        Toast.makeText(requireActivity(), "Speech recognition has started", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRmsChanged(float v) {}

                    @Override
                    public void onBufferReceived(byte[] bytes) {}

                    @Override
                    public void onEndOfSpeech() {
                        // Show a message to the user that speech recognition has ended
                        Toast.makeText(requireActivity(), "Speech recognition has ended", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int i) {
                        // Show an error message to the user
                        Toast.makeText(requireActivity(), "Speech recognition error: " + i, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResults(Bundle bundle) {
                        // Get the recognized speech text and show it to the user on an alert dialog
                        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        if (matches != null && matches.size() > 0) {
                            String spokenText = matches.get(0);
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            builder.setTitle("Spoken text");
                            builder.setMessage(spokenText);
                            builder.setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onPartialResults(Bundle bundle) {}

                    @Override
                    public void onEvent(int i, Bundle bundle) {}
                });

                // Show the alert dialog to the user and start the speech recognizer
                AlertDialog dialog = builder.create();
                dialog.show();
                speechRecognizer.startListening(speechRecognizerIntent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}