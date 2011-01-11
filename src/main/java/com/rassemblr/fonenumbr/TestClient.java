/*
 * Copyright (c) 2010 pdeschen@rassemblr.com All rights reserved.
 */

package com.rassemblr.fonenumbr;

import org.jboss.resteasy.client.*;
import org.jboss.resteasy.plugins.providers.*;
import org.jboss.resteasy.spi.*;

public class TestClient {

    public static void main(String[] args) {
        ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
        instance.registerProvider(org.codehaus.jackson.jaxrs.JacksonJsonProvider.class);
        RegisterBuiltin.register(instance);

        ValidatorService client = ProxyFactory.create(ValidatorService.class, "http://localhost:8080");

        client.isValid("1234");
    }
}
