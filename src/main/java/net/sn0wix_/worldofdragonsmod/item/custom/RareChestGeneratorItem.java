package net.sn0wix_.worldofdragonsmod.item.custom;


import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RareChestGeneratorItem extends Item {
    public RareChestGeneratorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        BlockEntity clickedBlock = context.getWorld().getBlockEntity(blockPos);
        context.getPlayer().getItemCooldownManager().set(ModItems.RARE_CHEST_GENERATOR,20);


        if (clickedBlock instanceof ChestBlockEntity || clickedBlock instanceof BarrelBlockEntity) {

            for (int currentItemSlot = 0; currentItemSlot < 27; currentItemSlot++) {

                if (canBeAdded()){
                    int sance = random();

                    if (sance <= 40){

                        int sance2 = random();

                        if (sance2 <= 20){
                            ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(Items.BONE,pocet()));
                        }
                        if (sance2 <= 50 && sance2 > 20){
                            ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.ROTTEN_FLESH,pocet()));
                        }
                        if (sance2 <= 60 && sance2 > 50){
                            ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.STRING,pocet()));
                        }
                        if (sance2 <= 70 && sance2 > 60){
                            ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.SPIDER_EYE,pocet()));
                        }
                        if (sance2 <= 100 && sance2 > 70){
                            ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(ModItems.SCRAP,pocet()));
                        }
                    }

                    if (sance <= 65 && sance > 40){
                        ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.ARROW));
                    }

                    if (sance <= 80 && sance > 65){
                        int sance2 = random();

                        if (sance2 <= 30){
                            int sance3 = random();
                            if (sance3 <= 45){
                                ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.BOW).setCustomName(
                                        Text.literal("§a§lBow")));
                            }

                            if (sance3 <= 90 && sance3 > 45){
                                ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.CROSSBOW).setCustomName(
                                        Text.literal("§a§lCrossbow")));
                            }

                            if (sance3 <= 100 && sance3 > 90){
                                ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot, new ItemStack(Items.BOW).setCustomName(
                                        Text.literal("§c§lTrident")));

                            }
                        }
                    }

                    if (sance <= 100 &&sance > 80){
                        int sance2 = random();

                      //  if (sance2 = )
                    }
                }
            }

            context.getPlayer().sendMessage(Text.literal("§bChest was succesfully filled!"), true);
        }

        return super.useOnBlock(context);
    }

    private boolean canBeAdded(){
        boolean vysledek = false;

        int sance = (int) (Math.random()*100);

        if (sance <= 60){
            vysledek = true;
        }
        return vysledek;
    }


    private int pocet(){
        int sance = (int) (Math.random()*100);
        int pocet = 1;

        if (sance < 70){
            pocet++;
        }

        return pocet;
    }


    private int random(){
        return (int) (Math.random()*100);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.worldofdragonsmod.chestGenerator.hasShiftDown"));
        }else {
            tooltip.add(Text.translatable("item.worldofdragonsmod.chestGenerator"));
        }
    }
}
