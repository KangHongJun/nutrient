package org.starmine.nutrient;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Food_Calculate extends AppCompatActivity {
    DBHelper Helper;
    ListView checkListView_food;

    ArrayList<String> foodList;
    ArrayList<String> sizeList;
    ArrayList<String> calList;
    ArrayList<String> imageList;



    TextView calculate;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_calculate);

        checkListView_food = findViewById(R.id.Food_CheckListView);
        calculate = findViewById(R.id.calculate_Text);

        foodList = new ArrayList<>();
        calList = new ArrayList<>();
        sizeList = new ArrayList<>();
        imageList = new ArrayList<>();




        viewData();





    }
    private class FoodAdapter extends ArrayAdapter<String> {
        private int layout;
        private FoodAdapter(Context context, int resource, List<String> objects){
            super(context,resource,objects);
            layout = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            FoodAdapter.ViewHolder mainViewHolder = null;
            if (convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,viewGroup,false);

                FoodAdapter.ViewHolder viewHolder = new FoodAdapter.ViewHolder();
                viewHolder.item_name = (TextView)convertView.findViewById(R.id.ItemName_Text);
                viewHolder.item_size= (TextView)convertView.findViewById(R.id.ItemSize_Text);
                viewHolder.item_cal = (TextView)convertView.findViewById(R.id.ItemCal_Text);
                viewHolder.List_calculate = (Button)convertView.findViewById(R.id.Check_Btn);
                viewHolder.item_image = (ImageView) convertView.findViewById(R.id.Item_Image);



                viewHolder.relativeLayout = (RelativeLayout)convertView.findViewById(R.id.tester);

                viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();

                        Intent intent = new Intent(Food_Calculate.this,Food_Detail.class);
                        intent.putExtra("food",selFood);
                        startActivity(intent);
                    }
                });
                viewHolder.List_calculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();
                        // 삭제로 Insert_Bookmark(selFood);
                    }
                });

                convertView.setTag(viewHolder);
            }else{
                mainViewHolder = (FoodAdapter.ViewHolder) convertView.getTag();
                mainViewHolder.item_name.setText(getItem(position));
                mainViewHolder.item_size.setText(sizeList.get(position));
                mainViewHolder.item_cal.setText((CharSequence) calList.get(position) + "kcal");
            }
            mainViewHolder = (FoodAdapter.ViewHolder) convertView.getTag();
            mainViewHolder.item_name.setText(getItem(position));
            mainViewHolder.item_size.setText(sizeList.get(position));
            mainViewHolder.item_cal.setText((CharSequence) calList.get(position) + "kcal");

            //이미지
            String a = imageList.get(position);
            System.out.println(a + "Cal");
            mainViewHolder.item_image.setImageResource(FoodList.images.get(a).intValue());
            return convertView;
        }


        public class ViewHolder{
            //이미지 추가
            RelativeLayout relativeLayout;
            TextView item_name;
            TextView item_cal;
            TextView item_size;
            Button List_calculate;
            ImageView item_image;
        }
    }
    private void viewData(){
        int calculate_result = 0;
        int protein_result = 0;
        int sodium_result = 0;
        int calcium_result = 0;

        String checkList[] = new String[100];

        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Food_Calculate.this, "food.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);



        String sql_check = "select * from food_checking";
        Cursor cursor_food = sqlDB.rawQuery(sql_check, null);
        int i = 0;
        while (cursor_food.moveToNext()){
            if (cursor_food.getString(0) == null){
                break;
            }else{
                checkList[i] = cursor_food.getString(0);
            }
            i++;
        }


        String sql_view = "select * from food2";
        cursor_food = sqlDB.rawQuery(sql_view, null);

        int j = 0;
        //cursor_food.moveToFirst();


        for (int count=0;count<checkList.length;count++){
            cursor_food.moveToFirst();

            while (cursor_food.moveToNext()){
                System.out.println(checkList[j]+"//"+cursor_food.getString(1));
                if (checkList[j]==null){
                    break;
                }else if (checkList[j].equals(cursor_food.getString(1))){
                    foodList.add(cursor_food.getString(1));
                    calList.add(cursor_food.getString(2));

                    //총 합계
                    calculate_result = calculate_result + Integer.parseInt(cursor_food.getString(2));
                    sodium_result = sodium_result + Integer.parseInt(cursor_food.getString(10));
                    protein_result = protein_result + Integer.parseInt(cursor_food.getString(12));
                    calcium_result = calcium_result + Integer.parseInt(cursor_food.getString(13));





                    calculate.setText("총 칼로리 : " +  calculate_result+"kcal" + " 나트륨 : "+ sodium_result + "mg" + " 단백질 : " + protein_result +"g" + " 칼슘 : " +calcium_result + "mg");


                    sizeList.add(cursor_food.getString(17));
                    imageList.add(cursor_food.getString(18));

                    //System.out.println(cursor_food.getString(1));
                    //sizeList.add(cursor_food.getString(17));
                    j++;

                }
            }
        }
        checkListView_food.setAdapter(new FoodAdapter(this,R.layout.foodlist_item,foodList));
    }
}

