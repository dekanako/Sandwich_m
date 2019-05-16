package com.example.android.sandwich_m;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwich_m.model.Sandwich;
import com.example.android.sandwich_m.utils.JsonUtils;
import com.squareup.picasso.Picasso;


import org.json.JSONException;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity
{

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getName();

    private TextView mAlsoKnownAsView;
    private TextView mDetailView;
    private TextView mPlaceOfOriginView;
    private TextView mDescriptionView;
    private TextView mIngredientsView;

    private TextView mAlsoKnownAsLabel;
    private TextView mPlaceOfOriginLabel;

    private Sandwich mSandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null)
        {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION)
        {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = null;
        try
        {
            mSandwich = JsonUtils.parseSandwichJson(json);
            Log.d(TAG, mSandwich.getImage());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSandwich == null)
        {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);
        populateUI();


        setTitle(mSandwich.getMainName());
    }

    private void closeOnError()
    {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI()
    {
        mAlsoKnownAsView = (TextView)findViewById(R.id.also_known_tv);
        mDescriptionView = (TextView)findViewById(R.id.description_tv);
        mIngredientsView = (TextView)findViewById(R.id.ingredients_tv);
        mPlaceOfOriginView = (TextView)findViewById(R.id.origin_tv);
        mAlsoKnownAsLabel = findViewById(R.id.also_known_as_label_id);
        mPlaceOfOriginLabel = findViewById(R.id.textView3);

        if (mSandwich.getAlsoKnownAs().size()==0)
        {
            mAlsoKnownAsView.setVisibility(View.GONE);
            mAlsoKnownAsLabel.setVisibility(View.GONE);

        }
        else
        {
            mAlsoKnownAsView.setText(getAlsoKnownAsFormatted(mSandwich.getAlsoKnownAs()));
        }
        if(mSandwich.getPlaceOfOrigin() == null||mSandwich.getPlaceOfOrigin().equals(""))
        {
            mPlaceOfOriginView.setVisibility(View.GONE);
            mPlaceOfOriginLabel.setVisibility(View.GONE);
        }

        mDescriptionView.setText(mSandwich.getDescription());
        mPlaceOfOriginView.setText(mSandwich.getPlaceOfOrigin());
        mIngredientsView.setText(getIngredinetsFormatted(mSandwich.getIngredients()));


    }

    private String getAlsoKnownAsFormatted(List<String> alsoKnownAs)
    {
        StringBuilder builder = new StringBuilder();

        for (int x = 0;x<alsoKnownAs.size();x++)
        {

            builder.append(alsoKnownAs.get(x));
            if (x!=alsoKnownAs.size()-1 )
            {
                builder.append("--");
            }
        }
        return builder.toString();
    }


    private String getIngredinetsFormatted(List<String> ingredients)
    {
        StringBuilder builder = new StringBuilder();

        for (int x = 0;x<ingredients.size();x++)
        {
            if (x==0)
            {
                builder.append("*");
            }
            builder.append(ingredients.get(x));
            if (x!=ingredients.size()-1 )
            {
                builder.append("\n");
                builder.append("*");

            }
        }
        return builder.toString();
    }
}
