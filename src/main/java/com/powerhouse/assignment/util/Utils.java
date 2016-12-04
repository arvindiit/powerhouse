package com.powerhouse.assignment.util;


import com.opencsv.CSVReader;
import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.model.Connection;
import com.powerhouse.assignment.model.Fraction;
import com.powerhouse.assignment.model.MeterReading;
import com.powerhouse.assignment.model.Profile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static int getMonth(String month){
        if(month.equalsIgnoreCase("Jan")){
            return 0;
        }
        if(month.equalsIgnoreCase("Feb")){
            return 1;
        }
        if(month.equalsIgnoreCase("Mar")){
            return 2;
        }
        if(month.equalsIgnoreCase("Apr")){
            return 3;
        }
        if(month.equalsIgnoreCase("May")){
            return 4;
        }
        if(month.equalsIgnoreCase("Jun")){
            return 5;
        }
        if(month.equalsIgnoreCase("Jul")){
            return 6;
        }
        if(month.equalsIgnoreCase("Aug")){
            return 7;
        }
        if(month.equalsIgnoreCase("Sep")){
            return 8;
        }
        if(month.equalsIgnoreCase("Oct")){
            return 9;
        }
        if(month.equalsIgnoreCase("Nov")){
            return 10;
        }
        if(month.equalsIgnoreCase("Dec")){
            return 11;
        }

        return -1;
    }

    public static boolean isValidConsumption(Profile profile, Connection connection) throws ValidationException {
        int totalReading = 0;
        for (MeterReading meterReading : connection.getMeterReadings()){
            totalReading += meterReading.getReading();
        }

        Map<String, Double> fractionMap = profile.getFractionMap();
        for (MeterReading meterReading : connection.getMeterReadings()){
            int actualReading = meterReading.getReading();
            double fraction = fractionMap.get(meterReading.getMonth());
            int calculatedReading = (int) (totalReading*fraction);
            int deviation = (int) (calculatedReading*.25);
            if(actualReading < calculatedReading-deviation || actualReading > calculatedReading+deviation){
                throw new ValidationException("Invalid consumption for profile: "+profile.getProfileId()+": in the month of: "+meterReading.getMonth()+"\n Hence removed " +
                        "from connection list");
            }

        }
        return true;
    }

    public static boolean doesProfileContainConnection(Map<String,Profile> map, Connection connection) throws ValidationException {
        List<Profile> list = map.values().stream()				   // Convert to steam
                .filter(x -> x.getProfileId().equalsIgnoreCase(connection.getProfileId())).collect(Collectors.toList());

        if(!list.contains(connection)){
            throw new ValidationException("Connection :"+connection.getProfileId()+" is missing in profile for any month. your job to find out which one :)");
        }
        return list.contains(connection);
    }

    public static Map<String, Profile> createProfileMap(File file) {


        Map<String, Profile> map = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            boolean skipHeader = true;
            while ((line = reader.readNext()) != null) {
                if(skipHeader){
                    skipHeader = false;
                    continue;
                }
                if(line.length != 3){
                    break;
                }
                String profileId = line[1];
                Profile profile = map.get(profileId);
                if(profile == null){
                    profile = new Profile(profileId);
                    map.put(profileId, profile);
                }

                Fraction fraction = new Fraction(line[0], Double.valueOf(line[2]));
                profile.addFraction(fraction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;

    }

    public static Map<String, Connection> createConnectionMap(File file) {

        Map<String, Connection> map = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            boolean skipHeader = true;
            while ((line = reader.readNext()) != null) {
                if(skipHeader){
                    skipHeader = false;
                    continue;
                }
                if(line.length != 4){
                    break;
                }
                String profileId = line[1];
                Connection connection = map.get(profileId);
                if(connection == null){
                    connection = new Connection(line[0], profileId);
                    map.put(profileId, connection);
                }

                MeterReading meterReading = new MeterReading(line[2], Integer.valueOf(line[3]));
                connection.addMeterReading(meterReading);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;

    }

}
