package com.ttn.demo.core.models;

import com.ttn.demo.core.models.impl.BlogItem;

import java.util.List;

public  interface BlogListModel {
    List<BlogItem> getBlogItems();
}