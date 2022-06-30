package org.starmine.nutrient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class selectallergy extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton all = (ImageButton) findViewById(R.id.all);
        ImageButton ramen = (ImageButton) findViewById(R.id.ramen);
        ImageButton snack = (ImageButton) findViewById(R.id.snack);
        ImageButton bread = (ImageButton) findViewById(R.id.bread);
        ImageButton drink = (ImageButton) findViewById(R.id.drink);
        ImageButton etc = (ImageButton) findViewById(R.id.etc);
        ImageButton bread1 = (ImageButton) findViewById(R.id.bread1);
        ImageButton drink1 = (ImageButton) findViewById(R.id.drink1);
        ImageButton etd1 = (ImageButton) findViewById(R.id.etc1);

        TextView tv = (TextView) findViewById(R.id.textview1);

        Button btn = (Button) findViewById(R.id.revise);

        Intent intent1 = getIntent();
        String result = intent1.getStringExtra("allergy");

        if(result.equals(null)) {tv.setText("선택된 알레르기가 없습니다.");}
        else {tv.setText("나의 알레르기: " + result);}

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectallergy.this,FoodList.class);
                startActivity(intent);
            }
        });

        ramen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        drink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}

