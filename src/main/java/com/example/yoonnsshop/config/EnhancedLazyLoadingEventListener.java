package com.example.yoonnsshop.config;

import org.hibernate.event.spi.InitializeCollectionEvent;
import org.hibernate.event.spi.InitializeCollectionEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class EnhancedLazyLoadingEventListener implements InitializeCollectionEventListener {
    private static final Logger log = LoggerFactory.getLogger(EnhancedLazyLoadingEventListener.class);

    @Override
    public void onInitializeCollection(InitializeCollectionEvent event) {
        int size = event.getCollection().getSize();
        log.debug("Lazy loading occurred for collection: {} of entity: {}. Loaded {} items.",
                event.getCollection().getRole(),
                event.getAffectedOwnerEntityName(),
                size);
    }
}
