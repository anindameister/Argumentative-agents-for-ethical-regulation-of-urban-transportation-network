package com.anindamaulik.PlatformTerritoire;

public class Couple {

    private Argument x;
    private Argument y;

    public Couple(Argument x, Argument y){

        this.x=x;
        this.y=y;

    }

    public Argument getX() {
        return x;
    }

    public void setX(Argument x) {
        this.x = x;
    }

    public Argument getY() {
        return y;
    }

    public void setY(Argument y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }




}
