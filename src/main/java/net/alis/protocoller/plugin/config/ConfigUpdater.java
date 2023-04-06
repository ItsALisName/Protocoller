package net.alis.protocoller.plugin.config;

import lombok.SneakyThrows;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ConfigUpdater {

    public static void updateConfiguration(Plugin plugin, String resourceName, File toUpdate, String... ignoredSections) {
        updateConfiguration(plugin, resourceName, toUpdate, Arrays.asList(ignoredSections));
        LogsManager.get().info("Configuration file \"" + resourceName + "\" successfully updated!");
    }

    @SneakyThrows
    public static void updateConfiguration(@NotNull Plugin plugin, String resourceName, @NotNull File toUpdate, List<String> ignoredSections) {
        if(!toUpdate.exists()) {
            new ExceptionBuilder().getConfigExceptions().nullConfigFile(toUpdate.getName(), "plugins\\Protocoller\\" + toUpdate).throwException();
        }
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(resourceName), StandardCharsets.UTF_8));
        FileConfiguration currentConfig = YamlConfiguration.loadConfiguration(toUpdate);
        Map<String, String> comments = parseComments(plugin, resourceName, defaultConfig);
        Map<String, String> ignoredSectionsValues = parseIgnoredSections(toUpdate, currentConfig, comments, ignoredSections == null ? Collections.emptyList() : ignoredSections);
        StringWriter writer = new StringWriter();
        write(defaultConfig, currentConfig, new BufferedWriter(writer), comments, ignoredSectionsValues);
        String value = writer.toString();
        Path toUpdatePath = toUpdate.toPath();
        if (!value.equals(new String(Files.readAllBytes(toUpdatePath), StandardCharsets.UTF_8))) {
            Files.write(toUpdatePath, value.getBytes(StandardCharsets.UTF_8));
        }
    }

    @SneakyThrows
    private static void write(FileConfiguration defaultConfig, FileConfiguration currentConfig, BufferedWriter writer, Map<String, String> comments, Map<String, String> ignoredSectionsValues) {
        FileConfiguration parserConfig = new YamlConfiguration();
        keyLoop: for (String fullKey : defaultConfig.getKeys(true)) {
            String indents = KeyBuilder.getIndents(fullKey, '.');
            if (ignoredSectionsValues.isEmpty()) {
                writeCommentIfExists(comments, writer, fullKey, indents);
            } else {
                for (Map.Entry<String, String> entry : ignoredSectionsValues.entrySet()) {
                    if (entry.getKey().equals(fullKey)) {
                        writer.write(ignoredSectionsValues.get(fullKey) + "\n");
                        continue keyLoop;
                    } else if (KeyBuilder.isSubKeyOf(entry.getKey(), fullKey, '.')) {
                        continue keyLoop;
                    }
                }
                writeCommentIfExists(comments, writer, fullKey, indents);
            }
            Object currentValue = currentConfig.get(fullKey);
            if (currentValue == null) currentValue = defaultConfig.get(fullKey);
            String[] splitFullKey = fullKey.split("[" + '.' + "]");
            String trailingKey = splitFullKey[splitFullKey.length - 1];
            if (currentValue instanceof ConfigurationSection) {
                writer.write(indents + trailingKey + ":");
                if (!((ConfigurationSection) currentValue).getKeys(false).isEmpty()) {
                    writer.write("\n");
                } else {
                    writer.write(" {}\n");
                }
                continue;
            }
            parserConfig.set(trailingKey, currentValue);
            String yaml = parserConfig.saveToString();
            yaml = yaml.substring(0, yaml.length() - 1).replace("\n", "\n" + indents);
            String toWrite = indents + yaml + "\n";
            parserConfig.set(trailingKey, null);
            writer.write(toWrite);
        }
        String danglingComments = comments.get(null);
        if (danglingComments != null) writer.write(danglingComments);
        writer.close();
    }
    @SneakyThrows
    private static @NotNull Map<String, String> parseComments(@NotNull Plugin plugin, String resourceName, FileConfiguration defaultConfig) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(resourceName)));
        Map<String, String> comments = new LinkedHashMap<>();
        StringBuilder commentBuilder = new StringBuilder();
        KeyBuilder keyBuilder = new KeyBuilder(defaultConfig, '.');
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("-")) continue;
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                commentBuilder.append(trimmedLine).append("\n");
            } else {
                keyBuilder.parseLine(trimmedLine);
                String key = keyBuilder.toString();
                if (commentBuilder.length() > 0) {
                    comments.put(key, commentBuilder.toString());
                    commentBuilder.setLength(0);
                }
                if (!keyBuilder.isConfigSectionWithKeys()) {
                    keyBuilder.removeLastKey();
                }
            }
        }
        reader.close();
        if (commentBuilder.length() > 0) comments.put(null, commentBuilder.toString());
        return comments;
    }

    @SneakyThrows
    private static @NotNull Map<String, String> parseIgnoredSections(File toUpdate, FileConfiguration currentConfig, Map<String, String> comments, @NotNull List<String> ignoredSections) {
        BufferedReader reader = new BufferedReader(new FileReader(toUpdate));
        Map<String, String> ignoredSectionsValues = new LinkedHashMap<>(ignoredSections.size());
        KeyBuilder keyBuilder = new KeyBuilder(currentConfig, '.');
        StringBuilder valueBuilder = new StringBuilder();
        String currentIgnoredSection = null;
        String line;
        lineLoop : while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) continue;
            if (trimmedLine.startsWith("-")) {
                for (String ignoredSection : ignoredSections) {
                    boolean isIgnoredParent = ignoredSection.equals(keyBuilder.toString());
                    if (isIgnoredParent || keyBuilder.isSubKeyOf(ignoredSection)) {
                        valueBuilder.append("\n").append(line);
                        continue lineLoop;
                    }
                }
            }
            keyBuilder.parseLine(trimmedLine);
            String fullKey = keyBuilder.toString();
            if (currentIgnoredSection != null && !KeyBuilder.isSubKeyOf(currentIgnoredSection, fullKey, '.')) {
                ignoredSectionsValues.put(currentIgnoredSection, valueBuilder.toString());
                valueBuilder.setLength(0);
                currentIgnoredSection = null;
            }
            for (String ignoredSection : ignoredSections) {
                boolean isIgnoredParent = ignoredSection.equals(fullKey);
                if (isIgnoredParent || keyBuilder.isSubKeyOf(ignoredSection)) {
                    if (valueBuilder.length() > 0) valueBuilder.append("\n");
                    String comment = comments.get(fullKey);
                    if (comment != null) {
                        String indents = KeyBuilder.getIndents(fullKey, '.');
                        valueBuilder.append(indents).append(comment.replace("\n", "\n" + indents));
                        valueBuilder.setLength(valueBuilder.length() - indents.length());
                    }
                    valueBuilder.append(line);
                    if (isIgnoredParent) currentIgnoredSection = fullKey;
                    break;
                }
            }
        }
        reader.close();
        if (valueBuilder.length() > 0) ignoredSectionsValues.put(currentIgnoredSection, valueBuilder.toString());
        return ignoredSectionsValues;
    }

    @SneakyThrows
    private static void writeCommentIfExists(@NotNull Map<String, String> comments, BufferedWriter writer, String fullKey, String indents) {
        String comment = comments.get(fullKey);
        if (comment != null) writer.write(indents + comment.substring(0, comment.length() - 1).replace("\n", "\n" + indents) + "\n");
    }

}

