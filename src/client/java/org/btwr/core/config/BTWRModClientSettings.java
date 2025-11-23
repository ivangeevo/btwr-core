package org.btwr.core.config;

import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class BTWRModClientSettings {

    public static Supplier<Boolean> exampleClientSetting;

    static {
        IConfigBuilder builder = ConfigBuilders.newTomlConfig("btwr", "btwr/core_client", false);

        exampleClientSetting = builder
                .comment("Example setting comment.")
                .define("exampleClientSetting", true);

        builder.build();
    }
}
