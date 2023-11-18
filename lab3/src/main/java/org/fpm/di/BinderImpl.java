package org.fpm.di;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BinderImpl implements Binder {
    private Map<Class<?>, Class<?>> classMap;  // TODO: rename fields
    private Map<Class<?>, Object> instanceMap;
    private List<Class<?>> prototypes;

    public BinderImpl() {
        classMap = new HashMap<>();
        instanceMap = new HashMap<>();
        prototypes = new ArrayList<>();
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz.getAnnotation(Singleton.class) != null) {
            bind(clazz, newInstance(clazz));
        } else {
            prototypes.add(clazz);
        }
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        classMap.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        instanceMap.put(clazz, instance);
    }

    public <T> T get(Class<T> clazz) {
        if (classMap.containsKey(clazz)) {
            return (T) get(classMap.get(clazz));
        }

        if (instanceMap.containsKey(clazz)) {
            return (T) instanceMap.get(clazz);
        }

        if (prototypes.contains(clazz)) {
            return newInstance(clazz);
        }

        return null;
    }

    public <T> T newInstance(Class<T> clazz) {
        T instance;

        try {
            Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
            Constructor<T> constructor = constructors[0];

            // TODO: work with multiple constructors

            if (constructor.getAnnotation(Inject.class) == null) {
                instance = constructor.newInstance();
            } else {
                Class<?>[] paramClasses = constructor.getParameterTypes();
                Object[] values = new Object[paramClasses.length];
                for (int i = 0; i < paramClasses.length; i++) {
                    values[i] = get(paramClasses[i]);
                }
                instance = constructor.newInstance(values);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);  // TODO: work with exceptions
        }

        return instance;
    }
}
