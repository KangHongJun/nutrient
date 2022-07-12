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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Food_Search extends AppCompatActivity {

    SearchView searchView;
    ListView listView_search;

    DBHelper dbHelper;
    String[] foodList ={"신라면","짜파게티","너구리"};

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> historyList;
    ArrayList<String> bookmarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_search);

        //툴바
        Toolbar toolbar = findViewById(R.id.Search_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("선택 알러지");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        //초기화
        searchView = findViewById(R.id.SearchView);

        listView_search = findViewById(R.id.Search_ListView);

        //DB
        SQLiteDatabase sqlDB;
        dbHelper = new DBHelper(Food_Search.this, "food.db", null, 1);
        sqlDB = dbHelper.getReadableDatabase();
        dbHelper.onCreate(sqlDB);

        //임시 - 검색기록
        historyList = new ArrayList<>();
        viewData();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);
        listView_search.setAdapter(arrayAdapter);

        listView_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selFood = adapterView.getItemAtPosition(i).toString();
                //Helper.In(selFood);
                historyList.clear();
                viewData();
                Intent intent = new Intent(Food_Search.this, Food_Detail.class);
                intent.putExtra("food",selFood);
                startActivity(intent);

                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Food_Search.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Food_Search.this.arrayAdapter.getFilter().filter(newText);
                System.out.println(newText);
                if (newText.length() >= 1){
                    listView_search.setVisibility(View.VISIBLE);
                }else if (newText.length() == 0){
                    listView_search.setVisibility(View.GONE);
                }

                return false;
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

                viewHolder.relativeLayout = (RelativeLayout)convertView.findViewById(R.id.tester);

                viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();

                        Intent intent = new Intent(Food_Search.this, Food_Detail.class);
                        intent.putExtra("food",selFood);
                        startActivity(intent);
                    }
                });
                viewHolder.List_calculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selFood = viewHolder.item_name.getText().toString();
                        //Insert_Bookmark(selFood);
                    }
                });

                convertView.setTag(viewHolder);
            }else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.item_name.setText(getItem(position));

            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.item_name.setText(getItem(position));

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
        dbHelper = new DBHelper(Food_Search.this, "food.db", null, 1);
        sqlDB = dbHelper.getReadableDatabase();
        dbHelper.onCreate(sqlDB);

        String sql_line = "select * from food_nutrient";
        Cursor cursor_food = sqlDB.rawQuery(sql_line, null);


        //cursor_food.moveToFirst();
        while (cursor_food.moveToNext()){
            historyList.add(cursor_food.getString(1));
        }


        listView_search.setAdapter(new FoodAdapter(this, R.layout.foodlist_item,historyList));
    }
}
