/*
 * Copyright (c) 2010 pdeschen@rassemblr.com All rights reserved.
 */

package com.rassemblr.fonenumbr;

import java.util.*;

import javax.ws.rs.*;

import com.google.i18n.phonenumbers.*;
import com.google.i18n.phonenumbers.PhoneNumberUtil.*;
import com.google.i18n.phonenumbers.Phonenumber.*;

@Path("/validator")
public class ValidatorService {

    @GET
    @Path("/{number}")
    @Produces("application/json")
    public Boolean isValid(@PathParam("number") String number) {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber phoneNumber = phoneUtil.parse(number, "ZZ");
            return phoneUtil.isValidNumber(phoneNumber);
        }
        catch (NumberParseException e) {
            return false;
        }
    }

    @GET
    @Path("/{country:[A-Z]{2}}/{number:.*}")
    @Produces("application/json")
    public Boolean isValidForCountry(@PathParam("country") String country, @PathParam("number") String number) {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {

            //phoneUtil.isPossibleNumber(arg0, arg1)
            PhoneNumber phoneNumber = phoneUtil.parse(number, country);
            return phoneUtil.isValidNumber(phoneNumber);
        }
        catch (NumberParseException e) {
            return false;
        }
    }

    @GET
    @Path("/{format:international|national|e164}/{country:[A-Z]{2}}/{number:.*}")
    @Produces("application/json")
    public Map<String, String> details(@PathParam("format") String format,
                                       @PathParam("country") String country,
                                       @PathParam("number") String number) {
        Map<String, String> map = new HashMap<String, String>();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {

            PhoneNumber phoneNumber = phoneUtil.parse(number, country);

            map.put("country", String.valueOf(phoneNumber.getCountryCode()));
            map.put("national", String.valueOf(phoneNumber.getNationalNumber()));
            map.put("extension", String.valueOf(phoneNumber.getExtension()));
            map.put("formated", phoneUtil.format(phoneNumber, PhoneNumberFormat.valueOf(format.toUpperCase())));
            map.put("type", phoneUtil.getNumberType(phoneNumber).name().toLowerCase());
        }
        catch (NumberParseException e) {
        }
        return map;
    }
}
