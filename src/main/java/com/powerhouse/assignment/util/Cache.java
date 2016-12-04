package com.powerhouse.assignment.util;


import com.powerhouse.assignment.model.Connection;
import com.powerhouse.assignment.model.Profile;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private static Map<String, Profile> profileMap = new HashMap<>();
    private static Map<String, Connection> connectionMap = new HashMap<>();

    static{

    }

    public static void setProfileMap(Map<String, Profile> profileMap) {
        Cache.profileMap = profileMap;
    }

    public static void setConnectionMap(Map<String, Connection> connectionMap) {
        Cache.connectionMap = connectionMap;
    }

    public static Map<String, Profile> getProfileCache(){
        return profileMap;
    }

    public static Map<String, Connection> getConnectionCache(){
        return connectionMap;
    }

    public static void addConnection(Connection connection){
        connectionMap.put(connection.getProfileId(), connection);
    }

    public static void addProfile(Profile profile){
        profileMap.put(profile.getProfileId(), profile);
    }


}
