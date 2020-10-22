package anichan.bookmark;

import anichan.exception.AniException;

import java.text.SimpleDateFormat;

public interface DateTime {
    SimpleDateFormat stringToDateTime = new SimpleDateFormat("dd/MM/yyyy HHmm");
    SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateToString = new SimpleDateFormat("dd MMM yyyy hh:mmaa");

    public void parseToDate(String input) throws AniException;
}
