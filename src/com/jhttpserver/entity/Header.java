package com.jhttpserver.entity;

/**
 * Created by jayin on 15/1/31.
 */
public class Header {

    private String field;
    private String value;


    public Header(String field,String value,String... values ){
        if(values.length > 0){
            for(String v : values) value += "; " + v;
        }
        this.field = field;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public String getField() {

        return field;
    }

    @Override
    public String toString() {
        return field + ": " + value;
    }
}
