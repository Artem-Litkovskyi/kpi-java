package org.fpm.di;

public class ContainerImpl implements Container {
    BinderImpl binder;

    public ContainerImpl(BinderImpl binder) {
        this.binder = binder;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        return binder.get(clazz);
    }
}
