package org.starmine.nutrient;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class FoodList extends AppCompatActivity {
    //서치뷰 추가
    ListView listView_food;
    ArrayList<String> foodList;
    ArrayList<String> sizeList;
    ArrayList<String> calList;

    Button toCal;

    DBHelper Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);

        toCal = findViewById(R.id.toCal_Btn);

        toCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodList.this,Food_Calculate.class);
                startActivity(intent);
            }
        });



        listView_food = findViewById(R.id.Food_ListView);
        foodList = new ArrayList<>();
        calList = new ArrayList<>();
        sizeList = new ArrayList<>();
        viewData();
    }

    private class FoodAdapter extends ArrayAdapter<String>{
        private int layout;
        private FoodAdapter(Context context, int resource, List<String> objects){
            super(context,resource,objects);
            layout = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder mainViewHolder = null;
            if (convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,viewGroup,false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.item_name = (TextView)convertView.findViewById(R.id.ItemName_Text);
                viewHolder.item_size= (TextView)convertView.findViewById(R.id.ItemSize_Text);
                viewHolder.item_cal = (TextView)convertView.findViewById(R.id.ItemCal_Text);
                viewHolder.List_calculate = (Button)convertView.findViewById(R.id.Check_Btn);

                viewHolder.relativeLayout = (RelativeLayout)convertView.findViewById(R.id.tester);

                viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();

                        Intent intent = new Intent(FoodList.this,Food_Detail.class);
                        intent.putExtra("food",selFood);
                        startActivity(intent);
                    }
                });
                viewHolder.List_calculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();
                        Insert_Bookmark(selFood);
                    }
                });

                convertView.setTag(viewHolder);
            }else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.item_name.setText(getItem(position));
                mainViewHolder.item_size.setText(sizeList.get(position));
                mainViewHolder.item_cal.setText((CharSequence) calList.get(position));
            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.item_name.setText(getItem(position));
            mainViewHolder.item_size.setText(sizeList.get(position));
            mainViewHolder.item_cal.setText((CharSequence) calList.get(position));
            return convertView;
        }


        public class ViewHolder{
            //이미지 추가
            RelativeLayout relativeLayout;
            TextView item_name;
            TextView item_cal;
            TextView item_size;
            Button List_calculate;
        }
    }
    private void viewData(){

        SQLiteDatabase sqlDB;
        Helper = new DBHelper(FoodList.this, "food.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        String sql_line = "select * from food_nutrient";
        Cursor cursor_food = sqlDB.rawQuery(sql_line, null);


        //cursor_food.moveToFirst();
        while (cursor_food.moveToNext()){
            foodList.add(cursor_food.getString(1));
            calList.add(cursor_food.getString(2));
            sizeList.add(cursor_food.getString(17));
        }


        listView_food.setAdapter(new FoodAdapter(this,R.layout.foodlist_item,foodList));
    }
    private void Insert_Bookmark(String name){

        SQLiteDatabase sqlDB;
        Helper = new DBHelper(FoodList.this, "food.db", null, 1);
        sqlDB = Helper.getWritableDatabase();
        Helper.onCreate(sqlDB);

        String sql = "insert into food_checking values (" + "\""+ name + "\"" +")";

        sqlDB.execSQL(sql);


        viewData();
    }
}
