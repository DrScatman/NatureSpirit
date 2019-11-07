package nature_spirit.tasks;

import nature_spirit.Main;
import nature_spirit.data.Location;
import nature_spirit.data.Quest;
import nature_spirit.wrappers.WalkingWrapper;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class NatureSpirit8 extends Task {
    @Override
    public boolean validate() {
        return Quest.NATURE_SPIRIT.getVarpValue() == 55;
    }

    @Override
    public int execute() {
       if (!Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {
           WalkingWrapper.walkToNatureGrotto();
       }

       if (Location.NATURE_GROTTO_AREA.contains(Players.getLocal())) {

           SceneObject brownStone =  SceneObjects.getNearest(3527);

           if (brownStone != null && Inventory.use(i -> i.getName().equals("Mort myre fungus"), brownStone)) {
               Time.sleepUntil(() -> !Players.getLocal().isMoving() && !Players.getLocal().isAnimating(), 5000);
           }

           SceneObject greyStone =  SceneObjects.getNearest(3529);

           if (greyStone != null && Inventory.use(i -> i.getName().equals("A used spell"), greyStone)) {
               Time.sleepUntil(() -> !Players.getLocal().isMoving() && !Players.getLocal().isAnimating(), 5000);
           }

           SceneObject orangeStone =  SceneObjects.getNearest(3528);
           if (!Players.getLocal().getPosition().equals(orangeStone.getPosition())) {
               Movement.walkTo(orangeStone::getPosition);
           }

           SceneObject grotto = SceneObjects.getNearest("Grotto");

           if (!Dialog.isOpen() && grotto != null && grotto.interact(a -> true)) {
               Time.sleepUntil(Dialog::isOpen, 5000);
           }

           if (Dialog.isOpen()) {
               if (Dialog.canContinue()) {
                   Dialog.processContinue();
               }

               if (Dialog.isViewingChatOptions()) {
                   //Dialog.process("Could I have another bloom scroll please?");
               }
           }
       }

       return Main.getLoopReturn();
    }
}
