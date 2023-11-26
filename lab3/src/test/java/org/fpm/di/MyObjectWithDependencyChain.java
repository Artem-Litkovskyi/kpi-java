package org.fpm.di;

import org.fpm.di.example.MyPrototype;
import org.fpm.di.example.UseA;

import javax.inject.Inject;

public class MyObjectWithDependencyChain {
    private final MyPrototype dependencyMyPrototype;
    private final UseA dependencyUseA;

    @Inject
    public MyObjectWithDependencyChain(MyPrototype dependencyMyPrototype, UseA dependencyUseA) {
        this.dependencyMyPrototype = dependencyMyPrototype;
        this.dependencyUseA = dependencyUseA;
    }

    public MyPrototype getMyPrototype() {
        return dependencyMyPrototype;
    }

    public UseA getUseA() {
        return dependencyUseA;
    }
}
