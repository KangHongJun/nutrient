package org.starmine.nutrient;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FoodList extends AppCompatActivity {
    ListView listView_food;
    ArrayList<String> foodList;
    ArrayList<String> sizeList;
    ArrayList<String> calList;
    ArrayList<String> imageList;

    ArrayList<String> filterList;

    //이미지 저장
    static HashMap<String,Integer> images = new HashMap<String,Integer>();

    Button toCal;
    SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor_food;
    String sql_line;

    DBHelper Helper;


    //나중에 food2 테이블로 수정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);

        //이미지 삽입
        images.put("shinramen",Integer.valueOf(R.drawable.shinramen));
        images.put("chapagetti",Integer.valueOf(R.drawable.chapagetti));
        images.put("neoguri",Integer.valueOf(R.drawable.neoguri));
        images.put("chalbibimmyun",Integer.valueOf(R.drawable.chalbibimmyun));
        images.put("shinnonfrying",Integer.valueOf(R.drawable.shinnonfrying));
        images.put("bananakick",Integer.valueOf(R.drawable.bananakick));
        images.put("potatochip",Integer.valueOf(R.drawable.patatochip));
        images.put("cornchips",Integer.valueOf(R.drawable.cornchips));
        images.put("honeybutterchip",Integer.valueOf(R.drawable.honeybutterchip));
        images.put("sweetpotatocrackers",Integer.valueOf(R.drawable.sweetpotatocrackers));
        images.put("fullmoon",Integer.valueOf(R.drawable.fullmoon));
        images.put("readbeanbread",Integer.valueOf(R.drawable.redbeanbread));
        images.put("pokemon1",Integer.valueOf(R.drawable.pokemon1));
        images.put("pokemon2",Integer.valueOf(R.drawable.pokemon2));
        images.put("donut",Integer.valueOf(R.drawable.donut));
        images.put("milkis",Integer.valueOf(R.drawable.milkis));
        images.put("pepsizero",Integer.valueOf(R.drawable.pepsizero));
        images.put("biraksikhye",Integer.valueOf(R.drawable.biraksikhye));
        images.put("pocarisweat",Integer.valueOf(R.drawable.pocarisweat));
        images.put("cocoparm",Integer.valueOf(R.drawable.cocoparm));




        Toolbar toolbar = findViewById(R.id.List_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("선택 알러지");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

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
        imageList = new ArrayList<>();
        filterList = new ArrayList<>();


        viewData();

        listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selFood = adapterView.getItemAtPosition(i).toString();
                //Helper.In(selFood);
                viewData();
                Intent intent = new Intent(FoodList.this,Food_Detail.class);
                intent.putExtra("food",selFood);
                startActivity(intent);

                finish();
            }
        });

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
                viewHolder.item_image = (ImageView) convertView.findViewById(R.id.Item_Image);

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

            String a = imageList.get(position);
            System.out.println(a + "List");
            mainViewHolder.item_image.setImageResource(images.get(a));
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

        //음식 코드 값
        int food_code;
        Intent code_intent = getIntent();
        food_code = code_intent.getIntExtra("code",1);

        System.out.println(food_code + "코드값 전달");

        SQLiteDatabase sqlDB;
        Helper = new DBHelper(FoodList.this, "food.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //코드에 따라
        switch (food_code){
            case 0 :
                sql_line = "select * from food_nutrient where Type = 0";
                cursor_food = sqlDB.rawQuery(sql_line, null);
                break;
            case 1 :
                sql_line = "select * from food_nutrient where Type = 1";
                cursor_food = sqlDB.rawQuery(sql_line, null);
                break;
            case 2 :
                sql_line = "select * from food_nutrient where Type = 2";
                cursor_food = sqlDB.rawQuery(sql_line, null);
                break;
            case 3 :
                sql_line = "select * from food_nutrient where Type = 3";
                cursor_food = sqlDB.rawQuery(sql_line, null);
                break;
            default:
                sql_line = "select * from food2";
                cursor_food = sqlDB.rawQuery(sql_line, null);
        }

        cursor_food.moveToFirst();
        while (cursor_food.moveToNext()){
            foodList.add(cursor_food.getString(1));
            calList.add(cursor_food.getString(2));
            sizeList.add(cursor_food.getString(17));
            imageList.add(cursor_food.getString(18));
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

    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
            case R.id.menu_search:{
                Intent intent = new Intent(FoodList.this,Food_Search.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
