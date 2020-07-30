package com.example.braintrainer;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton, button0, button1, button2, button3, playagain, button_add, button_sub, button_mul, button_div, button_rand;
    TextView timertext, scoretext, answertext, questiontext, finalscore, points_textview, highscore_textview, point_textview;
    RelativeLayout relativeLayout, relativeLayout1, relativeLayout3;
    int answerplace, incorrectanswer, oneincorrectplace;
    ArrayList<Integer> answerlist = new ArrayList<Integer>();
    Random rand = new Random();
    int score = 0, totalquestion = 0, num, a, b;
    String str;
    char symbol;
    int highscore = 0, temp_highscore;
    public static final String Shared_prefs = "sharedPrefs";
    int points = 0;
    float rotation;
    CountDownTimer countDownTimer;

    @SuppressLint("SetTextI18n")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playagain = (Button) findViewById(R.id.playagain);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.rl1);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl2);
        timertext = (TextView) findViewById(R.id.timerTextView);
        scoretext = (TextView) findViewById(R.id.scoreTextView);
        answertext = (TextView) findViewById(R.id.answerTextView);
        questiontext = (TextView) findViewById(R.id.questionTextView);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.rl3);
        finalscore = (TextView) findViewById(R.id.lastscoreTextView);
        points_textview = (TextView) findViewById(R.id.pointsTextView);
        highscore_textview = (TextView) findViewById(R.id.highscore_textview);
        point_textview = (TextView) findViewById(R.id.pointTextView);
        button_add = (Button) findViewById(R.id.buttonaddition);
        button_sub = (Button) findViewById(R.id.buttonsubtraction);
        button_mul = (Button) findViewById(R.id.buttonmultiplication);
        button_div = (Button) findViewById(R.id.buttondivision);
        button_rand = (Button) findViewById(R.id.buttonrandom);
        loaddata();

        startButton.setTranslationX(-1000f);


        startButton.animate().translationXBy(1000f).setDuration(1000);

        rotation = button0.getRotation();


    }

    public void generatequestion() {

        button0.setRotation(rotation);
        button1.setRotation(rotation);
        button2.setRotation(rotation);
        button3.setRotation(rotation);
        try {
            switch (str) {
                case "a":
                    symbol = '+';

                    break;
                case "b":
                    symbol = '-';

                    break;
                case "c":
                    symbol = '*';

                    break;
                case "d":
                    symbol = '÷';

                    break;
                case "r":
                    try {
                        int character;
                        character = 42 + rand.nextInt(6);
                        while (character == 44 || character == 46) {
                            character = 42 + rand.nextInt(6);
                        }
                        symbol = (char) character;
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        if (symbol == '+') {
            a = rand.nextInt(51);
            b = rand.nextInt(51);
            questiontext.setText(Integer.toString(a) + " " + symbol + " " + Integer.toString(b));
            num = a + b;


        } else if (symbol == '-') {
            a = rand.nextInt(51);
            b = rand.nextInt(51);
            while (a < b) {
                b = rand.nextInt(51);
            }
            questiontext.setText(Integer.toString(a) + " " + symbol + " " + Integer.toString(b));
            num = a - b;

        } else if (symbol == '*') {
            int a = rand.nextInt(21);
            int b = rand.nextInt(21);
            questiontext.setText(Integer.toString(a) + " " + symbol + " " + Integer.toString(b));
            num = a * b;

        } else {
            try {
                b = 1 + rand.nextInt(25);
                a = b * rand.nextInt(50);
                questiontext.setText(Integer.toString(a) + " " + "÷" + " " + Integer.toString(b));
                try {
                    num = a / b;
                } catch (Exception e) {
                    Log.e("divisionerror", "division_error");
                }

            } catch (Exception e) {
                Log.e("divisionerror", "division_error");
            }
        }
        answerplace = rand.nextInt(4);
        oneincorrectplace = rand.nextInt(4);
        while (oneincorrectplace == answerplace) {
            oneincorrectplace = rand.nextInt(4);
        }
        answerlist.clear();
        for (int i = 0; i < 4; i++) {
            if (i == answerplace) {

                answerlist.add(num);

            } else if (i == oneincorrectplace) {

                incorrectanswer = rand.nextInt(81);
                while (incorrectanswer == num) {
                    incorrectanswer = rand.nextInt(81);
                }
                answerlist.add(incorrectanswer);
            } else {
                incorrectanswer = (rand.nextInt(9) * 10) + ((a + b) % 10);
                while (incorrectanswer == num) {
                    incorrectanswer = (rand.nextInt(9) * 10) + ((a + b) % 10);
                }
                answerlist.add(incorrectanswer);

            }
        }
        button0.setText(Integer.toString(answerlist.get(0)));
        button1.setText(Integer.toString(answerlist.get(1)));
        button2.setText(Integer.toString(answerlist.get(2)));
        button3.setText(Integer.toString(answerlist.get(3)));

        button0.animate().rotationBy(360).setDuration(200);
        button1.animate().rotationBy(-360).setDuration(200);
        button2.animate().rotationBy(360).setDuration(200);
        button3.animate().rotationBy(-360).setDuration(200);


    }

    public void ChooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(answerplace))) {
            answertext.setText("Correct!");
            score++;
            points += 2;
            points_textview.setText("+2");
            answertext.setTextColor(getColor(R.color.color_green));
            points_textview.setTextColor(getColor(R.color.color_green));


        } else {
            answertext.setText("Wrong!!");
            points -= 1;
            points_textview.setText("-1");
            answertext.setTextColor(getColor(R.color.color_red));
            points_textview.setTextColor(getColor(R.color.color_red));
        }


        new CountDownTimer(600, 300) {

            @Override
            public void onTick(long millisUntilFinished) {
                points_textview.animate().alpha(1f).setDuration(350);
                answertext.animate().alpha(1f).setDuration(350);
            }

            @Override
            public void onFinish() {
                points_textview.animate().alpha(0f).setDuration(100);
                answertext.animate().alpha(0f).setDuration(100);
            }
        }.start();

        totalquestion++;
        if (score < 10) {
            scoretext.setText("0" + Integer.toString(score) + "/" + Integer.toString(totalquestion));
        } else {
            scoretext.setText(Integer.toString(score) + "/" + Integer.toString(totalquestion));

        }
        point_textview.setText(Integer.toString(points));
        generatequestion();


    }


    public void choosequestiontype(View view) {


        str = view.getTag().toString();
        relativeLayout.setTranslationX(1000f);
        new CountDownTimer(2000, 2000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // relativeLayout1.animate().translationXBy(1000f).setDuration(2000);
                relativeLayout1.animate().rotationBy(360).alpha(0).setDuration(2000);
                relativeLayout.animate().translationXBy(-1000f).setDuration(2000);
            }

            @Override
            public void onFinish() {
                //relativeLayout1.setTranslationX(-1000f);
                relativeLayout1.setAlpha(1);
                relativeLayout1.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }.start();
        playagain(findViewById(R.id.playagain));


    }

    public void start(View view) {

        button_add.setTranslationX(-1000f);
        button_sub.setTranslationX(1000f);
        button_mul.setTranslationX(-1000f);
        button_div.setTranslationX(1000f);
        button_rand.setTranslationX(-1000f);


        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                startButton.animate().rotationBy(360).translationXBy(1000f).setDuration(1000);
            }

            @Override
            public void onFinish() {
                startButton.setVisibility(View.INVISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);
                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        button_add.animate().translationXBy(1000f).setDuration(1000);
                        button_sub.animate().translationXBy(-1000f).setDuration(1000);
                        button_mul.animate().translationXBy(1000f).setDuration(1000);
                        button_div.animate().translationXBy(-1000f).setDuration(1000);
                        button_rand.animate().translationXBy(1000f).setDuration(1000);
                    }

                    @Override
                    public void onFinish() {
                        Log.i("finished", "timer finished");
                    }
                }.start();
            }
        }.start();
    }

    public void playagain(View view) {


        timertext.setText("30s");
        score = 0;
        totalquestion = 0;
        scoretext.setText("0/0");
        answertext.setText("");
        points = 0;
        point_textview.setText(Integer.toString(points));
        relativeLayout.setVisibility(View.VISIBLE);
        highscore_textview.setText("Highscore is: " + highscore);
        relativeLayout3.setVisibility(View.INVISIBLE);

        generatequestion();

        time(62000);
/*
        countDownTimer=new CountDownTimer(62000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;

                if (seconds < 10) {
                    timertext.setText("0" + String.valueOf(millisUntilFinished / 1000) + "s");
                } else {
                    timertext.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                }


            }

            @Override
            public void onFinish() {

                timertext.setText("0");
                answertext.setText("Done!!");

                if (points > highscore) {
                    highscore = points;
                }
                //relativeLayout4.setVisibility(View.INVISIBLE);


                savedata();
                relativeLayout.setVisibility(View.INVISIBLE);

                relativeLayout3.setVisibility(View.VISIBLE);


                finalscore.setText("Your Score is: " + Integer.toString(score) + "/" + Integer.toString(totalquestion) + "\nTotal points: " + points);


            }
        }.start();
*/
    }

    public void home(View view) {

        relativeLayout1.setVisibility(View.VISIBLE);
        button_add.setTranslationX(-1000f);
        button_sub.setTranslationX(1000f);
        button_mul.setTranslationX(-1000f);
        button_div.setTranslationX(1000f);
        button_rand.setTranslationX(-1000f);

        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                button_add.animate().translationXBy(1000f).setDuration(1000);
                button_sub.animate().translationXBy(-1000f).setDuration(1000);
                button_mul.animate().translationXBy(1000f).setDuration(1000);
                button_div.animate().translationXBy(-1000f).setDuration(1000);
                button_rand.animate().translationXBy(1000f).setDuration(1000);

            }

            @Override
            public void onFinish() {
                Log.i("finished", "timer finished");
            }
        }.start();
        relativeLayout3.setVisibility(View.INVISIBLE);
        //relativeLayout4.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);

    }

    public void savedata() {

        SharedPreferences sharedPreferences = getSharedPreferences(Shared_prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(String.valueOf(temp_highscore), highscore);
        editor.apply();
    }

    public void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_prefs, MODE_PRIVATE);
        highscore = sharedPreferences.getInt(String.valueOf(temp_highscore), 0);
    }

    @Override
    public void onBackPressed() {

        if (relativeLayout.getVisibility() == View.VISIBLE) {
            countDownTimer.cancel();
            new AlertDialog.Builder(this).setTitle("Do you want to quit!!!").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    start(findViewById(R.id.rl2));
                    countDownTimer.cancel();

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String s = timertext.getText().toString().substring(0, 2);
                    int msec = Integer.parseInt(s);
                    time(msec * 1000 + 100);

                }
            }).setCancelable(false).show();
        } else {

            new AlertDialog.Builder(this).setTitle("App band karna h, pakaa!!").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("No", null)
                    .setCancelable(false).
                    show();
        }

    }

    public void time(long milisec) {

        countDownTimer = new CountDownTimer(milisec, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;

                if (seconds < 10) {
                    timertext.setText("0" + String.valueOf(millisUntilFinished / 1000) + "s");
                } else {
                    timertext.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                }


            }

            @Override
            public void onFinish() {

                timertext.setText("0");
                answertext.setText("Done!!");

                if (points > highscore) {
                    highscore = points;
                }
                //relativeLayout4.setVisibility(View.INVISIBLE);

                /*try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                savedata();
                relativeLayout.setVisibility(View.INVISIBLE);

                relativeLayout3.setVisibility(View.VISIBLE);


                finalscore.setText("Your Score is: " + Integer.toString(score) + "/" + Integer.toString(totalquestion) + "\nTotal points: " + points);


            }
        }.start();

    }


}
