package net.sn0wix_.worldofdragonsmod.common.item.custom;


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
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommonChestGeneratorItem extends Item {
    public CommonChestGeneratorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        BlockEntity clickedBlock = context.getWorld().getBlockEntity(blockPos);
        try {
            context.getPlayer().getItemCooldownManager().set(ModItems.COMMON_CHEST_GENERATOR,20);
        }catch (NullPointerException ignored){}



        if (clickedBlock instanceof ChestBlockEntity || clickedBlock instanceof BarrelBlockEntity) {

            for (int currentItemSlot = 0; currentItemSlot < 27; currentItemSlot++) {

                if (canBeAdded()){
                    int sance = random();

                    if (sance <= 30){
                        ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(Items.ROTTEN_FLESH, pocet()));
                    }

                    if (sance <= 50 && sance > 30){
                        ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(Items.BONE, pocet()));
                    }

                    if (sance <= 70 && sance > 50){
                        ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(Items.ARROW, pocet()));
                    }

                    if (sance <= 80 && sance > 70){
                        ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(Items.STRING, pocet()));
                    }

                    if (sance <= 100 && sance > 80){
                         var sance2 = random();
                         if (sance2 <= 100 && sance2 > 35){
                             ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(ModItems.COMMON_LOOT, pocet()));
                         }else {
                             ((LootableContainerBlockEntity) clickedBlock).setStack(currentItemSlot,new ItemStack(ModItems.RARE_LOOT, pocet()));
                         }
                    }
                }
            }
            context.getPlayer().sendMessage(Text.literal("§7Chest was succesfully filled!"), true);
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

        if (sance <60){
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
