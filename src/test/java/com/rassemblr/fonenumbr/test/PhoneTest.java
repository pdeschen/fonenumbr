/*
 * Copyright (c) 2010 pdeschen@rassemblr.com All rights reserved.
 */

package com.rassemblr.fonenumbr.test;

import java.io.*;
import java.util.*;

import junit.framework.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;

import com.google.i18n.phonenumbers.PhoneNumberUtil.*;
import com.rassemblr.fonenumbr.*;

public class PhoneTest extends TestCase {

    public void testValidation() {

        ValidatorService service = new ValidatorService();
        assertTrue(service.isValid("+15145296356"));
        assertTrue(service.isValid("+15145813246"));
        assertTrue(service.isValid("+6433316005"));
        assertTrue(service.isValid("+64 3 331 6005"));
        assertTrue(service.isValid("+5493435551212"));
        assertTrue(service.isValid("+442034567890x456"));
        assertTrue(service.isValid("+442034567890x456"));

    }

    public void testValidationWithCountryCode() {
        ValidatorService service = new ValidatorService();
        assertTrue(service.isValidForCountry("CA", "5148613246"));
        assertTrue(service.isValidForCountry("US", "(650) 253-0000"));
        assertTrue(service.isValidForCountry("UK", "+44 2034567890 X456"));
        assertTrue(service.isValidForCountry("UK", "+44 2034567890#456"));
        assertTrue(service.isValidForCountry("NZ", "001800 SIX-FLAG"));
        assertTrue(service.isValidForCountry("NZ", "Tel:0800-345-600"));
    }

    public void testDetails() throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> expected = new HashMap<String, String>();
        ValidatorService service = new ValidatorService();

        Map<String, String> details = service.details(PhoneNumberFormat.INTERNATIONAL.name().toLowerCase(),
                                                      "CA",
                                                      "5148613246");
        String value = mapper.writeValueAsString(details);
        System.out.println(value);

        expected.put("formated", "+1 514-861-3246");
        expected.put("national", "5148613246");
        expected.put("country", "1");
        expected.put("extension", "");
        expected.put("type", PhoneNumberType.FIXED_LINE_OR_MOBILE.name().toLowerCase());

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.E164.name().toLowerCase(), "CA", "5148613246");
        expected.put("formated", "+15148613246");

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.NATIONAL.name().toLowerCase(), "CA", "5148613246");
        expected.put("formated", "(514) 861-3246");

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.INTERNATIONAL.name().toLowerCase(), "US", "(650) 253-0000");

        value = mapper.writeValueAsString(details);
        System.out.println(value);

        expected.put("formated", "+1 650-253-0000");
        expected.put("national", "6502530000");
        expected.put("country", "1");
        expected.put("extension", "");
        expected.put("type", PhoneNumberType.FIXED_LINE_OR_MOBILE.name().toLowerCase());

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.E164.name().toLowerCase(), "UK", "+44 2034567890 X456");

        value = mapper.writeValueAsString(details);
        System.out.println(value);

        expected.put("formated", "+442034567890");
        expected.put("national", "2034567890");
        expected.put("country", "44");
        expected.put("extension", "456");
        expected.put("type", PhoneNumberType.FIXED_LINE.name().toLowerCase());

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.E164.name().toLowerCase(), "NZ", "001800 SIX-FLAG");

        value = mapper.writeValueAsString(details);
        System.out.println(value);

        expected.put("formated", "+18007493524");
        expected.put("national", "8007493524");
        expected.put("country", "1");
        expected.put("extension", "");
        expected.put("type", PhoneNumberType.TOLL_FREE.name().toLowerCase());

        assertEquals(expected, details);

        details = service.details(PhoneNumberFormat.INTERNATIONAL.name().toLowerCase(), "NZ", "Tel:0800-345-600");

        value = mapper.writeValueAsString(details);
        System.out.println(value);

        expected.put("formated", "+64 800 345 600");
        expected.put("national", "800345600");
        expected.put("country", "64");
        expected.put("extension", "");
        expected.put("type", PhoneNumberType.TOLL_FREE.name().toLowerCase());

        assertEquals(expected, details);

    }
}
