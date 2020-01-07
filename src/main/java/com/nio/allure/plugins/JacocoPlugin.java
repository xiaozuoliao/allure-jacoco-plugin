package com.nio.allure.plugins;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.qameta.allure.Aggregator;
import io.qameta.allure.Widget;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.entity.TestResult;

public class JacocoPlugin implements Aggregator {

    //实现数据的提取处理，这里我把我想要的结果保存到codecoverage.json文件内
    @Override
    public void aggregate(final Configuration configuration, final List<LaunchResults> launches,
                          final Path outputDirectory) throws IOException {
        final JacksonContext jacksonContext = configuration.requireContext(JacksonContext.class);
        final Path dataFolder = Files.createDirectories(outputDirectory.resolve("data"));
        final Path dataFile = dataFolder.resolve("codecoverage.json");
        final Stream<TestResult> resultsStream = launches.stream().flatMap(launch -> launch.getAllResults().stream());
        Collection<Map> collection = new ArrayList<Map>();
        Map<String, String> jacocomap = new HashMap<String, String>();
        try (OutputStream os = Files.newOutputStream(dataFile)) {
            jacocomap.put("hasJa", "on");
            collection.add(jacocomap);
            jacksonContext.getValue().writeValue(os, collection);
        }
    }


}