package com.snov.swipequotes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.snov.swipequotes.Adapters.QuotesAdapter;
import com.snov.swipequotes.Models.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;
    private List<Quote> quotesList = new ArrayList<>();

    ProgressBar progressBar;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //initialize the progress dialog and show it
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.show();

        swipeCardsView = (SwipeCardsView)findViewById(R.id.swipe_cards);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getData();

        Button FavoritesButton = (Button)findViewById(R.id.faviorites_button);
        FavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddQuotePopUp addQuotePopUp = new AddQuotePopUp();
                addQuotePopUp.show(getSupportFragmentManager(), "PopUpDialog");
            }
        });

        Button RefreshButton = (Button)findViewById(R.id.refresh_button);
        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

        mFireStore.collection("quotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    //Log.d(TAG, "Error: " + e.getMessage());
                }

                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String quote = doc.getDocument().getString("quote");
                        String author = doc.getDocument().getString("author");
                        //String timestamp = doc.getDocument().getString("timestamp");
                        String timestamp = "Date";
                        String DocID = doc.getDocument().getId();

                        Quote quoteModel = new Quote(DocID, quote, author, timestamp);
                        quotesList.add(quoteModel);
//
                    }
                }
                progressDialog.dismiss();

//                DiscussionAdapter discussionAdapter = new DiscussionAdapter(ForumHomeActivity.this, DiscussionArray);
//                mMainList.setAdapter(discussionAdapter);
                //Toast.makeText(getApplicationContext(), NameString, Toast.LENGTH_LONG).show();
                QuotesAdapter quotesAdapter = new QuotesAdapter(quotesList, MainActivity.this);
                swipeCardsView.setAdapter(quotesAdapter);
            }

        });

//        quotesList.add(new Quote("\"No man should go through life without once experiencing healthy, even bored solitude in the wilderness, finding himself depending solely on himself and thereby learning his true and hidden strength.\"", "Shamalka", "2019"));
//        quotesList.add(new Quote("'' It's like the people who believe they'll be happy if they go and live somewhere else, but who learn it doesn't work that way. Wherever you go, you take yourself with you. If you see what I mean.'' ", "Shamalka", "2019"));
//        quotesList.add(new Quote("Quote3", "Shamalka", "2019"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
