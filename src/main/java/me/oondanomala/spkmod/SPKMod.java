package me.oondanomala.spkmod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SPKMod.MODID, name = SPKMod.NAME, version = SPKMod.VERSION, acceptedMinecraftVersions = "1.8")
public class SPKMod {
    public static final String MODID = "spkmod";
    public static final String NAME = "SPK Mod";
    public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ItemSword sword = (ItemSword) Items.diamond_sword;
        sword.attackDamage = 1000;
        LOGGER.info("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
    }
}
