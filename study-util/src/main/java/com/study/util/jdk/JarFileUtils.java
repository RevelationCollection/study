package com.study.util.jdk;

import javax.annotation.Resource;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileUtils {
    public static void main(String[] args) throws Exception{
        testJarFile("jar:file:/Users/wy/Downloads/deposit-service-1.0.1.test.jar!/");
//        testJarFile("jar:file:/Users/wy/Downloads/生产deposit-service-1.0.1.jar!/");
    }

    private static void testJarFile(String file) throws Exception {
        System.out.println(file);
        URL FileSysUrl = new URL(file);
        URLConnection con = FileSysUrl.openConnection();
        JarFile jarFile;
        String jarFileUrl;
        String rootEntryPath;
        boolean closeJarFile;
        // Should usually be the case for traditional JAR files.
        JarURLConnection jarCon = (JarURLConnection) con;
        jarFile = jarCon.getJarFile();
        jarFileUrl = jarCon.getJarFileURL().toExternalForm();
        JarEntry jarEntry = jarCon.getJarEntry();
        rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
        closeJarFile = !jarCon.getUseCaches();
        Set<Resource> result = new LinkedHashSet<>(8);
        Enumeration<JarEntry> entries = jarFile.entries();
        for (; entries.hasMoreElements();) {
            JarEntry entry = entries.nextElement();
            String entryPath = entry.getName();
            if (entryPath.startsWith("com/aden"))
                System.out.println("entries : "+entryPath);
            if (entryPath.startsWith(rootEntryPath)) {
                String relativePath = entryPath.substring(rootEntryPath.length());

            }
        }
    }
}
