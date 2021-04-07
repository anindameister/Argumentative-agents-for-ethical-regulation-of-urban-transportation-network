package com.anindamaulik.PlatformTerritoire;

public class Argument<T> {



    public Argument(T a) {

        this.a = a;
    }

    private T a;

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "Argument{" +
                "a=" + a +
                '}';
    }
}
