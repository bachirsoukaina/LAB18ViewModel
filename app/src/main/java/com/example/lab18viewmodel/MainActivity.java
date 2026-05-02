package com.example.lab18viewmodel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * MainActivity — Vue uniquement.
 * Elle observe le ViewModel, elle ne calcule rien.
 * Rotation → nouvelle Activity, MÊME ViewModel, données intactes.
 */
public class MainActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;
    private TextView scoreDisplay;
    private Button plusBtn, minusBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison des vues
        scoreDisplay = findViewById(R.id.scoreDisplay);
        plusBtn      = findViewById(R.id.plusBtn);
        minusBtn     = findViewById(R.id.minusBtn);
        clearBtn     = findViewById(R.id.clearBtn);

        // Récupération du ViewModel (créé une fois, survit à la rotation)
        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        // Observation : l'UI se met à jour automatiquement quand la valeur change
        // L'observer est détruit avec l'Activity → zéro memory leak
        scoreViewModel.getScore().observe(this, newScore -> {
            scoreDisplay.setText(String.valueOf(newScore));
        });

        // Les boutons délèguent toute la logique au ViewModel
        plusBtn.setOnClickListener(v -> scoreViewModel.addOne());
        minusBtn.setOnClickListener(v -> scoreViewModel.removeOne());
        clearBtn.setOnClickListener(v -> scoreViewModel.resetScore());
    }
}