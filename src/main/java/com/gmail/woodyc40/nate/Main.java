package com.gmail.woodyc40.nate;

import com.gmail.woodyc40.nate.storage.Config;
import com.gmail.woodyc40.nate.storage.StateManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.gmail.woodyc40.nate.Util.WORKING_DIR;

public class Main {
    public static void main(String[] args) throws IOException {
        String configProperty = System.getProperty("config");
        Path configPath = configProperty != null ? Paths.get(configProperty) : WORKING_DIR.resolve("config.json");
        Config config = new Config(configPath);

        String stateProperty = System.getProperty("state");
        Path statePath = stateProperty != null ? Paths.get(stateProperty) : WORKING_DIR.resolve("state.json");
        StateManager stateManager = new StateManager(statePath);

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Config.class).toInstance(config);
                bind(StateManager.class).toInstance(stateManager);
            }
        });

        Nate nate = injector.getInstance(Nate.class);
        nate.init();

        int expect = 10;
        int[] data = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        if (config.isContinuous()) {
            System.out.println("Using continuous mode, use Ctrl+C to stop...");
            while (true) {
                int result = nate.compute(data);
                System.out.println("Result = " + result + " (expected? " + (expect == result) + ')');

                nate.learn(expect, data);

                nate.saveState();
                stateManager.write(statePath);
            }
        }

        System.out.println("Computing without evolution...");
        int nonEvoResult = nate.compute(data);
        System.out.println("Result = " + nonEvoResult);

        nate.close();
        nate.init();

        System.out.println("Computing with evolution...");
        int evoResult = nate.computeWithEvolution(data);
        System.out.println("Result = " + evoResult);

        System.out.println();
        System.out.println("Starting learning process...");

        nate.learn(expect, data);
        System.out.println("Done.");

        stateManager.write(statePath);
    }
}