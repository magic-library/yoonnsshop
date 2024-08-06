package com.example.yoonnsshop.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

//@Component
public class HibernateListenerConfigurer {
//    @Autowired
    private EntityManagerFactory emf;

//    @Autowired
    private EnhancedLazyLoadingEventListener lazyLoadingEventListener;

    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.INIT_COLLECTION)
                .appendListener(lazyLoadingEventListener);
    }
}
