package com.powerhouse.assignment.service;

import com.powerhouse.assignment.model.Connection;
import com.powerhouse.assignment.model.MeterReading;
import com.powerhouse.assignment.model.Profile;
import com.powerhouse.assignment.util.Cache;
import com.powerhouse.assignment.util.Utils;
import org.springframework.stereotype.Service;

@Service
public class GenericService {

    public int retrieveConsumption(String month){

        int totalReading = 0;
        for (Connection connection : Cache.getConnectionCache().values()){
            connection.getMeterReadings().sort((MeterReading o1, MeterReading o2)-> ((Integer)Utils.getMonth(o1.getMonth())).compareTo(Utils.getMonth(o2.getMonth())));

            if(month.equalsIgnoreCase("Jan")){
                totalReading += connection.getMeterReadings().get(0).getReading();
            }
            else {
                for (int i = 1; i < connection.getMeterReadings().size(); i++) {
                    if (connection.getMeterReadings().get(i).getMonth().equalsIgnoreCase(month)) {
                        totalReading += connection.getMeterReadings().get(i).getReading() - connection.getMeterReadings().get(i - 1).getReading();
                        break;
                    }
                }
            }
        }

        return totalReading;
    }

    public Profile getProfile(String profileId){
        return Cache.getProfileCache().get(profileId);
    }

    public String updateProfile(Profile profile){
         Cache.getProfileCache().put(profile.getProfileId(), profile);
         return "true";
    }

    public String deleteProfile(String profileId){
        Cache.getProfileCache().remove(profileId);
        return "true";
    }

    public Connection getConnection(String profileId){
        return Cache.getConnectionCache().get(profileId);
    }

    public String updateConnection(Connection connection){
        Cache.getConnectionCache().put(connection.getProfileId(), connection);
        return "true";
    }

    public String deleteConnection(String connectionId){
        Cache.getConnectionCache().remove(connectionId);
        return "true";
    }


}
