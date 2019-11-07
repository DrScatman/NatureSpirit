package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Location;
import nature_spirit.data.Quest;
import nature_spirit.wrappers.WalkingWrapper;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class NatureSpirit7 extends Task {

    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 45
                || Quest.NATURE_SPIRIT.getVarpValue() == 50
                || (Quest.NATURE_SPIRIT.getVarpValue() == 55 && !Inventory.contains("Mort myre fungus"));
    }

    @Override
    public int execute() {
        if (!Inventory.contains(i -> i.getName().equalsIgnoreCase("Druidic spell"))
                && !Inventory.contains("Mort myre fungus")) {

            if (!Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {
                WalkingWrapper.walkToNatureGrotto();
            } else {
                SceneObject grotto = SceneObjects.getNearest("Grotto");

                if (!Dialog.isOpen() && grotto != null && grotto.interact(a -> true)) {
                    Time.sleepUntil(Dialog::isOpen, 5000);
                }

                if (Dialog.isOpen()) {
                    if (Dialog.canContinue()) {
                        Dialog.processContinue();
                    }

                    if (Dialog.isViewingChatOptions()) {
                        Dialog.process("Could I have another bloom scroll please?");
                    }
                }
            }
        }

        if (Inventory.contains(i -> i.getName().equalsIgnoreCase("Druidic spell"))
                && !Inventory.contains("Mort myre fungus")) {

            if (Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {
                SceneObject bridge = SceneObjects.getNearest("Bridge");

                if (bridge != null && bridge.interact(a -> true)) {
                    Time.sleepUntil(() -> !Location.NATURE_GROTTO_AREA.contains(Players.getLocal()), 5000);
                }
            }

            if (Location.ROTTING_LOG_POSITION.distance() > 2) {
                Movement.walkTo(Location.ROTTING_LOG_POSITION, WalkingWrapper::shouldBreakWalkLoop);
            }

            SceneObject log = SceneObjects.getNearest("Rotting log");
            Item spell = Inventory.getFirst("Druidic spell");

            if (log != null && log.distance() < 2 && spell != null && spell.interact(ActionOpcodes.ITEM_ACTION_0)) {
                Time.sleepUntil(() -> SceneObjects.getNearest(3509) != null, 6000);

                SceneObject fungiLog = SceneObjects.getNearest(3509);

                if (fungiLog != null && (fungiLog.click()
                        || fungiLog.interact(a -> true)
                        || fungiLog.interact("Pick")
                        || fungiLog.interact(ActionOpcodes.OBJECT_ACTION_0)
                        || fungiLog.interact(ActionOpcodes.GROUND_ITEM_ACTION_0)
                        || fungiLog.interact(ActionOpcodes.INTERFACE_ACTION)
                        || fungiLog.interact(ActionOpcodes.ITEM_ACTION_0))) {
                    Time.sleepUntil(() -> Inventory.contains("Mort myre fungus"), 5000);
                } else {
                    Log.severe("Cant pick fungi");

                    if (log.getPosition().equals(Players.getLocal().getPosition())) {
                        Movement.walkTo(Location.ROTTING_LOG_POSITION.translate(Random.nextInt(-1, 1), Random.nextInt(-1, 1)));
                    }
                }
            }
        }

        if (Inventory.contains("Mort myre fungus")) {
            WalkingWrapper.walkToNatureGrotto();
        }

        return Main.getLoopReturn();
    }
}
