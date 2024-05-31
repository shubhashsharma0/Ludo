

package com.example.ludo;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity2 extends AppCompatActivity {
    Context context = this;
    MediaPlayer mp;
    public static final String EXTRA_MESSAGE = "com.example.ludo.MESSAGE";
    private View numPlyrPrevView = null;
    private boolean isPlayerSelected = false;
    String playerSelected="";
    private View playWithPrevView = null;
    private boolean isPlayWithSel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main2);
        mp=MediaPlayer.create(MainActivity2.this, R.raw.ludo_game);
        mp.start();
        mp.setLooping(true);

        ((Button) findViewById(R.id.p2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp1 = MediaPlayer.create(MainActivity2.this, R.raw.click);
                mp1.start();
                String viewText = (String) ((Button) v).getText();
                playerSelected = viewText;
                    if (isPlayerSelected)
                        ((Button) numPlyrPrevView).setBackgroundResource(R.drawable.roundcornerbutton);
                    ((Button) v).setBackgroundResource(R.drawable.playerselected);
                    isPlayerSelected = true;
                    numPlyrPrevView = v;
            }

            });
        ((Button) findViewById(R.id.p3)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MediaPlayer mp1 = MediaPlayer.create(MainActivity2.this, R.raw.click);
                mp1.start();
                String viewText = (String) ((Button) v).getText();
                playerSelected = viewText;
                if (isPlayerSelected)
                    ((Button) numPlyrPrevView).setBackgroundResource(R.drawable.roundcornerbutton);
                ((Button) v).setBackgroundResource(R.drawable.playerselected);
                isPlayerSelected = true;
                numPlyrPrevView = v;
            }
        });
        ((Button) findViewById(R.id.p4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp1 = MediaPlayer.create(MainActivity2.this, R.raw.click);
                mp1.start();
                String viewText = (String) ((Button) v).getText();
                playerSelected=viewText;
                if (isPlayerSelected)
                    ((Button) numPlyrPrevView).setBackgroundResource(R.drawable.roundcornerbutton);
                ((Button) v).setBackgroundResource(R.drawable.playerselected);
                isPlayerSelected = true;
                numPlyrPrevView = v;
            }
        });

        ((Button) findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlayerSelected) {
                    if (mp != null) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }
                    MediaPlayer mp3 = MediaPlayer.create(MainActivity2.this, R.raw.start);
                    mp3.start();
                    Intent intent = new Intent(context, MainActivity3.class);
                    playerSelected = playerSelected;


                    intent.putExtra(EXTRA_MESSAGE, playerSelected);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
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
                 if(mp != null) {
                     mp.stop();
                     mp.release();
                     mp = null;
                 }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                else
                    finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}




