package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Quest;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class NatureSpirit4 extends Task {

    private boolean usedJournal;

    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 25;
    }

    @Override
    public int execute() {
        SceneObject tree = SceneObjects.getNearest("Grotto tree");
        if (!Inventory.contains("Journal") && tree != null && tree.interact("Search")) {
            Time.sleepUntil(() -> Inventory.contains("Journal"), 5000);
        }

        if (!usedJournal && Inventory.contains("Journal")) {
            SceneObject grotto = SceneObjects.getNearest("Grotto");

            if (!Dialog.isOpen() && grotto != null && grotto.interact(a -> true)) {
                Time.sleepUntil(Dialog::isOpen, 5000);
            }

            Inventory.getFirst("Journal").interact("Use");
            Time.sleepUntil(Inventory::isItemSelected, 5000);
            Time.sleep(600, 800);

            Npc filliman = Npcs.getNearest("Filliman Tarlock");
            if (filliman != null && filliman.interact("Use")) {
                usedJournal = true;
                Time.sleepUntil(Dialog::canContinue, 5000);
            }
        }

        if (Dialog.canContinue() || Dialog.isProcessing()) {
            Dialog.processContinue();
            Main.getLoopReturn();
        }

        return Main.getLoopReturn();
    }
}
