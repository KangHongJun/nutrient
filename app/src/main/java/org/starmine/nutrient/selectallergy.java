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

        if(result==null) {tv.setText("선택된 알레르기가 없습니다.");}
        else {tv.setText("나의 알레르기: " + result);}

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent all_intent=new Intent(selectallergy.this,FoodList.class);
                all_intent.putExtra("code",999);
                startActivity(all_intent);
            }
        });

        //라면
        ramen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ramen_intent = new Intent(selectallergy.this,FoodList.class);
                ramen_intent.putExtra("code",0);
                startActivity(ramen_intent);
            }
        });

        //과자
        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snack_intent = new Intent(selectallergy.this,FoodList.class);
                snack_intent.putExtra("code",1);
                startActivity(snack_intent);

            }
        });

        //빵
        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bread_intent = new Intent(selectallergy.this,FoodList.class);
                bread_intent.putExtra("code",2);
                startActivity(bread_intent);
            }
        });

        //음료수
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drink_intent = new Intent(selectallergy.this,FoodList.class);
                drink_intent.putExtra("code",3);
                startActivity(drink_intent);
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

