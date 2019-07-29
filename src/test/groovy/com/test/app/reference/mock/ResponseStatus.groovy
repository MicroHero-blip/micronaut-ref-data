package com.test.app.reference.mock

import groovy.transform.CompileStatic

@CompileStatic
enum ResponseStatus {
    SERVER_ERROR,
    CLIENT_ERROR,
    SUCCESS
}
