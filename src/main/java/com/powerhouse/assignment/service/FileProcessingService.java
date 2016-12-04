package com.powerhouse.assignment.service;

import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.model.Connection;
import com.powerhouse.assignment.model.Profile;
import com.powerhouse.assignment.util.Cache;
import com.powerhouse.assignment.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Service
public class FileProcessingService {

    static Logger log = Logger.getLogger(FileProcessingService.class);

    public boolean isStaticFilePresent(){
        return !Cache.getConnectionCache().isEmpty() && !Cache.getProfileCache().isEmpty();
    }

    public void cleanCache(){
        Cache.setConnectionMap(new HashMap<>());
        Cache.setProfileMap(new HashMap<>());
    }

    public void processFile(File fractionFile, File meterReadingFile) throws ValidationException {

        Map<String, Profile> profileMap = Utils.createProfileMap(fractionFile);
        Map<String, Connection> connectionMap = Utils.createConnectionMap(meterReadingFile);

        //profile validation
        for (Profile profile : profileMap.values()){
             if(profile.isValid()){
                 Cache.addProfile(profile);
             }
        }

        //connection validation
        String errorMsg = "";
        for (Connection connection : connectionMap.values()){
            if(connection.isValid() && Utils.doesProfileContainConnection(profileMap, connection)){
                //check valid consumption
                try {
                    if (Utils.isValidConsumption(profileMap.get(connection.getProfileId()), connection)) {
                        Cache.addConnection(connection);
                    }
                }catch(ValidationException e){
                    errorMsg += e.getMessage();
                }
            }
        }
        if(!errorMsg.isEmpty()){
            throw new ValidationException(errorMsg);
        }
    }

    @PostConstruct
    public void initIt() {
       try{
           ClassLoader classLoader = getClass().getClassLoader();
           File fractionFile = new File(classLoader.getResource("data/fraction.csv").getFile());
           File meterReadingFile = new File(classLoader.getResource("data/meterReading.csv").getFile());
           processFile(fractionFile, meterReadingFile);
       }catch(ValidationException e){
           log.error(e.getMessage());
       }catch(Exception e){
           log.error("Files not preset in data directory. Please load it from UI");
       }


    }


}
