package org.misha;

import java.util.Random;

/**
 * author: misha
 * date: 1/3/18
 * time: 5:11 PM
 */
public final class TestUtil {
    
    private TestUtil() {
    }
    
    public static String name() {
        switch (new Random().nextInt() % 6) {
            case 0:
                return "John Smith";
            case 1:
                return "Joan Parker";
            case 2:
                return "Peter The First";
            case 3:
                return "Elisabeth The Second";
            case 4:
                return "James Cook";
            default:
                return "Basil The Great";
        }
    }
    
    public static String id() {
        return String.valueOf(new Random().nextInt());
    }
}
