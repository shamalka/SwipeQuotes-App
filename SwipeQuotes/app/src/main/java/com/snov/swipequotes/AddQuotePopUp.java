package com.snov.swipequotes;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddQuotePopUp extends DialogFragment {

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    RadioGroup radioGroup;
    RadioButton radioButton;

    String DiscussionType;
    Button AddDiscussionButton;

    EditText UserName, Quote;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_popup, container,false);

        mFirestore = FirebaseFirestore.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy:hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        Toast.makeText(getContext(),"Time " + format,Toast.LENGTH_SHORT).show();

        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar_pop);
        progressBar.setVisibility(View.GONE);

        UserName = (EditText)view.findViewById(R.id.user_name);
        Quote = (EditText)view.findViewById(R.id.quote_text);

        Button SubmitButton = (Button)view.findViewById(R.id.submit_button);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String UserNameText = UserName.getText().toString();
                String QuoteText = Quote.getText().toString();

                Map<String, String> QuotesMap = new HashMap<>();
                QuotesMap.put("quote", QuoteText);
                QuotesMap.put("author", "- " + UserNameText);

                mFirestore.collection("quotes").add(QuotesMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Quote Added", Toast.LENGTH_LONG).show();
                        getDialog().dismiss();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed, Try again.!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        return view;



    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