/*


    public void ifPart(LinearLayout parentOfViewv, View v) {
        parentOfViewv.removeView(v);
        if (v.getId() == R.id.gt01 || v.getId() == R.id.gt02 || v.getId() == R.id.gt03 || v.getId() == R.id.gt04) {
            ((LinearLayout) findViewById(R.id.bo13)).addView(v);
            ((Button) findViewById(R.id.gd)).setClickable(true);
        }
        else if (v.getId() == R.id.yt01 || v.getId() == R.id.yt02 || v.getId() == R.id.yt03 || v.getId() == R.id.yt04){
            ((LinearLayout) findViewById(R.id.bo26)).addView(v);
            ((Button) findViewById(R.id.yd)).setClickable(true);
        }
        else if (v.getId() == R.id.rt01 || v.getId() == R.id.rt02 || v.getId() == R.id.rt03 || v.getId() == R.id.rt04){
            ((LinearLayout) findViewById(R.id.bo00)).addView(v);
            ((Button) findViewById(R.id.rd)).setClickable(true);
        }
        else
            {
            ((LinearLayout) findViewById(R.id.bo39)).addView(v);
            ((Button) findViewById(R.id.bd)).setClickable(true);
            }
        isDiceRolled = false;

    }

    View v;
    boolean firstTime;
    int i;

    public void elsePart(LinearLayout parentOfViewv, View view) {
        v = view;
        firstTime = true;
        i = 1;
        running = true;

            if (newParentBox()) {
                i++;
                (new Handler()).postDelayed(moveBetweenBox, 200);
            }
        }

    int diceId;


    Runnable moveBetweenBox = new Runnable() {
        // LinearLayout parentOfViewv;
        @Override
        public void run() {
            if (running) {

                if (i <= Integer.parseInt(getText(findViewById(diceId)))) {
                    newParentBox();
                    i++;
                    (new Handler()).postDelayed(this, 200);
                }
                else {
                    isDiceRolled = false;
                    settingValueSix();
                    ((Button) findViewById(diceId)).setClickable(true);
                    v.setClickable(false);
                }
            }
        }
    };


    boolean newParentBox()
    {
        if (firstTime) {
            if (v.getId() == R.id.gt01 || v.getId() == R.id.gt02 || v.getId() == R.id.gt03 || v.getId() == R.id.gt04)
                diceId = R.id.gd;
            else if (v.getId() == R.id.yt01 || v.getId() == R.id.yt02 || v.getId() == R.id.yt03 || v.getId() == R.id.yt04)
                diceId = R.id.yd;
            else if (v.getId() == R.id.rt01 || v.getId() == R.id.rt02 || v.getId() == R.id.rt03 || v.getId() == R.id.rt04)
                diceId = R.id.rd;
            else
                diceId = R.id.bd;
            firstTime = false;
        }

        LinearLayout parentOfViewv = (LinearLayout) v.getParent();

        String id = (parentOfViewv).getResources().getResourceEntryName((parentOfViewv).getId());

        int boxNumber = Integer.parseInt(id.substring(id.length() - 2, id.length()));
        boolean isBox = id.startsWith("gb") || id.startsWith("yb") || id.startsWith("rb") || id.startsWith("bb");
        if (isBox && i == 1 && boxNumber + Integer.parseInt(getText(findViewById(diceId))) > 6)
            (new Handler()).removeCallbacks(moveBetweenBox);//break
        if (boxNumber == 51)
            boxNumber = -1;

        else if (diceId == R.id.gd && boxNumber == 11 || diceId == R.id.yd && boxNumber == 24 || diceId == R.id.rd && boxNumber == 50 || diceId == R.id.bd && boxNumber == 37) {
            boxNumber = 0;
            if (v.getId() == R.id.gt01)
                notRoundCompleteGreenToken1 = false;
            else if (v.getId() == R.id.gt02)
                notRoundCompleteGreenToken2 = false;
            else if (v.getId() == R.id.gt03)
                notRoundCompleteGreenToken3 = false;
            else if (v.getId() == R.id.gt04)
                notRoundCompleteGreenToken4 = false;
            else if (v.getId() == R.id.yt01)
                notRoundCompleteYellowToken1 = false;
            else if (v.getId() == R.id.yt02)
                notRoundCompleteYellowToken2 = false;
            else if (v.getId() == R.id.yt03)
                notRoundCompleteYellowToken3 = false;
            else if (v.getId() == R.id.yt04)
                notRoundCompleteYellowToken4 = false;
            else if (v.getId() == R.id.rt01)
                notRoundCompleteRedToken1 = false;
            else if (v.getId() == R.id.rt02)
                notRoundCompleteRedToken2 = false;
            else if (v.getId() == R.id.rt03)
                notRoundCompleteRedToken3 = false;
            else if (v.getId() == R.id.rt04)
                notRoundCompleteRedToken4 = false;
            else if (v.getId() == R.id.bt01)
                notRoundCompleteBlueToken1 = false;
            else if (v.getId() == R.id.bt02)
                notRoundCompleteBlueToken2 = false;
            else if (v.getId() == R.id.bt03)
                notRoundCompleteBlueToken3 = false;
            else
                notRoundCompleteBlueToken4 = false;
        }

        int newBoxNumber = boxNumber + 1;

        String newBox = "\n\n";
    if(notRoundCompleteGreenToken1 &&v.getId()==R.id.gt01 ||notRoundCompleteGreenToken2 &&v.getId()==R.id.gt02 ||notRoundCompleteGreenToken3 &&v.getId()==R.id.gt03 ||notRoundCompleteGreenToken4 &&v.getId()==R.id.gt04 ||
    notRoundCompleteYellowToken1 &&v.getId()==R.id.yt01 ||notRoundCompleteYellowToken2 &&v.getId()==R.id.yt02 ||notRoundCompleteYellowToken3 &&v.getId()==R.id.yt03 ||notRoundCompleteYellowToken4 &&v.getId()==R.id.yt04 ||
    notRoundCompleteRedToken1 &&v.getId()==R.id.rt01 ||notRoundCompleteRedToken2 &&v.getId()==R.id.rt02 ||notRoundCompleteRedToken3 &&v.getId()==R.id.rt03 ||notRoundCompleteRedToken4 &&v.getId()==R.id.rt04 ||
    notRoundCompleteBlueToken1 &&v.getId()==R.id.bt01 ||notRoundCompleteBlueToken2 &&v.getId()==R.id.bt02 ||notRoundCompleteBlueToken3 &&v.getId()==R.id.bt03 ||notRoundCompleteBlueToken4 &&v.getId()==R.id.bt04)

    {
        if (newBoxNumber > 9)
            newBox = "bo" + newBoxNumber;
        else
            newBox = "bo0" + newBoxNumber;
    } else if(diceId ==R.id.gd &&(boxNumber +Integer.parseInt(getText(findViewById(R.id.gd)))<=6))
    newBox ="gb0"+newBoxNumber;
      else if(diceId ==R.id.yd &&(boxNumber +Integer.parseInt(getText(findViewById(R.id.yd)))<=6))
    newBox ="yb0"+newBoxNumber;
      else if(diceId ==R.id.rd &&(boxNumber +Integer.parseInt(getText(findViewById(R.id.rd)))<=6))
    newBox ="rb0"+newBoxNumber;
      else if(diceId ==R.id.bd &&(boxNumber +Integer.parseInt(getText(findViewById(R.id.bd)))<=6))
    newBox ="bb0"+newBoxNumber;

                if(newBox.equals("gb06")||newBox.equals("yb06")||newBox.equals("rb06")||newBox.equals("bb06"))
    {
        ((Button) v).setVisibility(View.GONE);
        setText(findViewById(diceId), "6");
    } else

    {
        LinearLayout newParentOfViewv;
        if (!newBox.equals("\n\n"))
            newParentOfViewv = (LinearLayout) findViewById(getResources().getIdentifier(newBox,"id",getPackageName()));
        else {
            newParentOfViewv = (LinearLayout) v.getParent();
            return false;
        }

            ((LinearLayout) v.getParent()).removeView(v);
        if (i == Integer.parseInt(getText(findViewById(diceId))) && newParentOfViewv.getChildCount() > 0 && newBoxNumber != 0 && newBoxNumber != 8 && newBoxNumber != 13 && newBoxNumber != 21 && newBoxNumber != 26 && newBoxNumber != 34 && newBoxNumber != 39 && newBoxNumber != 47) {
            boolean vAddedIfNoTokenRemoved = true;
            for (int j = 0; j < newParentOfViewv.getChildCount(); j++) {
                String idOfTokenToRemove = (newParentOfViewv.getChildAt(j)).getResources().getResourceEntryName((newParentOfViewv.getChildAt(j)).getId());
                if (!idOfTokenToRemove.startsWith("gt") && diceId == R.id.gd || !idOfTokenToRemove.startsWith("yt") && diceId == R.id.yd || !idOfTokenToRemove.startsWith("rt") && diceId == R.id.rd || !idOfTokenToRemove.startsWith("bt") && diceId == R.id.bd) {
                    running = false;
                    String idOfLinerLayoutToAdd = idOfTokenToRemove.charAt(0) + "h" + idOfTokenToRemove.substring(id.length() - 2, id.length());
                    Button tokenToRemove = (Button) newParentOfViewv.getChildAt(j);
                    newParentOfViewv.removeView(tokenToRemove);
                    ((LinearLayout)findViewById(getResources().getIdentifier(idOfTokenToRemove,"id",getPackageName()))).addView(tokenToRemove);
                    newParentOfViewv.addView(v);
                    setText(findViewById(diceId), "6");
                    vAddedIfNoTokenRemoved = false;

                    isDiceRolled = false;
                    ((Button) findViewById(diceId)).setClickable(true);
                    v.setClickable(false);

                    (new Handler()).removeCallbacks(moveBetweenBox);//break

                }
            }
            if (vAddedIfNoTokenRemoved)
                newParentOfViewv.addView(v);
            (new Handler()).removeCallbacks(moveBetweenBox);//break
        } else
            newParentOfViewv.addView(v);
    }
return true;
}

    public void requiredTokenAndDiceClickable(boolean v1,boolean v2,boolean v3,boolean v4)
    {
        ((Button) findViewById(R.id.gt01)).setClickable(v1);
        ((Button) findViewById(R.id.gt02)).setClickable(v1);
        ((Button) findViewById(R.id.gt03)).setClickable(v1);
        ((Button) findViewById(R.id.gt04)).setClickable(v1);
        ((Button) findViewById(R.id.gd)).setClickable(false);

        ((Button) findViewById(R.id.yt01)).setClickable(v2);
        ((Button) findViewById(R.id.yt02)).setClickable(v2);
        ((Button) findViewById(R.id.yt03)).setClickable(v2);
        ((Button) findViewById(R.id.yt04)).setClickable(v3);
        ((Button) findViewById(R.id.yd)).setClickable(false);

        ((Button) findViewById(R.id.rt01)).setClickable(v3);
        ((Button) findViewById(R.id.rt02)).setClickable(v3);
        ((Button) findViewById(R.id.rt03)).setClickable(v3);
        ((Button) findViewById(R.id.rt04)).setClickable(v3);
        ((Button) findViewById(R.id.rd)).setClickable(false);

        ((Button) findViewById(R.id.bt01)).setClickable(v4);
        ((Button) findViewById(R.id.bt02)).setClickable(v4);
        ((Button) findViewById(R.id.bt03)).setClickable(v4);
        ((Button) findViewById(R.id.bt04)).setClickable(v4);
        ((Button) findViewById(R.id.bd)).setClickable(false);
    }
    public void buttonAnimation()
    {
        ((Button) sendingButtonViewDice).setText("");
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(400);
        rotateAnimation.setRepeatCount(1);
        sendingButtonViewDice.startAnimation(rotateAnimation);
    }

    public void rollingDiceSound()
    {
        final MediaPlayer mp= MediaPlayer.create(MainActivity3.this,R.raw.rolling_dice);
        mp.start();
    }


    public void delay(int d) {
        (new Handler()).postDelayed(this::settingValueSix, d);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Want to Exit game?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void settingValueSix() {

        if (!getText(findViewById(R.id.rd)).equals("6") && !getText(findViewById(R.id.rd)).equals("\n\n")) {
            if (!playerSelected.equals("2P")) {
                setText(findViewById(R.id.bd), "6");
                ((Button) findViewById(R.id.bd)).setClickable(true);
                ((Button) findViewById(R.id.yd)).setText("\n\n");
                ((Button) findViewById(R.id.yd)).setClickable(false);
            } else {
                setText(findViewById(R.id.yd), "6");
                ((Button) findViewById(R.id.yd)).setClickable(true);
            }
            if (!playerSelected.equals("2P") && !playerSelected.equals("3P")) {
                ((Button) findViewById(R.id.gd)).setText("\n\n");
                ((Button) findViewById(R.id.gd)).setClickable(false);
            }

            ((Button) findViewById(R.id.rd)).setText("\n\n");
            ((Button) findViewById(R.id.rd)).setClickable(false);

        } else if (!playerSelected.equals("2P") && !getText(findViewById(R.id.bd)).equals("6") && !getText(findViewById(R.id.bd)).equals("\n\n")) {
            setText(findViewById(R.id.yd), "6");
            ((Button) findViewById(R.id.yd)).setClickable(true);
            ((Button) findViewById(R.id.bd)).setText("\n\n");
            ((Button) findViewById(R.id.bd)).setClickable(false);
            if (!playerSelected.equals("3P")) {
                ((Button) findViewById(R.id.gd)).setText("\n\n");
                ((Button) findViewById(R.id.gd)).setClickable(false);
            }
            ((Button) findViewById(R.id.rd)).setText("\n\n");
            ((Button) findViewById(R.id.rd)).setClickable(false);
        } else if (!getText(findViewById(R.id.yd)).equals("6") && !getText(findViewById(R.id.yd)).equals("\n\n")) {
            if (!playerSelected.equals("2P") && !playerSelected.equals("3P")) {
                setText(findViewById(R.id.gd), "6");
                ((Button) findViewById(R.id.gd)).setClickable(true);
                ((Button) findViewById(R.id.rd)).setText("\n\n");
                ((Button) findViewById(R.id.rd)).setClickable(false);
            } else {
                setText(findViewById(R.id.rd), "6");
                ((Button) findViewById(R.id.rd)).setClickable(true);
            }
            ((Button) findViewById(R.id.yd)).setText("\n\n");
            ((Button) findViewById(R.id.yd)).setClickable(false);
            if (!playerSelected.equals("2P")) {
                ((Button) findViewById(R.id.bd)).setText("\n\n");
                ((Button) findViewById(R.id.bd)).setClickable(false);
            }

        } else if (!playerSelected.equals("2P") && !playerSelected.equals("3P") && !getText(findViewById(R.id.gd)).equals("6") && !getText(findViewById(R.id.gd)).equals("\n\n")) {
            setText(findViewById(R.id.rd), "6");
            ((Button) findViewById(R.id.rd)).setClickable(true);
            ((Button) findViewById(R.id.gd)).setText("\n\n");
            ((Button) findViewById(R.id.gd)).setClickable(false);
            ((Button) findViewById(R.id.yd)).setText("\n\n");
            ((Button) findViewById(R.id.yd)).setClickable(false);
            ((Button) findViewById(R.id.bd)).setText("\n\n");
            ((Button) findViewById(R.id.bd)).setClickable(false);
        }
    }

    public String setDiceVal(View v) {
        isDiceRolled = true;
        String diceValue = getText(v);

        if (diceValue.equals("6")) {
            int randVal1to6 = r.nextInt(6) + 1;
            diceValue = Integer.toString(randVal1to6);

        }
        return diceValue;
    }

    public void houseSelection() {
        if (playerSelected.equals("2P")) {
            ((Button) findViewById(R.id.gd)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.gdl)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt01)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt02)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt03)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt04)).setVisibility(View.GONE);

            ((Button) findViewById(R.id.bd)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.bdl)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.bt01)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.bt02)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.bt03)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.bt04)).setVisibility(View.GONE);
        }

        if (playerSelected.equals("3P")) {
            ((Button) findViewById(R.id.gd)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.gdl)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt01)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt02)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt03)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.gt04)).setVisibility(View.GONE);
        }
    }

 */

/*LinkedList<LinearLayout> rh=new LinkedList<LinearLayout>();;
    public void fourLinkedList()
    {

        for(int i=0;i<=50;i++)
        {
            int id;
            if(i<10)
            id=getResources().getIdentifier("b0"+i,"id",getPackageName());
            else
            id=getResources().getIdentifier("b"+i,"id",getPackageName());
            rh.add(findViewById(id));
        }
        ((LinearLayout)findViewById(R.id.b00)).addView(findViewById(R.id.b71));
    }*/