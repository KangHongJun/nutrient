package org.starmine.nutrient;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.StreamSupport;

public class Food_Detail extends AppCompatActivity {
    String curFood;
    TextView foodName,brand;
    static Cursor cursor_foodDetail;

    //알레르기
    TextView Allergy1,Allergy2,Allergy3,Allergy4,Allergy5,Allergy6;

    //영양성분
    TextView kcal,carbohydrate,sugar,protein,calcium,fat,cholesterol,sodium;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);

        Allergy1 = findViewById(R.id.Allergy1_Text);
        Allergy2 = findViewById(R.id.Allergy2_Text);
        Allergy3 = findViewById(R.id.Allergy3_Text);
        Allergy4 = findViewById(R.id.Allergy4_Text);
        Allergy5 = findViewById(R.id.Allergy5_Text);
        Allergy6 = findViewById(R.id.Allergy6_Text);



        foodName = findViewById(R.id.FoodName_Text);
        kcal = findViewById(R.id.Food_kcal_Text);
        carbohydrate = findViewById(R.id.Food_carbohydrate_Text);
        sugar = findViewById(R.id.Food_sugar_Text);
        protein = findViewById(R.id.Food_protein_Text);
        calcium = findViewById(R.id.Food_calcium_Text);
        fat = findViewById(R.id.Food_fat_Text);
        cholesterol = findViewById(R.id.Food_cholesterol_Text);
        sodium = findViewById(R.id.Food_sodium_Text);
        brand= findViewById(R.id.Brand_Text);

        Intent food_intent = getIntent();
        curFood = food_intent.getStringExtra("food");
        foodName.setText(curFood);

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Food_Detail.this, "food.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        System.out.println(curFood+"출력");

        String sql_line = "select * from food_nutrient where Name = " + "\"" + curFood + "\"" + "";
        cursor_foodDetail = sqlDB.rawQuery(sql_line, null);


        //cursor_food.moveToFirst();
        while (cursor_foodDetail.moveToNext()){
            System.out.println(cursor_foodDetail.getString(1));
            System.out.println(cursor_foodDetail.getString(2));
            kcal.setText(cursor_foodDetail.getString(2));
            sugar.setText(cursor_foodDetail.getInt(9)+"g");
            sodium.setText(cursor_foodDetail.getInt(10)+"mg");
            carbohydrate.setText(cursor_foodDetail.getInt(11)+"g");
            protein.setText(cursor_foodDetail.getInt(12)+"g");
            calcium.setText(cursor_foodDetail.getInt(13)+"g");
            cholesterol.setText(cursor_foodDetail.getInt(14)+"mg");
            fat.setText(cursor_foodDetail.getInt(15)+"g");
            brand.setText(cursor_foodDetail.getString(16));

        }




        setAllergy();

        //사진, 이름, 알레르기, 영양정보 정보 세팅
    }
    public void  setDetail(){

    }
    public void  setAllergy(){

        cursor_foodDetail.moveToFirst();
        for(int i = 3;i<9;i++){
            int check = cursor_foodDetail.getInt(i);
            switch (i){
                case 3:
                    if (check==0){
                        Allergy1.setText("X");
                    }else{
                        Allergy1.setText("O");
                    }
                    break;
                case 4:
                    if (check==0){
                        Allergy2.setText("X");
                    }else{
                        Allergy2.setText("O");
                    }
                    break;
                case 5:
                    if (check==0){
                        Allergy3.setText("X");
                    }else{
                        Allergy3.setText("O");
                    }
                    break;
                case 6:
                    if (check==0){
                        Allergy4.setText("X");
                    }else{
                        Allergy4.setText("O");
                    }
                    break;
                case 7:
                    if (check==0){
                        Allergy5.setText("X");
                    }else{
                        Allergy5.setText("O");
                    }
                    break;
                case 8:
                    if (check==0){
                        Allergy6.setText("X");
                    }else{
                        Allergy6.setText("O");
                    }
                    break;
                default:
                    break;
            }
        }


    }
}

