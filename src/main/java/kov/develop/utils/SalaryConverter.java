package kov.develop.utils;

public class SalaryConverter {

    public static String convertFromHh (String fromSum, String toSum, String currency){

        String from = (fromSum.equals("null") ? "" : fromSum);
        String to = (toSum.equals("null") ? "" : toSum);
        currency = (currency.equals("USD") ? "USD" : "руб");
        if (from.equals("") && to.equals(""))
            return "";
        else if (!from.equals("") && !to.equals("") && !from.equals(to))
            return (from + " - " + to + " " + currency);
        else return (from.equals("") ? to : from) + " " + currency;
    }
}
