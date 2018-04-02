package com.gmail.woodyc40.nate;

import com.gmail.woodyc40.nate.storage.Config;
import com.gmail.woodyc40.nate.storage.StateManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.nio.file.Paths;

import static com.gmail.woodyc40.nate.Util.WORKING_DIR;

public class Main {
    public static void main(String[] args) throws IOException {
        String configPath = System.getProperty("config");
        Config config = new Config(configPath != null ? Paths.get(configPath) : WORKING_DIR.resolve("config.json"));

        String statePath = System.getProperty("state");
        StateManager stateManager = new StateManager(statePath != null ? Paths.get(statePath) : WORKING_DIR.resolve("state..json"));

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Config.class).toInstance(config);
                bind(StateManager.class).toInstance(stateManager);
            }
        });

        Nate nate = injector.getInstance(Nate.class);
        nate.init();
    }
}