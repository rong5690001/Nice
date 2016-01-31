package com.nice.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2016/1/31.
 */
public class NiceValue implements Serializable {

    public String shIdAndGroupId;
    public Map<Long, String> selectedValues = new HashMap<>();
    public Map<Long, String> selectedStrutionValues = new HashMap<>();

    public NiceValue(String shIdAndGroupId, Map<Long, String> selectedValues, Map<Long, String> selectedStrutionValues){
        this.shIdAndGroupId = shIdAndGroupId;
        this.selectedValues = selectedValues;
        this.selectedStrutionValues = selectedStrutionValues;
    }

}
