package com.example.test;

public interface TestService<T> {

    <S extends T> void methodWithBoundedType(S t);

    void methodWithUnboundedType(T t);

}
