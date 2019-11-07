package nature_spirit.wrappers;

import nature_spirit.data.Location;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;

public class WalkingWrapper {

    public static boolean shouldBreakWalkLoop() {
        Npc attacker = Npcs.getNearest(a -> true);
        return Movement.getRunEnergy() > 0
                && !Movement.isRunEnabled()
                && Players.getLocal().isHealthBarVisible()
                && attacker != null
                && attacker.getTarget() != null
                && attacker.getTarget().equals(Players.getLocal());
    }

    public static void walkToNatureGrotto() {
        if (Location.NATURE_GROTTO_BRIDGE_POSITION.distance() > 3) {
            Movement.walkTo(Location.NATURE_GROTTO_BRIDGE_POSITION, WalkingWrapper::shouldBreakWalkLoop);

            if (Location.NATURE_GROTTO_BRIDGE_POSITION.distance() > 3 && !Movement.isRunEnabled()) {
                Movement.toggleRun(true);
            }
        } else {
            SceneObject bridge = SceneObjects.getNearest("Bridge");
            if (bridge != null && bridge.interact(a -> true)) {
                Time.sleepUntil(() -> Location.NATURE_GROTTO_AREA.contains(Players.getLocal()), 5000);
            }
        }
    }
}
