package com.google.engedu.ghost;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private TextView ghosttext , gamestatus;
    private Button challenge , restart;
    private String st_ghost , st_status;
    private int count_user, count_computer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        ghosttext=(TextView) findViewById(R.id.ghostText);
        gamestatus=(TextView) findViewById(R.id.gameStatus);
        challenge=(Button) findViewById(R.id.button1);
        restart=(Button) findViewById(R.id.button2);

        ghosttext.setText(st_ghost);
        gamestatus.setText(st_status);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st=ghosttext.getText().toString();
                String word=dictionary.getAnyWordStartingWith(st);

                if(st.length()>=4 && dictionary.isWord(st) || word==null) {
                    gamestatus.setText("User Victory"); count_user++;
                }
                else if (word !=null ) {
                    gamestatus.setText("Computer Victory");
                    ghosttext.setText(dictionary.ComputerWins(word));
                    count_computer++;
                }
                else ;
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ghosttext.setText("");
                gamestatus.setText(USER_TURN);
            }
        });

    }

    @Override
    public boolean onKeyUp(int keyCode , KeyEvent event) {

        final android.os.Handler handler=new android.os.Handler();

        Log.w("count_user : ",""+count_user);
        Log.w("count_comp : ",""+count_computer);

        Log.w("KeyCode entering Func()" , ""+keyCode);

        if(keyCode >=29 && keyCode <=54) {
            Log.w("KeyCode = " , ""+keyCode);
            ghosttext.setText(ghosttext.getText().toString() + (char)(keyCode+68));
            gamestatus.setText(COMPUTER_TURN);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
//                    handler.removeCallbacks(this);   // NOT REQUIRED
                }
            }, 1000);

            return true;

        }
        else  {
            return super.onKeyUp(keyCode,event);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = true;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again

        Log.w("Func()  :  " , "ComputerTurn()");
        String st=ghosttext.getText().toString();

        if((st.length()>=4 && dictionary.isWord(st)) || (!dictionary.isWord(st) && dictionary.getAnyWordStartingWith(st) == null) ) {
                gamestatus.setText("Computer Victory");
//            ghosttext.setText(dictionary.ComputerWins(dictionary.getAnyWordStartingWith(st)));
            //ghosttext.setText(dictionary.getAnyWordStartingWith(st));
            count_computer++;
            return;
        }


//        Log.w("Not a word in cturn()"," "+st );
//
//        dictionary.getAnyWordStartingWith(st);

        if(dictionary.getAnyWordStartingWith(st) == null) {
            gamestatus.setText("User Victory");
            count_user++;
            Log.w("null word"," ");
        }
        else {
            String val = dictionary.getAnyWordStartingWith(st);
            ghosttext.setText(val.substring(0, st.length() + 1));
            Log.w("word prefix available"," "+dictionary.getAnyWordStartingWith(st));
        }


        userTurn = true;
        label.setText(USER_TURN);
    }

    // For handling the cases when the app gets suspended temporarily

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("fragment_word", ghosttext.getText().toString());
        savedInstanceState.putString("game_status", gamestatus.getText().toString());
        savedInstanceState.putInt("count_user" , count_user);
        savedInstanceState.putInt("count_user" , count_computer);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        st_ghost= savedInstanceState.getString("fragment_word");
        st_status=savedInstanceState.getString("game_status");
        count_user=savedInstanceState.getInt("count_user");
        count_computer=savedInstanceState.getInt("count_computer");

    }
}