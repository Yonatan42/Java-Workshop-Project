package com.yoni.javaworkshopprojectserver.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;

// todo - remove this later
/**
 *
 * @author Yoni
 */
@Path("ping")
public class PingTestResource {
    
    @GET
    public Response ping(){
        return Response
                .ok("pong")
                .build();
    }

    @GET
    @Path("process")
    public Response getProcess() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        java.lang.management.RuntimeMXBean runtime =
                java.lang.management.ManagementFactory.getRuntimeMXBean();
        java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
        jvm.setAccessible(true);
        sun.management.VMManagement mgmt =
                (sun.management.VMManagement) jvm.get(runtime);
        java.lang.reflect.Method pid_method =
                mgmt.getClass().getDeclaredMethod("getProcessId");
        pid_method.setAccessible(true);

        int pid = (Integer) pid_method.invoke(mgmt);
        return Response
                .ok(pid)
                .build();
    }
}
