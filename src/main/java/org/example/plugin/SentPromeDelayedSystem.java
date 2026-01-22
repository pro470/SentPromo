package org.example.plugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.Main;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.DelayedSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fi.sulku.hytale.TinyMsg;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class SentPromeDelayedSystem extends DelayedSystem<EntityStore> {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public SentPromeDelayedSystem() {
        super(5.0f);
    }

    @Override
    public void delayedTick(float v, int i, @Nonnull Store<EntityStore> store) {

        Gson gson = new Gson();
        LOGGER.atInfo().log("Hello from ");
        InputStream in = Main.class
                .getClassLoader()
                .getResourceAsStream("strings.json");

        if (in == null) {
            LOGGER.atInfo().log("no file");
            return;
        }

        Type type = new TypeToken<List<String>>() {
        }.getType();

        List<String> list = gson.fromJson(
                new InputStreamReader(in),
                type
        );

        for (String string : list) {
            LOGGER.atInfo().log(string);
            Message message = TinyMsg.parse(string);
            Universe.get().sendMessage(message);
        }
    }
}
