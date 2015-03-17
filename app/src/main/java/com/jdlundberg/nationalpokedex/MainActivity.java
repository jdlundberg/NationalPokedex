package com.jdlundberg.nationalpokedex;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    ListView pokemonListView;
    ImageView pokemonImageView;
    TextView pokemonNumber, pokemonName, pokemonSpecies, pokemonType, pokemonHeight, pokemonWeight, pokemonDescription;
    ArrayList<HashMap> pokemonData = new ArrayList<>();
    ArrayAdapter<String> pokemonListAdapter;
    Context context;
    String pokemonNumberValue, pokemonNameValue, pokemonSpeciesValue, pokemonDescriptionValue, pokemonHeightValue, pokemonWeightValue;
    ArrayList<String> pokemonTypeValue = new ArrayList<>();
    Bitmap spriteBMP;
    GetPokemonInfo getInfo = new GetPokemonInfo();
    HashMap pokemonHash = new HashMap();
    ArrayList<String> pokemonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        pokemonListView = (ListView) findViewById(R.id.pokemonListView);
        pokemonImageView = (ImageView)findViewById(R.id.pokemonImageView);
        pokemonNumber = (TextView)findViewById(R.id.pokemonNumber);
        pokemonName = (TextView)findViewById(R.id.pokemonName);
        pokemonSpecies = (TextView)findViewById(R.id.pokemonSpecies);
        pokemonType = (TextView)findViewById(R.id.pokemonType);
        pokemonHeight = (TextView)findViewById(R.id.pokemonHeight);
        pokemonWeight = (TextView)findViewById(R.id.pokemonWeight);
        pokemonDescription = (TextView)findViewById(R.id.pokemonDescription);
        pokemonListAdapter = new ArrayAdapter<>(this, R.layout.pokemon_list_item, pokemonList);
        pokemonListView.setAdapter(pokemonListAdapter);

        PokemonListLoader pokemonListLoader = new PokemonListLoader();
        pokemonListLoader.execute();

        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer listPosition = position + 1;

                Log.w("Number sent", "Number " + listPosition + " was sent");

                PokemonInfoLoader pokemonInfoLoader = new PokemonInfoLoader();
                pokemonInfoLoader.execute(listPosition);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    public class PokemonListLoader extends AsyncTask<Void, Void, Void> {

        ProgressDialog listDialog;

        @Override

        protected void onPreExecute() {

            listDialog = new ProgressDialog(context);
            listDialog.setTitle("Loading Pokemon List");
            listDialog.setMessage("Please wait");
            listDialog.show();

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            pokemonData = getInfo.GetPokemonInfo();
            String pokemonNumber;
            String pokemonName;

            for (int i = 0; i < pokemonData.size(); i++) {

                pokemonHash = pokemonData.get(i);
                pokemonNumber = pokemonHash.get("Number").toString();
                pokemonName = pokemonHash.get("Name").toString();

                pokemonList.add(pokemonNumber + " " + pokemonName);

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            listDialog.dismiss();
            pokemonListAdapter.notifyDataSetChanged();

            super.onPostExecute(result);

        }

    }

    public class PokemonInfoLoader extends AsyncTask<Integer, Void, Void> {

        ProgressDialog infoDialog;

        @Override
        protected void onPreExecute() {

            infoDialog = new ProgressDialog(context);
            infoDialog.setTitle("Loading Pokemon Information");
            infoDialog.setMessage("Please wait");
            infoDialog.setCancelable(false);
            infoDialog.show();

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Integer... pokeNumber) {

            Integer pokemonNumber = pokeNumber[0] - 1;
            String spriteURL;

            //pokemonData = getInfo.GetPokemonInfo();

            pokemonHash = pokemonData.get(pokemonNumber);

            pokemonNumberValue = pokemonHash.get("Number").toString();
            pokemonNameValue = pokemonHash.get("Name").toString();
            pokemonTypeValue = (ArrayList)pokemonHash.get("Type");
            pokemonHeightValue = pokemonHash.get("Height").toString();
            pokemonWeightValue = pokemonHash.get("Weight").toString();
            pokemonDescriptionValue = pokemonHash.get("Description").toString();
            spriteURL = pokemonHash.get("Sprite").toString();

            try {

                URL sprite = new URL(spriteURL);
                spriteBMP = BitmapFactory.decodeStream(sprite.openConnection().getInputStream());

            }

            catch (IOException e) {

                e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            infoDialog.dismiss();
            pokemonNumber.setText(pokemonNumberValue);
            pokemonName.setText(pokemonNameValue);
            pokemonType.setText(pokemonTypeValue.toString());
            pokemonSpecies.setText(pokemonSpeciesValue);
            pokemonHeight.setText(pokemonHeightValue);
            pokemonWeight.setText(pokemonWeightValue);
            pokemonImageView.setImageBitmap(spriteBMP);
            pokemonDescription.setText(pokemonDescriptionValue);

            super.onPostExecute(result);

        }

    }

}