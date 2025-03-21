package com.ttn.demo.core.models;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.AssetManager;
import com.adobe.granite.asset.api.Rendition;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageRetentionModel {

    @ValueMapValue
    private String imagePath;

    @ValueMapValue
    private String renditionName;  // Example: "cq5dam.thumbnail.140.100.png"

    @SlingObject
    private ResourceResolver resourceResolver;

    private String imageUrl;

    @PostConstruct
    protected void init() {
        if (imagePath != null) {
            AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
            if (assetManager != null) {
                Asset asset = assetManager.getAsset(imagePath);
                if (asset != null) {
                    Rendition rendition = asset.getRendition(renditionName);
                    if (rendition != null) {
                        imageUrl = rendition.getPath();
                    } else {
                        imageUrl = asset.getPath(); // Fallback to original image
                    }
                }
            }
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
