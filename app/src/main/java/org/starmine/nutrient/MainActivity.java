package org.starmine.nutrient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    public String result= "";

    Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectallergy);
        SharedPreferences sp = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        final CheckBox cb1 = (CheckBox) findViewById(R.id.crustaceans);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.milk);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.peanut);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.egg);
        final CheckBox cb5 = (CheckBox) findViewById(R.id.fish);
        final CheckBox cb6 = (CheckBox) findViewById(R.id.flour);

        button1 = (Button) findViewById(R.id.choicebutton);
        button2 = (Button) findViewById(R.id.initbutton);

        if(sp.getString("crab","F").equals("T")) cb1.setChecked(true);
        else cb1.setChecked(false);
        if(sp.getString("milk","F").equals("T")) cb2.setChecked(true);
        else cb2.setChecked(false);
        if(sp.getString("peanut","F").equals("T")) cb3.setChecked(true);
        else cb3.setChecked(false);
        if(sp.getString("egg","F").equals("T")) cb4.setChecked(true);
        else cb4.setChecked(false);
        if(sp.getString("fish","F").equals("T")) cb5.setChecked(true);
        else cb5.setChecked(false);
        if(sp.getString("flour","F").equals("T")) cb6.setChecked(true);
        else cb6.setChecked(false);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cb1.isChecked() == true) { result += "갑각류알레르기."; editor.putString("crab","T"); editor.apply();}
                else {editor.putString("crab","F"); editor.apply();}
                if (cb2.isChecked() == true) { result += "유제품알레르기.";  editor.putString("milk","T"); editor.apply();}
                else {editor.putString("milk","F"); editor.apply();}
                if (cb3.isChecked() == true) { result += "땅콩/대두알레르기."; editor.putString("peanut","T"); editor.apply();}
                else {editor.putString("peanut","F"); editor.apply();}
                if (cb4.isChecked() == true) { result += "계란알레르기."; editor.putString("egg","T"); editor.apply();}
                else {editor.putString("egg","F"); editor.apply();}
                if (cb5.isChecked() == true) { result += "생선알레르기.";  editor.putString("fish","T"); editor.apply();}
                else {editor.putString("fish","F"); editor.apply();}
                if (cb6.isChecked() == true) { result += "밀가루알레르기.";  editor.putString("flour","T"); editor.apply();}
                else {editor.putString("flour","F"); editor.apply();}

                Intent intent = new Intent(getApplicationContext(), selectallergy.class);
                intent.putExtra("allergy",result);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                cb5.setChecked(false);
                cb6.setChecked(false);

                editor.clear();
                editor.commit();
            }
        });

    }

}