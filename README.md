## This is a test project to reproduce a javapoet issue with bounded types

### Problem description

I am trying to create an annotation processor to generate implementations of interfaces using java 23 and javapoet
0.5.0.
Those interfaces contain bounded types such as `<S extends T>` where `T` is a generic type variable that is part of the
interface itself (which gets specified with a concrete type using another interface).

These would be the classes I work with.

```
public interface TestService<T> {
    <S extends T> void methodWithBoundedType(S t);
    void methodWithUnboundedType(T t);
}

@GenerateImplementation
public interface TestServiceExtension extends TestService<String> {
    // empty
}
```

The code that is generated looks as follows:

```

public class TestImplementation implements TestServiceExtension {
    @Override
    public <S extends T> void methodWithBoundedType(final S t) {
        // some code
    }

    @Override
    public void methodWithUnboundedType(final String t) {
        // some code
    }
}
```

I would expect it to look as follows. `T` being replaced with whatever type I have specified in my interface

```
public class TestImplementation implements TestServiceExtension {
    @Override
    public <S extends String> void methodWithBoundedType(final S t) {
        // some code
    }

    @Override
    public void methodWithUnboundedType(final String t) {
        // some code    
    }
}
```

### Project structure

The project is split into two parts.

- javapoet-example-processor:
  containing the annotation processor and the javapoet invocations.

- javapoet-example-test:
  containing the test files which are processed by the annotation processor.

### How to build

This project uses [Gradle](https://gradle.org/) as its build system.

The build process of this project invokes the annotation processor calling to javapoet, creating the invalid source
file (which in turn results in a failed build).

To build the project, run ``./gradlew assemble``