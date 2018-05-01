package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {
        final TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        final TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        final TextView descriptionTextView = findViewById(R.id.description_tv);
        final TextView originTextView = findViewById(R.id.origin_tv);

        alsoKnownAsTextView.setText(toFormattedString(sandwich.getAlsoKnownAs()));
        ingredientsTextView.setText(toFormattedString(sandwich.getIngredients(), "\n"));
        descriptionTextView.setText(toFormattedString(sandwich.getDescription()));
        originTextView.setText(toFormattedString(sandwich.getPlaceOfOrigin()));
    }

    private String toFormattedString(final List<String> strings) {
        return toFormattedString(strings, ", ");
    }

    private String toFormattedString(final List<String> strings, final String delimeter) {
        if (strings == null | strings.size() == 0) {
            return "Not Available";
        }
        final StringBuilder builder = new StringBuilder();
        final int length = strings.size();
        for (int i = 0; i < length - 1; i++) {
            builder.append(strings.get(i) + delimeter);
        }
        builder.append(strings.get(length - 1));
        return builder.toString();
    }

    private String toFormattedString (final String string) {
        if (string == null | string.equals("")) {
            return "Not Available";
        }
        return string;
    }
}
