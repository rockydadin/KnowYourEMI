package com.ramanuj.utilities;

public class Calc{

public static double payment(double p,double i,int m){
double ratepm=i/1200;
double intpermonth=p*ratepm;
return Math.round(intpermonth/(1-(1/(Math.pow((1+ratepm),m))))*100.00)/100.00;

}

}