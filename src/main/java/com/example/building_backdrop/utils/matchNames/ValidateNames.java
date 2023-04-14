package com.example.building_backdrop.utils.matchNames;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

@Component
public class ValidateNames {
    private static final int MAX_LEVENSHTEIN_DISTANCE = 2;
    private static final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public boolean isMatch(String inputName, String databaseName){
        inputName = inputName.replaceAll("\\s+", "").toLowerCase();
        databaseName = databaseName.replaceAll("\\s+", "").toLowerCase();

        if(inputName.equals(databaseName)) return true;
        int distance = levenshteinDistance.apply(inputName, databaseName);
        return distance <= MAX_LEVENSHTEIN_DISTANCE;
    }
}
