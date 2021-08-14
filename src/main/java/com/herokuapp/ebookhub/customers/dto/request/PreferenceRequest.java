package com.herokuapp.ebookhub.customers.dto.request;

// import java.util.Set;

import com.herokuapp.ebookhub.customers.entities.CustomersPreference;

// import lombok.Getter;

// @Getter
public class PreferenceRequest {
    
    private boolean foodFlag;
    
    private boolean fashionFlag;

    private boolean lifestyleFlag;

    private boolean propertyFlag;

    private String prefs;

    // private Set<CustomersPreference> customersPreferences;

    // public String getCustomersPreference() {
    //     String result = "";
    //     for (CustomersPreference customersPreference: customersPreferences) {
    //         result += customersPreference.ordinal() + ",";
    //     }
    //     return result.substring(0, result.length() - 1);
    // }

    public String getPrefs() {
        // System.out.println("KOSONG " + this.prefs);
        if (this.prefs != null) {
            String[] temp = this.prefs.split(",");
            for (int i = 0; i < temp.length; i++) {
            //     switch (CustomersPreference.valueOf(temp[i])) {
            //         case FOOD:
            //             foodFlag = true;
            //             continue;
            //         case FASHION:
            //             fashionFlag = true;
            //             continue;
            //         case LIFESTYLE:
            //             lifestyleFlag = true;
            //             continue;
            //         case PROPERTY:
            //             propertyFlag = true;
            //             continue;
            //         default:
                if (Integer.parseInt(temp[i]) < 0 || Integer
                .parseInt(temp[i]) > CustomersPreference.values().length) {
                    return "Error no " + temp[i] + " preference";
                }
            //     }
            }
        }
        return this.prefs;
    }

}