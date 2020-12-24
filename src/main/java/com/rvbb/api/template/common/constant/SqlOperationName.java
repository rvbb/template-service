package com.rvbb.api.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SqlOperationName {
    equal,
    greater,
    less,
    greaterorequal,
    lessorequal,
    like,
    notlike,
    in,
    notin,
    likeleft,
    likeright,
    exists;

    public static String asString() {
        return equal.toString()
                + ", " + greater.toString()
                + ", " + less.toString()
                + ", " + greaterorequal.toString()
                + ", " + lessorequal.toString()
                + ", " + like.toString();
    }

    public static boolean contains(String enumAsString){
        for(SqlOperationName item : SqlOperationName.values()){
            return item.toString().equalsIgnoreCase(enumAsString);
        }
        return false;
    }
}
