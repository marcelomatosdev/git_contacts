package com.example.gitcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvSettings;
    private TextView tvMainTitle;
    private Spinner spFontSize;
    private Spinner spFontColor;
    private Spinner spFontStyle;
    private Spinner spBackgroundColor;
    private String selectedFontSize;
    private String selectedFontColor;
    private String selectedBackgroundColor;
    private int selectedFontStyle;
    private Button btnSaveSettings;
    private Button btnCancelSettings;
    //private TextView tvFeed;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvSettings = findViewById(R.id.tvSettings);
        tvMainTitle = findViewById(R.id.tvMainTitle);
        spFontSize = findViewById(R.id.spFontSize);
        spFontColor = findViewById(R.id.spFontColor);
        spFontStyle = findViewById(R.id.spFontStyle);
        spBackgroundColor = findViewById(R.id.spBackgroundColor);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnCancelSettings = findViewById(R.id.btnCancelSettings);

        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        //Spinner Font Size
        spFontSize.setSelection(0);
        ArrayAdapter<CharSequence> adapter_font_size = ArrayAdapter.createFromResource(this,
                R.array.font_size_array, android.R.layout.simple_spinner_item);
        adapter_font_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFontSize.setAdapter(adapter_font_size);
        //

        //Spinner Font Color
        spFontColor.setSelection(0);
        ArrayAdapter<CharSequence> adapter_font_color = ArrayAdapter.createFromResource(this,
                R.array.font_color_array, android.R.layout.simple_spinner_item);
        adapter_font_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFontColor.setAdapter(adapter_font_color);
        //

        //Spinner Font Style
        spFontStyle.setSelection(0);
        ArrayAdapter<CharSequence> adapter_font_style = ArrayAdapter.createFromResource(this,
                R.array.font_style_array, android.R.layout.simple_spinner_item);
        adapter_font_style.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFontStyle.setAdapter(adapter_font_style);
        //

        //Spinner Background Color
        spBackgroundColor.setSelection(0);
        ArrayAdapter<CharSequence> adapter_background_color = ArrayAdapter.createFromResource(this,
                R.array.background_color_array, android.R.layout.simple_spinner_item);
        adapter_background_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBackgroundColor.setAdapter(adapter_background_color);
        //

        EventHandler eventHandler = new EventHandler();
        spFontSize.setOnItemSelectedListener(eventHandler);
        spFontColor.setOnItemSelectedListener(eventHandler);
        spFontStyle.setOnItemSelectedListener(eventHandler);
        spBackgroundColor.setOnItemSelectedListener(eventHandler);
        btnSaveSettings.setOnClickListener(eventHandler);
        btnCancelSettings.setOnClickListener(eventHandler);
    }


    class EventHandler implements View.OnClickListener, AdapterView.OnItemSelectedListener{
        @Override
        public void onClick(View view) {
            //what View (UI widget) was clicked?
            switch (view.getId()) {
                case R.id.btnSaveSettings:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fontSize", selectedFontSize);
                    editor.putString("fontColor", selectedFontColor);
                    editor.putInt("fontStyle", selectedFontStyle);
                    editor.putString("backgroundColor", selectedBackgroundColor);
                    editor.commit();

                    onBackPressed();
                    break;

                case R.id.btnCancelSettings:

                    onBackPressed();
                    break;

            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.spFontSize:
                    if (position>0){
                        selectedFontSize = parent.getItemAtPosition(position).toString();
                    }
                    if (position<=0){
                        selectedFontSize = "20";
                    }
                    int fontSize = Integer.parseInt(selectedFontSize);
                    tvMainTitle.setTextSize((fontSize));
                    break;

                case R.id.spFontColor:
                    if (position>0){
                        //selectedFontColor = "#00ff00";
                        selectedFontColor = parent.getItemAtPosition(position).toString();
                    }
                    if (position<=0){
                        selectedFontColor = "BLACK";
                    }
                    int fontColor = Color.parseColor(selectedFontColor);
                    tvMainTitle.setTextColor((fontColor));
                    break;

                case R.id.spFontStyle:
                    if (position==0||position==1){
                        selectedFontStyle = R.style.NormalText;
                        tvMainTitle.setTextAppearance(getApplicationContext(), selectedFontStyle);
                    }
                    if (position==2){
                        selectedFontStyle = R.style.BoldText;
                        tvMainTitle.setTextAppearance(getApplicationContext(), selectedFontStyle);
                    }
                    if (position==3){
                        selectedFontStyle = R.style.ItalicText;
                        tvMainTitle.setTextAppearance(getApplicationContext(), selectedFontStyle);
                    }
                    break;

                case R.id.spBackgroundColor:
                    if (position>0){
                        selectedBackgroundColor = parent.getItemAtPosition(position).toString();
                    }
                    if (position<=0){
                        selectedBackgroundColor = "#FAFAFA";
                    }
                    int backgroundColor = Color.parseColor(selectedBackgroundColor);
                    tvMainTitle.setBackgroundColor((backgroundColor));
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }



    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }




}
