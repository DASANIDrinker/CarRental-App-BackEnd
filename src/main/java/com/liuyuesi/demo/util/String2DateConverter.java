package com.liuyuesi.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class String2DateConverter implements Converter<String,Date> {

	@Nullable
	public Date convert(String json) {
        System.out.println(json.length());
        System.out.println(json);
        if (json.length() == 10) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                return simpleDateFormat.parse(json);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;

        }
        if (json.length() == 13) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
            try {
                return simpleDateFormat.parse(json);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;

        }

        if (json.length() == 16) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                return simpleDateFormat.parse(json);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;

        }
        if (json.length() == 19) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return simpleDateFormat.parse(json);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

	
}
