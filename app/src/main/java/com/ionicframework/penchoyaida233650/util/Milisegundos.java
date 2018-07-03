package com.ionicframework.penchoyaida233650.util;

/**
 * Created by Santiago on 27/05/2016.
 */
public class Milisegundos {
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        String minutString="";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        if (minutes < 10) {
            minutString = "0" + minutes;
        } else {
            minutString = "" + minutes;
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutString + ":" + secondsString;
        // return timer string
        return finalTimerString;
    }
}
