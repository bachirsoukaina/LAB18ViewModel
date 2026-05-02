package com.example.lab18viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ScoreViewModel — survit à la rotation d'écran.
 * Contient toute la logique métier du compteur.
 * L'Activity ne fait que observer et déléguer.
 */
public class ScoreViewModel extends ViewModel {

    // MutableLiveData : modifiable uniquement ici dans le ViewModel
    private final MutableLiveData<Integer> scoreLive = new MutableLiveData<>();

    public ScoreViewModel() {
        // Initialisation une seule fois, même si l'Activity tourne
        scoreLive.setValue(0);
    }

    /** Exposé en lecture seule vers l'Activity */
    public LiveData<Integer> getScore() {
        return scoreLive;
    }

    public void addOne() {
        int val = getCurrentValue();
        scoreLive.setValue(val + 1);
    }

    public void removeOne() {
        int val = getCurrentValue();
        scoreLive.setValue(val - 1);
    }

    public void resetScore() {
        scoreLive.setValue(0);
    }

    // Bonus : mise à jour depuis un thread background (postValue = thread-safe)
    public void addOneAsync() {
        new Thread(() -> {
            int val = getCurrentValue();
            scoreLive.postValue(val + 1); // postValue = safe depuis n'importe quel thread
        }).start();
    }

    private int getCurrentValue() {
        Integer current = scoreLive.getValue();
        return current != null ? current : 0;
    }
}