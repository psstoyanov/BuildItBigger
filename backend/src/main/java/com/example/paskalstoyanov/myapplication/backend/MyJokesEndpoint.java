package com.example.paskalstoyanov.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myJokesApi",
        version = "v1",
        resource = "myJokes",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.paskalstoyanov.example.com",
                ownerName = "backend.myapplication.paskalstoyanov.example.com",
                packagePath = ""
        )
)
public class MyJokesEndpoint {

    private static final Logger logger = Logger.getLogger(MyJokesEndpoint.class.getName());

    @ApiMethod(name = "getMyJokes")
    public MyJokes getMyJokes() {
        MyJokes response = new MyJokes();
        response.setData();
        return response;
    }

}