package com.ttn.demo.core.services;

import com.ttn.demo.core.config.BlogConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = BlogConfigService.class, immediate = true)
@Designate(ocd = BlogConfig.class)
public class BlogConfigService {

    private int maxItems;

    @Activate
    @Modified
    protected void activate(BlogConfig config) {
        this.maxItems = config.maxItems();
    }

    public int getMaxItems() {
        return maxItems;
    }
}
