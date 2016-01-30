package com.nice.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2016/1/31.
 */
public class NiceValue implements Serializable {

    public long shId;
    public Map<Long, String> selectedValues = new HashMap<>();
    public Map<Long, String> selectedStrutionValues = new HashMap<>();

}
