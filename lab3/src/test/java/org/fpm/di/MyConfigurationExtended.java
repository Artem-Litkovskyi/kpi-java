package org.fpm.di;

import org.fpm.di.example.*;

public class MyConfigurationExtended implements Configuration {
    @Override
    public void configure(Binder binder) {
        binder.bind(MySingleton.class);
        binder.bind(MyPrototype.class);
        binder.bind(MyObjectWithNoValidConstructors.class);

        binder.bind(MyObjectWithDependencyChain.class);  // Uses MyPrototype and UseA

        binder.bind(UseA.class);

        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
    }
}
