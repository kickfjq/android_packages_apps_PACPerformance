/*
 * Copyright (C) 2014 PAC-man ROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pac.performance.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String replaceLastChar(String s, int length) {
        int slength = s.length();
        if (slength < length) return "0";
        return s.substring(0, slength - length) + "";
    }

    public static String setAllLetterUpperCase(String text) {
        StringBuilder res = new StringBuilder();

        String[] strArr = text.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }

    public static String listSplitline(List<String> value) {
        StringBuilder mValue = new StringBuilder();
        for (String s : value) {
            mValue.append(s);
            mValue.append("\t");
        }
        return mValue.toString();
    }

    public static String getFormattedKernelVersion() {
        try {
            return formatKernelVersion(readLine("/proc/version"));
        } catch (IOException e) {
            return "Unavailable";
        }
    }

    private static String formatKernelVersion(String rawKernelVersion) {
        final String PROC_VERSION_REGEX = "Linux version (\\S+) "
                + "\\((\\S+?)\\) " + "(?:\\(gcc.+? \\)) " + "(#\\d+) "
                + "(?:.*?)?" + "((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)";

        Matcher m = Pattern.compile(PROC_VERSION_REGEX).matcher(
                rawKernelVersion);

        return !m.matches() || m.groupCount() < 4 ? "Unavailable" : m.group(1)
                + "\n" + m.group(2) + " " + m.group(3) + "\n" + m.group(4);
    }

    public static String getString(String name, String defaults, Context context) {
        return context.getSharedPreferences("prefs", 0).getString(name,
                defaults);
    }

    public static void saveString(String name, String value, Context context) {
        context.getSharedPreferences("prefs", 0).edit().putString(name, value)
                .commit();
    }

    public static boolean getBoolean(String name, boolean defaults,
            Context context) {
        return context.getSharedPreferences("prefs", 0).getBoolean(name,
                defaults);
    }

    public static void saveBoolean(String name, boolean value, Context context) {
        context.getSharedPreferences("prefs", 0).edit().putBoolean(name, value)
                .commit();
    }

    public static void toast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean exist(String file) {
        return new File(file).exists();
    }

    public static String readBlock(String filename) throws IOException {
        BufferedReader buffreader = new BufferedReader(
                new FileReader(filename), 256);
        String line;
        StringBuilder text = new StringBuilder();
        while ((line = buffreader.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        buffreader.close();
        return text.toString();
    }

    public static String readLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename),
                256);
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }

}
