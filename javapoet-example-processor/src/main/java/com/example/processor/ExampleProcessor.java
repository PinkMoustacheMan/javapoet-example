package com.example.processor;

import io.avaje.prism.GenerateAPContext;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.util.Set;

@GenerateAPContext
@SupportedAnnotationTypes({"com.example.test.GenerateImplementation"})
public class ExampleProcessor extends AbstractProcessor {

    private static final boolean CONSUME_ANNOTATIONS = true;
    private static final boolean DONT_CONSUME_ANNOTATIONS = false;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        APContext.init(processingEnv);
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            APContext.clear();
            return DONT_CONSUME_ANNOTATIONS;
        }

        annotations.forEach(annotation -> {
            ElementFilter
                    .typesIn(APContext.elementsAnnotatedWith(roundEnv, annotation.getQualifiedName().toString()))
                    .forEach(element -> {
                        try {
                            ImplementationGenerator
                                    .with(APContext.types())
                                    .generate(element)
                                    .writeTo(APContext.filer());
                        } catch (final IOException e) {
                            APContext.messager().printError("Error while writing file.", element);
                            throw new RuntimeException(e);
                        }
                    });
        });

        return CONSUME_ANNOTATIONS;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
