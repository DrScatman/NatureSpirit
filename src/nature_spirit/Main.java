package nature_spirit;

import nature_spirit.tasks.*;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
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

        submit(new BuySupplies(),
                new NatureSpirit0(),
                new NatureSpirit1(),
                new NatureSpirit2(),
                new NatureSpirit3(),
                new NatureSpirit4(),
                new NatureSpirit5(),
                new NatureSpirit6(),
                new NatureSpirit7(),
                new NatureSpirit8(),
                new NatureSpirit9(),
                new NatureSpirit10()
        );
    }

    @Override
    public void onStop() {
        Log.severe("Script Stopped");
    }

    public static int getLoopReturn() {
        return Random.low(600, 1600);
    }

    public static boolean useItemOnObject(String itemName, String objectName) {
        return useItemOnObject(itemName, SceneObjects.getNearest(objectName).getId());
    }

    public static boolean useItemOnObject(String itemName, Position objectPosition) {
        return useItemOnObject(itemName, SceneObjects.getFirstAt(objectPosition).getId());
    }

    public static boolean useItemOnObject(String itemName, int objectID) {
        Item item = Inventory.getFirst(itemName);
        if (item != null && (item.interact(a -> a.equalsIgnoreCase("Use")) || item.interact(ActionOpcodes.ITEM_ACTION_0))) {
            Time.sleepUntil(Inventory::isItemSelected, 5000);

            SceneObject object = SceneObjects.getNearest(objectID);
            if (object != null && (object.interact(ActionOpcodes.ITEM_ON_OBJECT) || object.click())) {
                Time.sleepUntil(() -> Players.getLocal().isAnimating() && !Inventory.isItemSelected(), 5000);
                Time.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), 5000);
                return true;
            }
        }
        return false;
    }
}
