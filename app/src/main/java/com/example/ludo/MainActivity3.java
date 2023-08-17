package com.example.ludo;
/*
b0-b51 whitebox,rb01-rb05 redbox,bb01-bb05 bluebox,yb01-yb05 yellowbox,gb01-gb05 greenbox,b71-b86 red green yellow blue tokens respectively and rh01-rh04 redhouse,bh01-bh04 bluehouse,yh01-yh04 yellowhouse,gh01-gh04 greenhouse
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener{
    Random r;
    Boolean state = false;
    int counter=0;
    String playerSelected;
    int noOfPlayerPlayingNow;
    String diceValue;
    ArrayList<LinearLayout> alc[];
    LinearLayout alca[][];
    int turn = 0;
    int dv = 0;
    int playerOrder[][] = {{0, 2}, {0, 3, 2}, {0, 3, 2, 1}};
    int playerOrderNow[];
    boolean eligible = false;
    View previousDice=null;
    int checkingn=0;
    LinearLayout parent;
    View view;
    boolean callOnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        playerSelected = getIntent().getStringExtra(MainActivity2.EXTRA_MESSAGE);
        noOfPlayerPlayingNow = Character.getNumericValue(playerSelected.charAt(0));
        playerOrderNow = playerOrder[noOfPlayerPlayingNow - 2];
        setContentView(R.layout.activity_main3);

        houseSelection();
        for (int i = 71; i <= 90; i++)
            ((Button) findViewById(getResources().getIdentifier(("b" + i), "id", getPackageName()))).setOnClickListener(this);
        for (int i = 71; i <= 90; i++)
            ((Button) findViewById(getResources().getIdentifier(("b" + i), "id", getPackageName()))).setClickable(false);

        turn();
   }


    public void turn() {
        int n = playerOrderNow[turn];
        int p = playerOrderNow[turn != 0 ? (turn - 1) : (playerOrderNow.length - 1)];
         for (int i = 0; i < 4; i++) {
            if (findViewById(getResources().getIdentifier(("b" + (71 + p * 4 + i)), "id", getPackageName())) != null) {
                ((Button) findViewById(getResources().getIdentifier(("b" + (71 + p * 4 + i)), "id", getPackageName()))).setClickable(false);
               // ((Button) findViewById(getResources().getIdentifier(("b" + (71 + p * 4 + i)), "id", getPackageName()))).setVisibility(View.INVISIBLE);
            }
        }

        ((Button) findViewById(getResources().getIdentifier(("b" + (87 + playerOrderNow[turn != 0 ?
                turn - 1 : (playerOrderNow.length - 1)])), "id", getPackageName()))).setClickable(false);
        ((Button) findViewById(getResources().getIdentifier(("b" + (87 + playerOrderNow[turn != 0 ?
                turn - 1 : (playerOrderNow.length - 1)])), "id", getPackageName()))).setVisibility(View.INVISIBLE);

         if(alca[turn][alca[turn].length - 1].getChildCount() != 4)  {
             ((Button) findViewById(getResources().getIdentifier(("b" + (87 + n)), "id", getPackageName()))).setVisibility(View.VISIBLE);
             ((Button) findViewById(getResources().getIdentifier(("b" + (87 + n)), "id", getPackageName()))).setClickable(true);
             setDotSymbol(findViewById(getResources().getIdentifier(("b" + (87 + n)), "id", getPackageName())), "6");
         }
         else
         {
           turn++;
           turn();
         }
    }

    public void tokenClickableAfterDiceRole() {
        View v = null;
        int n = playerOrderNow[turn];
        checkingn = n;
        eligible = false;
        for (int i = 0; i < 4; i++) {
            if ((v = findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))) != null && alc[turn].indexOf(v.getParent()) != alca[turn].length - 1) {
                ((Button) findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))).setClickable(true);
               // ((Button) findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))).setVisibility(View.VISIBLE);
            }
            int a = Integer.parseInt(getDiceIntegerValueFromDot(previousDice));
                if (v != null && alc[turn].contains(v.getParent()) && (alc[turn].indexOf(v.getParent()) + a) < alca[turn].length)
                    eligible = true;
            if (v != null && alc[turn].contains(v.getParent()) && (alc[turn].indexOf(v.getParent()) + a) > alca[turn].length)
                ((Button)v).setClickable(false);

        }
    }
    MediaPlayer mps;
    public void sound(String a) {
        if(mps!=null) {
            mps.stop();
            mps.release();
            mps=null;
        }
        switch (a) {

            case "roll": mps = MediaPlayer.create(MainActivity3.this, R.raw.rolling_dice_sound);
                break;
            case "kill": mps = MediaPlayer.create(MainActivity3.this, R.raw.sh);
                            mps.setLooping(true);
                            callOnce=false;
                break;
            case "sitting": mps = MediaPlayer.create(MainActivity3.this, R.raw.beep_sound);
                break;
            case "red": mps = MediaPlayer.create(MainActivity3.this, R.raw.red);
                break;
            case "win": mps = MediaPlayer.create(MainActivity3.this, R.raw.win);
                break;
            case "move": mps = MediaPlayer.create(MainActivity3.this, R.raw.whoosh_sound);
                break;
            default: mps = MediaPlayer.create(MainActivity3.this, R.raw.gameover);
        }
            mps.start();

    }
    public void houseSelection() {
        int startVanish = 87;
        alc = new ArrayList[noOfPlayerPlayingNow];
        alca = new LinearLayout[noOfPlayerPlayingNow][57];
        for (int i = 0; i < noOfPlayerPlayingNow; i++) {
            alc[i] = new ArrayList<LinearLayout>();
        }

        display(noOfPlayerPlayingNow);
        for (int i = 0; i < noOfPlayerPlayingNow; i++) {
            int a = alc[0].size();
            alca[i] = new LinearLayout[a];
            alca[i] = alc[i].toArray(alca[i]);
        }

        if (playerSelected.equals("2P") || playerSelected.equals("3P")) {
            ((Button) findViewById(R.id.b90)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.gdl)).setVisibility(View.GONE);
            startVanish = 75;

        }
        if (playerSelected.equals("2P")) {
            ((Button) findViewById(R.id.b88)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.bdl)).setVisibility(View.GONE);
            startVanish = 75;
        }

        for (int i = startVanish; i < 87; i++) {
            ((Button) findViewById(getResources().getIdentifier(("b" + i), "id", getPackageName()))).setVisibility(View.GONE);
            if (i == 78 && playerSelected.equals("2P"))
                i = 82;
            if(i == 78 && playerSelected.equals("3P"))
                break;

        }
    }

    public String getDiceIntegerValueFromDot(View v) {
        if (((Button) v).getText().equals("●"))
            return "1";
        else if (((Button) v).getText().equals("●\n\n●"))
            return "2";
        else if (((Button) v).getText().equals("\t\t●\n\t●\t\n●\t\t"))
            return "3";
        else if (((Button) v).getText().equals("●\t\t●\n\n●\t\t●"))
            return "4";
        else if (((Button) v).getText().equals("●\t\t●\n●\n●\t\t●"))
            return "5";
        else
            return "6";
    }

    public void setDotSymbol(View v, String s) {
        if (s.equals("1")) {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("●");
        } else if (s.equals("2")) {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("●\n\n●");
        } else if (s.equals("3")) {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("\t\t●\n\t●\t\n●\t\t");
        } else if (s.equals("4")) {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("●\t\t●\n\n●\t\t●");
        } else if (s.equals("5")) {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("●\t\t●\n●\n●\t\t●");
        } else {
            ((Button) v).setTextSize(8);
            ((Button) v).setText("●\t\t●\n●\t\t●\n●\t\t●");
        }
    }



    public void display(int ps) {
        for (int j = 0; j < ps; j++) {
            int c = 0;
            int a = playerOrderNow[j];
            for (int i = 13 * a; c < 51; i++) {

                alc[j].add(findViewById(getResources().getIdentifier((i < 10 ? "b0" : "b") + i, "id", getPackageName())));

                if (i == 51)
                    i = -1;
                c++;
            }
            for (int i = 1; i < 6; i++)
                alc[j].add(findViewById(getResources().getIdentifier((a == 0 ? "r" : a == 1 ? "g" : a == 2 ? "y" : "b") + "b0" + i, "id", getPackageName())));

            alc[j].add(findViewById(getResources().getIdentifier("l0" + a, "id", getPackageName())));
}


    }

    public void checkingToTurnOrNot() {
        if (dv != 6)
            turn++;
        if (turn == noOfPlayerPlayingNow)
            turn = 0;

        if (dv != 6)
            turn();
        if(dv == 6 && eligible == false) {
            int store = 0;
           String s = ((playerOrderNow[turn] == 0) ? "r" : (playerOrderNow[turn] == 1) ? "g" : (playerOrderNow[turn] == 2) ? "y" : "b");
            for (int i = 1; i <=4; i++)
                store = store + ((LinearLayout)findViewById(getResources().getIdentifier( s+ "h0" + i, "id", getPackageName()))).getChildCount();
        if(store == 0) {
            turn++;
            if (turn == noOfPlayerPlayingNow)
                turn = 0;
            turn();
        }
        }

    }
    public void diceAnimation(View v) {
        ((Button)v).setText("");
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setRepeatCount(1);
        v.startAnimation(rotateAnimation);
    }
    int i=1;
    int rec;
    int index;
    int irec;
    View viewToRemove;
    int di;
    int ci;
    public void backAfterIf() {
        String s = (ci == 0) ? "r" : (ci == 1) ? "g" : (ci == 2) ? "y" : "b";
        for (int j = 1; j <= 4; j++) {
            int id = (getResources().getIdentifier(s + "h0" + j, "id", getPackageName()));
            if (((LinearLayout) findViewById(id)).getChildCount() == 0) {
                ((LinearLayout)viewToRemove.getParent()).removeView(viewToRemove);
                ((LinearLayout) findViewById(id)).addView(viewToRemove);
            }
        }

    }
  LinearLayout l;
    boolean flag=true;
    Runnable run = new Runnable() {

        public void run() {
            parent = (LinearLayout) view.getParent();

        if (i <= (rec = Integer.parseInt(getDiceIntegerValueFromDot(findViewById(getResources().getIdentifier("b" + (87 + playerOrderNow[turn]), "id", getPackageName())))))) {

            flag = true;
            if (view != null) {


                parent.removeView(view);
                sound("move");
                (alca[turn][(alc[turn].indexOf(parent)) + 1]).addView(view);

                LinearLayout newParent = alca[turn][(alc[turn].indexOf(parent)) + 1];

                if(alc[turn].indexOf(view.getParent()) == alca[turn].length - 1) {
                    ((Button)view).setLayoutParams(new LinearLayout.LayoutParams(20, 35));
                    ((LinearLayout) view.getParent()).setGravity(Gravity.CENTER);
                    view.setClickable(false);
                }

                if (i == rec && (newParent.getId() == R.id.b08||newParent.getId() == R.id.b13 || newParent.getId() == R.id.b21 || newParent.getId() == R.id.b26 || newParent.getId() == R.id.b34 || newParent.getId() == R.id.b39 || newParent.getId() == R.id.b47 || newParent.getId() == R.id.b00))
                    sound("sitting");

                if (i == rec && newParent.getChildCount() > 1 && newParent.getId() != R.id.b08 && newParent.getId() != R.id.b13 && newParent.getId() != R.id.b21 && newParent.getId() != R.id.b26 && newParent.getId() != R.id.b34 && newParent.getId() != R.id.b39 && newParent.getId() != R.id.b47 && newParent.getId() != R.id.b00) {
                     for (int k = 0; k < (alca[turn][(alc[turn].indexOf(parent)) + 1]).getChildCount() - 1; k++) {
                        index = 0;
                        viewToRemove = (alca[turn][(alc[turn].indexOf(parent)) + 1]).getChildAt(k);
                        ci = Integer.parseInt((String) viewToRemove.getTag());
                        LinearLayout x3 = (alca[turn][(alc[turn].indexOf(parent)) + 1]);
                        String x2 = (String)x3.getChildAt(x3.getChildCount() - 1).getTag();
                        int x1 = Integer.parseInt(x2);
                        if (ci != x1) {
                            flag = false;
                            di = 0;
                            for (int i = 0; i < noOfPlayerPlayingNow; i++) {
                                if (ci == playerOrderNow[i]) {
                                    di = i;
                                    break;
                                }
                            }

                            index = alc[di].indexOf(viewToRemove.getParent());
                            callOnce = true;
                            Runnable back = new Runnable() {
                                @Override
                                public void run() {

                                    if(viewToRemove.getParent() == alca[di][0]) {
                                        dv = 6;
                                        setDotSymbol(previousDice,"6");
                                        mps.setLooping(false);
                                        mps.stop();
                                        mps.release();
                                        mps = null;
                                        backAfterIf();
                                        ifAfter();
                                    }
                                    if(index > 0) {
                                        alca[di][index--].removeView(viewToRemove);
                                        if(callOnce)
                                            sound("kill");
                                        alca[di][index].addView(viewToRemove);
                                        (new Handler()).postDelayed(this, 0);
                                    }
                                }
                            };

                            back.run();
                            break;
                        }
                    }
                }

            }
            i++;//
            if(l.getChildAt(l.getChildCount() - 1) == view && flag) {
                if(l == alca[turn][alca[turn].length - 1]) {
                    sound("red");
                    if(l.getChildCount() != 4) {
                        dv = 6;
                        setDotSymbol(previousDice, "6");
                    }
                    else {
                        sound("win");
                        counter++;
                        ((TextView) findViewById(getResources().getIdentifier("ll0" + (playerOrderNow[turn]), "id", getPackageName()))).setText(Integer.toString(counter));
                        if(counter == noOfPlayerPlayingNow-1) {
                            ((TextView) findViewById(R.id.mm)).setVisibility(View.VISIBLE);
                            sound("gameover");
                        }
                    }
                }
                ifAfter();
            }
            else
                (new Handler()).postDelayed(this,100);
        }
        }
    };
    public void ifAfter() {
        if (previousDice != null)
            previousDice.setClickable(true);
        View v = null;
            int n = playerOrderNow[turn];
            for (int i = 0; i < 4; i++) {
                if ((v = findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))) != null && alc[turn].indexOf(v.getParent()) != alca[turn].length - 1) {
                    ((Button) findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))).setClickable(false);
                    // ((Button) findViewById(getResources().getIdentifier(("b" + (71 + n * 4 + i)), "id", getPackageName()))).setVisibility(View.VISIBLE);
                }
            }

        checkingToTurnOrNot();
    }
    public void diceSymbolSet() {
        r = new Random();
        setDotSymbol(previousDice, Integer.toString(dv = (r.nextInt(6) + 1)));
        (new Handler()).postDelayed(this::remainingElse,500);
    }

    @Override
    public void onClick(View v) {

       if(counter < noOfPlayerPlayingNow - 1) {
           parent = (LinearLayout) v.getParent();
           if (v.getId() != R.id.b87 && v.getId() != R.id.b88 && v.getId() != R.id.b89 && v.getId() != R.id.b90) {
               if (!alc[turn].contains(parent)) {
                   if (getDiceIntegerValueFromDot(findViewById(getResources().getIdentifier("b" + (87 + playerOrderNow[turn]), "id", getPackageName()))).equals("6")) {
                       parent.removeView(v);
                       sound("move");
                       alca[turn][0].addView(v);
                       ifAfter();
                   }
               } else {
                   i = 1;
                   view = v;
                   l = alca[turn][alc[turn].indexOf(v.getParent()) + Integer.parseInt(getDiceIntegerValueFromDot(previousDice))];
                   run.run();
               }
           } else {

               (previousDice = v).setClickable(false);
                sound("roll");
               diceAnimation(v);
               (new Handler()).postDelayed(this::diceSymbolSet, 425);
           }

       }

    }
public void remainingElse() {

    tokenClickableAfterDiceRole();
    if(eligible == false)
        checkingToTurnOrNot();
}
        //((Button)findViewById(R.id.test)).setText(Integer.toString(turn)+" "+Integer.toString(checkingn)+" "+Integer.toString(dv));


    public void onBackPressed()
    {
        message();
    }
    public void message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Want to Exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goTo();
            }
    });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void goTo() {
        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
        finish();
    }
}












