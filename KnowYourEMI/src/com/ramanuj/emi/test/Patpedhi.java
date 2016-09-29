package com.ramanuj.emi.test;

import com.ramanuj.emi.view.EMIProcessor;

public class Patpedhi{
public static void main(String [] args){

	EMIProcessor e =new EMIProcessor();

String r = e.getpayment(120000.00, 12.00, 24);

System.out.println(r);

}}