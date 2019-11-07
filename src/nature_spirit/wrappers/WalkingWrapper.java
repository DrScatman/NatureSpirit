package nature_spirit.wrappers;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;

public class WalkingWrapper {

    public static boolean shouldBreakWalkLoop() {
        Npc attacker = Npcs.getNearest(a -> true);
        return Movement.getRunEnergy() > 0 &&!Movement.isRunEnabled() && attacker != null
                && attacker.getTarget() != null
                && attacker.getTarget().equals(Players.getLocal());
    }
}