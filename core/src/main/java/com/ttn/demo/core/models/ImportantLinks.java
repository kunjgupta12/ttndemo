package com.ttn.demo.core.models;

import java.util.List;
import java.util.Map;

public interface ImportantLinks {

    String getMainTitle();

    List<Map<String,String>> getLinksWithTitle();
}