package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsImageView = findViewById(R.id.image_iv);

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

        //Parsing the Json data and putting it into a 'Sandwich' using populateUI():
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String jsonData = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(jsonData);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich newSandwich) {
        //Inflate the id for each TextView:
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);

        //Invoke the Sandwich Getters to define the 4 variables, use setText & TextUtils
        placeOfOriginTextView.setText(newSandwich.getPlaceOfOrigin());
        descriptionTextView.setText(newSandwich.getDescription());
        //Merge the arrays of strings for 'ingredients' and 'alsoKnowsAs':
        ingredientsTextView.setText(TextUtils.join(", ", newSandwich.getIngredients()));
        alsoKnownAsTextView.setText(TextUtils.join(", ", newSandwich.getAlsoKnownAs()));

    }
}
