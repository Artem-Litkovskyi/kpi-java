package org.fpm.di;

import org.fpm.di.example.MySingleton;
import org.fpm.di.example.MyPrototype;
import org.fpm.di.example.A;
import org.fpm.di.example.B;
import org.fpm.di.example.UseA;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class RealTest {

    private Container container;

    @Before
    public void setUp() {
        Environment env = new EnvironmentImpl();
        container = env.configure(new MyConfigurationExtended());
    }

    @Test
    public void shouldInjectSingleton() {
        assertSame(container.getComponent(MySingleton.class), container.getComponent(MySingleton.class));
    }

    @Test
    public void shouldInjectPrototype() {
        assertNotSame(container.getComponent(MyPrototype.class), container.getComponent(MyPrototype.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
        */
        final B bAsSingleton = container.getComponent(B.class);
        assertSame(container.getComponent(A.class), bAsSingleton);
        assertSame(container.getComponent(B.class), bAsSingleton);
    }

    @Test
    public void shouldBuildInjectDependencies() {
        final UseA hasADependency = container.getComponent(UseA.class);
        assertSame(hasADependency.getDependency(), container.getComponent(B.class));
    }

    @Test
    public void shouldInjectDependencyChain() {
        // Should instantiate regardless of the binding order in the configuration
        final MyObjectWithDependencyChain object = container.getComponent(MyObjectWithDependencyChain.class);

        // Should create a new instance of MyPrototype
        assertNotSame(object.getMyPrototype(), container.getComponent(MyPrototype.class));

        // Should create a new instance of UseA
        // Should inject the same instance of B into the new instance of UseA
        assertSame(object.getUseA().getDependency(), container.getComponent(B.class));
    }

    @Test(expected = ClassNotBoundException.class)
    public void shouldThrowClassNotBoundException() {
        container.getComponent(MyObjectNotBound.class);
    }

    @Test(expected = NoValidConstructorException.class)
    public void shouldThrowNoValidConstructorException() {
        container.getComponent(MyObjectWithNoValidConstructors.class);
    }
}
