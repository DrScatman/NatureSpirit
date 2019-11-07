package nature_spirit;

import nature_spirit.tasks.*;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.providers.subclass.GameCanvas;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;


@ScriptMeta(name = "Nature Spirit", desc = "Nature Spirit", developer = "DrScatman")
public class Main extends TaskScript {

    @Override
    public void onStart() {
        Log.fine("Starting Nature Spirit");

        if (!GameCanvas.isInputEnabled()) {
            GameCanvas.setInputEnabled(true);
        }

        submit( new BuySupplies(),
                new NatureSpirit0(),
                new NatureSpirit1(),
                new NatureSpirit2(),
                new NatureSpirit3(),
                new NatureSpirit4(),
                new NatureSpirit5(),
                new NatureSpirit6()
        );
    }

    @Override
    public void onStop() {
        Log.severe("Script Stopped");
    }

    public static int getLoopReturn() {
        return Random.low(600, 1200);
    }
}
