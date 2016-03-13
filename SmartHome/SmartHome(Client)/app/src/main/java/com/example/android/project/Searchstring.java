package com.example.android.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Specter on 2016/3/5.
 */
public class Searchstring {
    private Searchstring(){

    }
        public static String getString(String src, String match)
        {
            Pattern p = Pattern.compile(match);
            Matcher m = p.matcher(src);
            while(m.find()) {
                String g = m.group();
                return g;
            }
            return null;
        }

}
