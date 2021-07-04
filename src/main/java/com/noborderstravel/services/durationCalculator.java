package com.noborderstravel.services;

public class durationCalculator {
    public String calc(String current, String addition){
//        System.out.println("cur:" + current);
//        System.out.println("add: " + addition);
        int temp_i_hour = 0;
        int temp_i_day = 0;
        String[] splitet_current = current.split(":");
        String[] splited_addition = addition.split(":");
        int calculated_i_min = Integer.parseInt(splitet_current[2]) + Integer.parseInt(splited_addition[2]);
        if(calculated_i_min>59){
            if(calculated_i_min>119){
                calculated_i_min = calculated_i_min - 120;
                temp_i_hour = 2 ;
            }else {
                calculated_i_min = calculated_i_min - 60;
                temp_i_hour = 1;
            }
        }
        int calculated_i_hour = Integer.parseInt(splitet_current[1]) + Integer.parseInt(splited_addition[1]);
        if(calculated_i_hour>23){
            if(calculated_i_hour>47){
                calculated_i_hour = calculated_i_hour - 48;
                temp_i_day = 2 ;
            }else {
                calculated_i_hour = calculated_i_hour -24;
                temp_i_day = 1;
            }
        }

        int calculated_i_day = Integer.parseInt(splitet_current[0]) + Integer.parseInt(splited_addition[0]);
        int minutes = calculated_i_min;
        int hours = calculated_i_hour + temp_i_hour;
        int days = calculated_i_day + temp_i_day;
        String s_minutes = String.valueOf(minutes);
        String s_hours = String.valueOf(hours);
        String s_days = String.valueOf(days);
        if(s_minutes.length()==1){
            s_minutes = "0"+ s_minutes;
        }
        if(s_hours.length()==1){
            s_hours = "0"+ s_hours;
        }
        if(s_days.length()==1){
            s_days = "0"+ s_days;
        }
//        System.out.println("my cacls: "+days+"d "+hours+"h "+ minutes + "'");
//        System.out.println("my cacls formatted: "+s_days+":"+s_hours+":"+s_minutes + "");

        String result = s_days+":"+s_hours+":"+s_minutes;
        return result;
    }

    public String formatter(String duration){
        if(duration.substring(duration.length() - 1).equals(":")){
            duration += "00";
            System.out.println("\n\n\n\nQETU\n\n\n\n");
        }
        String[] splitet_duration = duration.split(":");
        String days = splitet_duration[0];
        String hours = splitet_duration[1];
        String minutes = splitet_duration[2];

        String result ="";
        if(days.equals("00") || days.equals("0")){
            result = hours+"h "+ minutes + "'";
        }else{
            result = days+"d "+hours+"h "+ minutes + "'";
        }
        return result;
    }
}
