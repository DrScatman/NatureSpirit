package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Location;
import nature_spirit.data.Quest;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class NatureSpirit1 extends Task {

    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 5;
    }

    @Override
    public int execute() {
        if (Location.DUNGEON_AREA.contains(Players.getLocal())) {
            SceneObject holyBarrier = SceneObjects.getNearest("Holy barrier");

            if (holyBarrier != null && holyBarrier.interact(a -> true)) {
                Time.sleepUntil(() -> !Location.DUNGEON_AREA.contains(Players.getLocal()), 10_000);
            }
        }

        if (!Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {

            if (Location.NATURE_GROTTO_BRIDGE_POSITION.distance() > 3) {
                Movement.walkTo(Location.NATURE_GROTTO_BRIDGE_POSITION, Main::shouldBreakWalkLoop);

                if (Location.NATURE_GROTTO_BRIDGE_POSITION.distance() > 3 && !Movement.isRunEnabled()) {
                    Movement.toggleRun(true);
                }
            } else {
                SceneObject bridge = SceneObjects.getNearest("Bridge");
                if (bridge != null && bridge.interact(a -> true)) {
                    Time.sleepUntil(() -> Players.getLocal().isMoving() || Players.getLocal().isAnimating(), 5000);
                }
            }
        }

        if (Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {

            if (Inventory.contains("Ghostspeak amulet")) {
                Inventory.getFirst("Ghostspeak amulet").interact(a -> true);
                Time.sleepUntil(() -> Equipment.contains("Ghostspeak amulet"), 5000);
            }

            SceneObject grotto = SceneObjects.getNearest("Grotto tree");

            if (grotto != null && grotto.interact("Search")) {
                Time.sleep(2000, 2500);
            }

            SceneObject bowl = SceneObjects.getNearest("Washing bowl");

            if (bowl != null && bowl.interact("Take")) {
                Time.sleep(2000, 2500);
            }
        }

        return Main.getLoopReturn();
    }
}
