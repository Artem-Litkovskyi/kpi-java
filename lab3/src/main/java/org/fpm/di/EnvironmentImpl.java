package org.fpm.di;

public class EnvironmentImpl implements Environment {

    @Override
    public Container configure(Configuration configuration) {
        BinderImpl binder = new BinderImpl();
        configuration.configure(binder);
        return new ContainerImpl(binder);
    }
}
