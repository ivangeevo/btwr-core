package org.btwr.core.config;

import com.google.common.reflect.Reflection;

public class BTWRModConfig {
    public static void register() {
        Reflection.initialize(BTWRModSettings.class);
    }
}