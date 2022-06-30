package org.starmine.nutrient;

//시간표 - 리사이클 뷰에 들어갈 변수 저장
public class Food {
    private int Item_Image;
    String ItemName_Text;
    String ItemCal_Text;

    public Food(int Item_Image,String ItemName_Text, String ItemCal_Text) {
        this.Item_Image = Item_Image;
        this.ItemName_Text = ItemName_Text;
        this.ItemCal_Text = ItemCal_Text;
    }
    public int getImage(){
        return this.Item_Image;
    }

    public String getNAME(){
        return this.ItemName_Text;
    }

    public String getCal(){
        return this.ItemCal_Text;
    }

    public void setNAME(String ItemName_Text){
        this.ItemName_Text = ItemName_Text;
    }

    public void setCal(String ItemCal_Text){
        this.ItemCal_Text = ItemCal_Text;
    }

    public void setImage(int Item_Image){
        this.Item_Image = Item_Image;
    }
}
