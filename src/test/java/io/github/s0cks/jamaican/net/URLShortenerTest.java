package io.github.s0cks.jamaican.net;

public class URLShortenerTest{
    @org.junit.Test
    public void testShorten()
    throws Exception{
        String url = URLShortener.shorten("https://www.github.com");
        System.out.println(url);
        String expanded = URLShortener.expand(url);
        System.out.println(expanded);
    }
}