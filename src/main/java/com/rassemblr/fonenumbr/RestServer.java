/*
 * Copyright (c) 2010 pdeschen@rassemblr.com All rights reserved.
 */

package com.rassemblr.fonenumbr;

import java.util.*;

import org.jboss.resteasy.plugins.server.servlet.*;
import org.mortbay.jetty.*;
import org.mortbay.jetty.servlet.*;

public class RestServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        Context root = new Context(server, "/", Context.SESSIONS);
        root.addServlet(new ServletHolder(new HttpServletDispatcher()), "/*");
        //root.setAttribute("resteasy.scan", true);
        Map initParams = new HashMap();
        //initParams.put("resteasy.scan", "true");
        initParams.put("resteasy.resources", com.rassemblr.fonenumbr.ValidatorService.class.getName());
        initParams.put("resteasy.providers", org.codehaus.jackson.jaxrs.JacksonJsonProvider.class.getName());
        initParams.put("resteasy.use.builtin.providers", Boolean.FALSE);
        root.setInitParams(initParams);
        server.start();
        System.out.println("wooga");
    }

}
