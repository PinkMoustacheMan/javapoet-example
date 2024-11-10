package com.example.processor;

import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImplementationGenerator {

    private final Types types;

    public static ImplementationGenerator with(final Types types) {
        return new ImplementationGenerator(types);
    }

    public ImplementationGenerator(final Types types) {
        this.types = types;
    }

    public JavaFile generate(final TypeElement typeElement) {

        final TypeSpec.Builder builder =
                TypeSpec.classBuilder("TestImplementation")
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(typeElement.asType());

        this.getAllMethods(typeElement).forEach(method -> {
            builder.addMethod(
                    MethodSpec.overriding(method, (DeclaredType) typeElement.asType(), this.types).build()
            );
        });

        return JavaFile
                .builder("com.example.test.impl", builder.build())
                .indent("\t")
                .build();
    }

    public List<ExecutableElement> getAllMethods(final TypeElement type) {
        final Map<String, ExecutableElement> methods = new LinkedHashMap<>();
        collectMethods(type, methods);
        return new ArrayList<>(methods.values().stream().filter(a -> !a.isDefault()).toList());
    }

    private void collectMethods(final TypeElement type, final Map<String, ExecutableElement> methods) {
        // Get methods declared in the current type
        for (final ExecutableElement method : ElementFilter.methodsIn(type.getEnclosedElements())) {
            methods.putIfAbsent(getMethodSignature(method), method);
        }

        // Get methods from interfaces
        for (final TypeMirror interfaceType : type.getInterfaces()) {
            final TypeElement interfaceElement = (TypeElement) this.types.asElement(interfaceType);
            collectMethods(interfaceElement, methods);
        }

        // Get methods from the superclass, if any
        final TypeMirror superclass = type.getSuperclass();
        if (superclass.getKind() != TypeKind.NONE) {
            final TypeElement superclassElement = (TypeElement) this.types.asElement(superclass);
            collectMethods(superclassElement, methods);
        }
    }

    private String getMethodSignature(final ExecutableElement method) {
        final StringBuilder signature = new StringBuilder(method.getSimpleName().toString());
        signature.append("(");
        final String parameters = method.getParameters().stream()
                .map(param -> this.types.erasure(param.asType()).toString())
                .collect(Collectors.joining(","));
        signature.append(parameters);
        signature.append(")");
        return signature.toString();
    }

}
