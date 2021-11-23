package com.sta.utils.dfs;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FsUtils
{

    public static boolean fsPathExists(String fsPath)
    {
        return (new File(fsPath)).exists();
    }

    public static String readFile(String fileNameWithPath) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(fileNameWithPath)));
    }

    public static Set<String> getFileNames(String directoryPath)
    {
        Set<String> fileNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        File dir = new File(directoryPath);
        if (!dir.isDirectory())
        {
            return fileNames;
        }

        for (File file : dir.listFiles())
        {
            if (!file.isFile())
            {
                continue;
            }

            fileNames.add(file.getName());
        }

        return fileNames;
    }

    public static void writeToFile(String filePathWithName, String content, boolean append) throws IOException
    {
        FileWriter fw = new FileWriter(filePathWithName, append);
        fw.write(content);
    }

    //<editor-fold defaultstate="collapsed" desc="JSON Read/Write functions">
    public static Map<String, JSONObject> readJsonFiles(String directoryPath, Set<String> filterFileNames) throws ParseException, IOException
    {
        Map<String, JSONObject> fileList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        File dir = new File(directoryPath);
        if (!dir.isDirectory())
        {
            return fileList;
        }

        FilenameFilter filteredFileNames = new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                if ((filterFileNames == null) || filterFileNames.isEmpty())
                {
                    return true;
                }

                return !filterFileNames.contains(name);
            }
        };

        for (File file : dir.listFiles(filteredFileNames))
        {
            if (!file.isFile())
            {
                continue;
            }

            String fileName = file.getName();
            if (fileList.containsKey(fileName))
            {
                continue;
            }

            JSONObject json;

            try
            {
                json = readJsonFile(file.getAbsolutePath());
            }
            catch (ParseException ex)
            {
                continue;
            }

            fileList.put(fileName, json);
        }

        return fileList;
    }

    public static JSONObject readJsonFile(String fileNameWithPath) throws ParseException, IOException
    {
        if (fileNameWithPath.startsWith("https://"))
        {
            return readJsonFile(new URL(fileNameWithPath));
        }

        return (JSONObject) (new JSONParser()).parse(readFile(fileNameWithPath));
    }

    public static JSONObject readJsonFile(URL url) throws ParseException, IOException
    {
        try (Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");

            return (JSONObject) (new JSONParser()).parse(scanner.hasNext() ? scanner.next() : "");
        }
    }

    public static void writeToFile(String filePathWithName, JSONObject jsonContent, boolean append) throws IOException
    {
        writeToFile(filePathWithName, jsonContent.toJSONString(), append);
    }
    //</editor-fold>
}
