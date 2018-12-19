package Model;

public class Grass {

    //num 0-100
    int num=0;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean getEaten() {
        if (num==0){

        }else {
            num-=1;
        }


    }

}
