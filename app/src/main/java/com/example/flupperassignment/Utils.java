package com.example.flupperassignment;

public class Utils {
    public static boolean checkForNullandEmpty(Object object) {
        if (object instanceof Product) {
            Product p = (Product) object;
            return p.getName() != null && !p.getName().isEmpty()
                    && p.getDescription() != null && !p.getDescription().isEmpty()
                    && p.getRegularPrice() != null && !p.getRegularPrice().isEmpty()
                    && p.getSalePrice() != null && !p.getSalePrice().isEmpty()
                    && p.getColorList() != null && p.getColorList().size() > 0;
        }
        return false;
    }
}
