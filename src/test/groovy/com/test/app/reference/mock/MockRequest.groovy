package com.test.app.reference.mock

import org.junit.ComparisonFailure

import static groovy.json.JsonOutput.prettyPrint
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals

abstract class MockRequest<T extends MockRequest> {

    static def assertJsonEquals(String expected, String actual) {
        tryAssertion({ assertEquals(expected, actual, false) }, expected, actual)
    }

    static def tryAssertion(Closure<Void> assertion, String expected, String actual) {
        try {
            assertion.run()
            return true
        } catch (AssertionError e) {
            throw new ComparisonFailure(e.getMessage(), prettyPrint(expected), prettyPrint(actual))
        }
    }

    protected abstract boolean matches(T request);
}
