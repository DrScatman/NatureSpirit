package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Quest;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class NatureSpirit3 extends Task {

    private boolean usedMirror;

    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 20;
    }

    @Override
    public int execute() {
        if (!Inventory.contains("Mirror")) {

            Pickable bowl = Pickables.getNearest("Washing bowl");
            Pickable mirror = Pickables.getNearest("Mirror");

            if (mirror == null && bowl != null && bowl.interact("Take")) {
                Time.sleepUntil(() -> Inventory.contains("Washing bowl"), 5000);
            }

            if (mirror != null && mirror.interact("Take")) {
                Time.sleepUntil(() -> Inventory.contains("Mirror"), 5000);
            }
        }

        if (Dialog.canContinue() || Dialog.isProcessing()) {
            Dialog.processContinue();

            Main.getLoopReturn();
        }

        if (!usedMirror && Inventory.contains("Mirror")) {
            SceneObject grotto = SceneObjects.getNearest("Grotto");

            if (!Dialog.isOpen() && grotto != null && grotto.interact(a -> true)) {
                Time.sleepUntil(Dialog::isOpen, 5000);
            }

            Inventory.getFirst("Mirror").interact("Use");
            Time.sleepUntil(Inventory::isItemSelected, 5000);
            Time.sleep(600, 800);

            Npc filliman = Npcs.getNearest("Filliman Tarlock");
            if (filliman != null && filliman.interact("Use")) {
                usedMirror = true;
                Time.sleepUntil(Dialog::canContinue, 5000);
            }
        }

        return Main.getLoopReturn();
    }
}
