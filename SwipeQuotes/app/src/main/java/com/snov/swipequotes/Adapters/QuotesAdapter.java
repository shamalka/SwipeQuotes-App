package com.snov.swipequotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.snov.swipequotes.Models.Quote;
import com.snov.swipequotes.R;

import java.util.List;

public class QuotesAdapter extends BaseCardAdapter {

    private List<Quote> quotesList;
    private Context context;
    String FavPrefs = "FavPrefs";

    public QuotesAdapter(List<Quote> quotesList, Context context) {
        this.quotesList = quotesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return quotesList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if(quotesList == null || quotesList.size() ==0){
            return;
        }


        TextView quote = (TextView)cardview.findViewById(R.id.text_view);
        TextView author = (TextView)cardview.findViewById(R.id.author_text);
        Button FavioriteButton = (Button)cardview.findViewById(R.id.fav_button);
        CardView Card = (CardView)cardview.findViewById(R.id.card);
        final Quote quotesModel = quotesList.get(position);
        quote.setText(quotesModel.getQuote());
        author.setText(quotesModel.getAuthor());
        FavioriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("FavPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(quotesModel.getQuoteId(), quotesModel.getQuote());
                editor.commit();
                Toast.makeText(context, "Added to Favorite", Toast.LENGTH_LONG).show();
            }
        });

        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, quotesModel.getQuote() + "  - " + quotesModel.getAuthor());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });


    }
}
