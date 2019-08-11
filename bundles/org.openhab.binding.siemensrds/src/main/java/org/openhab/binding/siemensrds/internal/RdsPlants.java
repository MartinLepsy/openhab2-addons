/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.siemensrds.internal;

import static org.openhab.binding.siemensrds.internal.RdsBindingConstants.*;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * Interface to the Plants List of a particular User
 * 
 * @author Andrew Fiddian-Green - Initial contribution
 * 
 */
class RdsPlants {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RdsPlants.class);

    @SerializedName("items")
    private List<PlantInfo> plants;

    static class PlantInfo {

        @SerializedName("id")
        private String plantId;
        @SerializedName("isOnline")
        private Boolean online;

        public String getId() {
            return plantId;
        }

        public Boolean isOnline() {
            return online;
        }
    }

    /*
     * public method: execute a GET on the cloud server, parse JSON, and create a
     * class that encapsulates the data
     */
    @Nullable
    public static RdsPlants create(String apiKey, String token) {
        /*
         * use the RdsDataPoints.httpGenericGetJson static method to fetch the JSON
         */
        String json = RdsDataPoints.httpGenericGetJson(apiKey, token, URL_PLANTS);

        if (json.equals("")) {
            LOGGER.debug("create: empty JSON element");
            return null;
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(json, RdsPlants.class);
        } catch (JsonSyntaxException e) {
            LOGGER.debug("create: JSON syntax error");
            return null;
        }
    }

    /*
     * public method: return the plant list
     */
    public List<PlantInfo> getPlants() {
        return plants;
    }

}
