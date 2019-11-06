package nature_spirit;

import nature_spirit.tasks.BuySupplies;
import nature_spirit.tasks.NatureSpirit0;
import nature_spirit.tasks.NatureSpirit1;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
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
                new NatureSpirit1());
    }

    @Override
    public void onStop() {
        Log.severe("Script Stopped");
    }

    public static boolean shouldBreakWalkLoop() {
        Npc attacker = Npcs.getNearest(a -> true);
        return Movement.getRunEnergy() > 0 && attacker != null
                && attacker.getTarget() != null
                && attacker.getTarget().equals(Players.getLocal());
    }

    public static int getLoopReturn() {
        return Random.low(600, 1200);
    }
}
