package com.sta.utils.web;

import java.util.HashSet;
import java.util.Set;

public class WebUtils
{

    public static String[] getUniqueStringValues(String commaSeperatedValues)
    {
        if ((commaSeperatedValues == null) || (commaSeperatedValues.isEmpty()))
        {
            return null;
        }

        return getUniqueStringValues(commaSeperatedValues.split(","));
    }

    public static String[] getUniqueStringValues(String[] strValues)
    {
        if ((strValues == null) || (strValues.length < 1))
        {
            return null;
        }

        Set<String> uniqueValues = new HashSet<>();

        for (String strValue : strValues)
        {
            if (strValue.isEmpty())
            {
                continue;
            }

            uniqueValues.add(strValue);
        }

        return uniqueValues.toArray(new String[uniqueValues.size()]);
    }

    public static Integer[] getUniqueIntValues(String commaSeperatedValues)
    {
        if ((commaSeperatedValues == null) || (commaSeperatedValues.isEmpty()))
        {
            return null;
        }

        return getUniqueIntValues(commaSeperatedValues.split(","));
    }

    public static Integer[] getUniqueIntValues(String[] strValues)
    {
        if ((strValues == null) || (strValues.length < 1))
        {
            return null;
        }

        Set<Integer> uniqueValues = new HashSet<>();

        for (String strValue : strValues)
        {
            try
            {
                if (strValue.isEmpty())
                {
                    continue;
                }

                uniqueValues.add(Integer.parseInt(strValue));
            }
            catch (NumberFormatException ex)
            {
            }
        }

        return uniqueValues.toArray(new Integer[uniqueValues.size()]);
    }

}
