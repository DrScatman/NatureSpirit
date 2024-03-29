package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Location;
import nature_spirit.data.Quest;
import nature_spirit.wrappers.WalkingWrapper;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
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
            WalkingWrapper.walkToNatureGrotto();
        }

        if (Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {

            if (Inventory.contains("Ghostspeak amulet")) {
                Inventory.getFirst("Ghostspeak amulet").interact(a -> true);
                Time.sleepUntil(() -> Equipment.contains("Ghostspeak amulet"), 5000);
            }

            SceneObject grotto = SceneObjects.getNearest("Grotto");
            SceneObject bridge = SceneObjects.getNearest("Bridge");

            if (grotto != null && grotto.interact(a -> true)) {
                if (Time.sleepUntil(() -> Quest.NATURE_SPIRIT.getVarpValue() == 10, 5000)) {
                    Time.sleepUntil(Dialog::isOpen, 1000, 5000);
                } else {
                    if (bridge != null && bridge.interact(a -> true)) {
                        Time.sleepUntil(() -> !Location.NATURE_GROTTO_AREA.contains(Players.getLocal()), 5000);
                    }
                }
            }
        }

        return Main.getLoopReturn();
    }
}
