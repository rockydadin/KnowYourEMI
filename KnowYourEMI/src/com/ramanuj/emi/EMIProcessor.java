package com.ramanuj.emi;

import java.util.*;

import com.google.gson.Gson;

public class EMIProcessor{

public String getpayment(Double prin,Double rate, Integer month){
	
ArrayList<ArrayList<String>> emiTable = new ArrayList<ArrayList<String>>();

	
Double emi;
Double openbal;
Double intpaid;
Double prinpaid;
Double netoverdue;
Double closingbal;


emi=Calc.payment(prin,rate,month);
closingbal=prin;
Double intrpm=rate/1200;

for(Integer i=1;i<=month;i++){

ArrayList<String> row = new ArrayList<String>();
row.add(i.toString());
openbal=Math.round(closingbal*100.00)/100.00;
row.add(openbal.toString());
intpaid=Math.round((openbal*intrpm)*100.00)/100.00;
row.add(intpaid.toString());
prinpaid=Math.round((emi-intpaid)*100.00)/100.00;
row.add(prinpaid.toString());
netoverdue=Math.round((openbal+intpaid)*100.00)/100.00;
row.add(netoverdue.toString());
row.add(emi.toString());
closingbal=Math.round((netoverdue-emi)*100.00)/100.00;
row.add(closingbal.toString());
emiTable.add(row);
}

String json = new Gson().toJson(emiTable);

return json;
}
}