package com.javarush.khmelov.lesson14.config;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Config {

    public static final Path WEB_INF = Paths.get(URI.create(
                    Objects.requireNonNull(
                            Config.class.getResource("/")
                    ).toString()))
            .getParent();

}
