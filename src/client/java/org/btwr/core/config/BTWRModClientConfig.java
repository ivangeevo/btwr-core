package org.btwr.core.config;

import com.google.common.reflect.Reflection;

public class BTWRModClientConfig {
    public static void register() {
        Reflection.initialize(BTWRModClientSettings.class);
    }
}