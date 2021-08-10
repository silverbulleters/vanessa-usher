/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.ioc

/**
 * https://jjba.dev/posts/jenkins-shared-library/
 */
class ContextRegistry implements Serializable {
    private static IContext context

    static void registerContext(IContext context) {
        ContextRegistry.context = context
    }

    static void registerDefaultContext(Object steps) {
        ContextRegistry.context = new DefaultContext(steps)
    }

    static IContext getContext() {
        return ContextRegistry.context
    }
}