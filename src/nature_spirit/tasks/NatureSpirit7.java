package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Location;
import nature_spirit.data.Quest;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.script.task.Task;

public class NatureSpirit7 extends Task {

    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 45;
    }

    @Override
    public int execute() {
        if (!Inventory.contains(i -> i.getName().toLowerCase().contains("shroom"))) {

            if (Location.DREZEL_POSITION.distance() > 3) {
                Movement.walkTo(Location.DREZEL_POSITION);
            }

            Npc drezel = Npcs.getNearest("Drezel");
            if (drezel != null && drezel.interact("Talk-to")) {
                Time.sleepUntil(Dialog::isOpen, 5000);
            }

        }

        return Main.getLoopReturn();
    }
}
