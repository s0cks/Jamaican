package io.github.s0cks.jamaican.net;

import java.io.IOException;

public final class GrepCode{
    private static final String API_URL = "http://grepcode.com/file/repo1.maven.org/maven2";

    public static String grep(String group, String project, String version, String classpath)
    throws IOException{
        String request = API_URL + "/" + group + "/" + project + "/" + version + "/" + classpath.replace(".", "/") + ".java" + "?rel=file&v=source";
        return URLShortener.shorten(request);
    }
}