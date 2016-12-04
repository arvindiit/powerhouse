package com.powerhouse.assignment.model;


import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Connection {

    String connectionId;

    String profileId;

    List<MeterReading> meterReadings = new ArrayList<>();

    public Connection(String connectionId, String profileId) {
        this.connectionId = connectionId;
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public List<MeterReading> getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(List<MeterReading> meterReadings) {
        this.meterReadings = meterReadings;
    }

    public void addMeterReading(MeterReading meterReading){
        meterReadings.add(meterReading);
    }

    public Map<String, Integer> getMeterReading(){
        return meterReadings.stream().collect(
                Collectors.toMap(MeterReading::getMonth, MeterReading::getReading));
    }

    public boolean isValid() throws ValidationException {
        if(meterReadings.size() != 12){
            throw new ValidationException("Size of meter reading should be 12 for profile: "+profileId);
        }

        meterReadings.sort((MeterReading o1, MeterReading o2)-> ((Integer)Utils.getMonth(o1.getMonth())).compareTo(Utils.getMonth(o2.getMonth())));

        for(int i = 1; i < meterReadings.size(); i++){
            if(meterReadings.get(i).getReading() < meterReadings.get(i-1).getReading()){
                throw new ValidationException("For Profile: "+profileId+" Reading for month: "+meterReadings.get(i).getMonth()+" is less than previous month");
            }
        }
        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(o instanceof Profile){
            Profile profile = (Profile) o;

            if (profileId != null ? !profileId.equals(profile.profileId) : profile.profileId != null) return false;
            return meterReadings != null ? meterReadings.equals(profile.getFractions()) : profile.getFractions() == null;
        }else if(o instanceof Connection) {
            Connection that = (Connection) o;

            if (profileId != null ? !profileId.equals(that.profileId) : that.profileId != null) return false;
            return meterReadings != null ? meterReadings.equals(that.meterReadings) : that.meterReadings == null;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
       return 0;
    }
}
