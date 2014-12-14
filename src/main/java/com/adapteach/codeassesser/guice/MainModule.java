package com.adapteach.codeassesser.guice;

import com.adapteach.codeassesser.verify.SubmissionVerifier;
import com.adapteach.codeassesser.web.JsonTransformer;
import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(JsonTransformer.class).asEagerSingleton();
        bind(SubmissionVerifier.class).asEagerSingleton();

    }

}
