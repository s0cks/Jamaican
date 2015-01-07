package io.github.s0cks.jamaican.util;

import java.lang.reflect.Array;

public final class Utilities{
    public static String[] remove(String[] array, int index){
        int len = array.length;
        if(index < 0 || index >= len){
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + len);
        }

        Object res = Array.newInstance(array.getClass().getComponentType(), len - 1);
        System.arraycopy(array, 0, res, 0, index);
        if(index < len - 1){
            System.arraycopy(array, index + 1, res, index, len - index - 1);
        }
        return (String[]) res;
    }
}