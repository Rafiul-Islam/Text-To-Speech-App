package com.example.rafiulislamrafi.texttospeech;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {

    EditText message;
    ImageButton speak_button;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.textField);
        speak_button = (ImageButton) findViewById(R.id.speakButton);


        ///init text to speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {

                    int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    //speak_button.setEnabled(true);
                    textToSpeech.setPitch(0.3f);
                    textToSpeech.setSpeechRate(.50f);

                    speak();
                }

            }
        });

        //init view

        speak_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }


    private void speak() {

        String text = message.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        } else {

            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }

    }

    @Override
    protected void onDestroy() {

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

}
