package controller;

public class Well {
    private static Well ourInstance = new Well();

    public static Well getInstance() {
        return ourInstance;
    }

    private Well() {
    }
}
