package com.snov.swipequotes;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.snov.swipequotes.Adapters.QuotesAdapter;
import com.snov.swipequotes.Models.Quote;
import com.snov.swipequotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;
    private List<Quote> quotesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        swipeCardsView = (SwipeCardsView)findViewById(R.id.fav_swipe_cards);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);



        getFavData();
    }

    private void getFavData() {

        SharedPreferences prefs = getSharedPreferences("FavPref", MODE_PRIVATE);
        //String Quote = prefs.getString("F2dBKpgvIG47Epa7GGbC", "");
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            //Log.d("Map values",entry.getKey() + ": " + entry.getValue().toString());
            Quote quoteModel = new Quote(entry.getKey(), entry.getValue().toString(), "", "");
            quotesList.add(quoteModel);
        }

        QuotesAdapter quotesAdapter = new QuotesAdapter(quotesList, FavoritesActivity.this);
        swipeCardsView.setAdapter(quotesAdapter);
    }
}
