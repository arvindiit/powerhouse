package com.powerhouse.assignment.model;


import com.powerhouse.assignment.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Profile {

    String profileId;

    List<Fraction> fractions = new ArrayList<>();

    public Profile(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public void addFraction(Fraction fraction){
        fractions.add(fraction);
    }

    public Map<String, Double> getFractionMap(){
        return fractions.stream().collect(
                Collectors.toMap(Fraction::getMonth, Fraction::getFraction));
    }

    public boolean isValid() throws ValidationException {
        if(fractions.size() != 12){
            throw new ValidationException("Size of fraction should be 12 for profile: "+profileId);
        }
        double sum = fractions.stream().mapToDouble(o -> o.getFraction()).sum();

        if(sum != 1){
            throw new ValidationException("Sum of fraction should be equal to 1 for profile: "+profileId);
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(o instanceof Connection){
            Connection connection = (Connection) o;

            if (profileId != null ? !profileId.equals(connection.profileId) : connection.profileId != null) return false;
            return fractions != null ? fractions.equals(connection.getMeterReadings()) : connection.getMeterReadings() == null;
        }else if(o instanceof Profile) {
            Profile profile = (Profile) o;

            if (profileId != null ? !profileId.equals(profile.profileId) : profile.profileId != null) return false;
            return fractions != null ? fractions.equals(profile.fractions) : profile.fractions == null;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
