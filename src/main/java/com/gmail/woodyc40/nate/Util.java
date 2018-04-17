package com.gmail.woodyc40.nate;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Util {
    public static final Charset UTF_8 = Charsets.UTF_8;
    public static final Path WORKING_DIR = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

    private static final Gson GSON = new GsonBuilder().setLenient().create();

    private Util() {
    }

    public static JsonObject read(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        return GSON.fromJson(new InputStreamReader(Files.newInputStream(path)), JsonObject.class);
    }
}