/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemHoe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.item.crafting.FurnaceRecipes
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCrystalFurnace
extends TileEntity
implements ISidedInventory {
    private static final int[] slots_top = new int[]{0};
    private static final int[] slots_bottom = new int[]{2, 1};
    private static final int[] slots_sides = new int[]{1};
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;
    private String field_94130_e;

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int par1) {
        return this.furnaceItemStacks[par1];
    }

    public ItemStack decrStackSize(int par1, int par2) {
        if (this.furnaceItemStacks[par1] != null) {
            if (this.furnaceItemStacks[par1].stackSize <= par2) {
                ItemStack itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            ItemStack itemstack = this.furnaceItemStacks[par1].splitStack(par2);
            if (this.furnaceItemStacks[par1].stackSize == 0) {
                this.furnaceItemStacks[par1] = null;
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.furnaceItemStacks[par1] != null) {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.furnaceItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.field_94130_e : "container.furnace";
    }

    public boolean isCustomInventoryName() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void setCustomInventoryName(String par1Str) {
        this.field_94130_e = par1Str;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 < 0 || b0 >= this.furnaceItemStacks.length) continue;
            this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttagcompound1);
        }
        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = TileEntityCrystalFurnace.getItemBurnTime(this.furnaceItemStacks[1]);
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94130_e = par1NBTTagCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] == null) continue;
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)i);
            this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
            nbttaglist.appendTag((NBTBase)nbttagcompound1);
        }
        par1NBTTagCompound.setTag("Items", (NBTBase)nbttaglist);
        if (this.isCustomInventoryName()) {
            par1NBTTagCompound.setString("CustomName", this.field_94130_e);
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(value=Side.CLIENT)
    public int getCookProgressScaled(int par1) {
        return this.furnaceCookTime * par1 / 150;
    }

    @SideOnly(value=Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 150;
        }
        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity() {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }
        if (!this.world.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.furnaceBurnTime = TileEntityCrystalFurnace.getItemBurnTime(this.furnaceItemStacks[1]);
                if (this.furnaceBurnTime > 0) {
                    flag1 = true;
                    if (this.furnaceItemStacks[1] != null) {
                        --this.furnaceItemStacks[1].stackSize;
                        if (this.furnaceItemStacks[1].stackSize == 0) {
                            this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt()) {
                ++this.furnaceCookTime;
                if (this.furnaceCookTime == 150) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }
            if (flag != this.furnaceBurnTime > 0) {
                flag1 = true;
                CrystalFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.world, this.x, this.y, this.z);
            }
        }
        if (flag1) {
            this.markDirty();
        }
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
        if (itemstack == null) {
            return false;
        }
        if (this.furnaceItemStacks[2] == null) {
            return true;
        }
        if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) {
            return false;
        }
        int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
        return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemstack.copy();
            } else if (this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize;
            }
            --this.furnaceItemStacks[0].stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack par0ItemStack) {
        if (par0ItemStack == null) {
            return 0;
        }
        Item item = par0ItemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem((Item)item) != Blocks.AIR) {
            Block block = Block.getBlockFromItem((Item)item);
            if (block == Blocks.WOODEN_SLAB) {
                return 150;
            }
            if (block.getMaterial() == Material.WOOD) {
                return 300;
            }
            if (block == Blocks.COAL_BLOCK) {
                return 16000;
            }
        }
        if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item == Items.STICK) {
            return 100;
        }
        if (item == Items.COAL) {
            return 1600;
        }
        if (item == Items.LAVA_BUCKET) {
            return 20000;
        }
        if (item == Item.getItemFromBlock((Block)Blocks.SAPLING)) {
            return 100;
        }
        if (item == Items.BLAZE_ROD) {
            return 2400;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.CrystalCoal)) {
            return 20000;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalTreeLog)) {
            return 800;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.CrystalPlanksBlock)) {
            return 400;
        }
        return GameRegistry.getFuelValue((ItemStack)par0ItemStack);
    }

    public static boolean isItemFuel(ItemStack par0ItemStack) {
        return TileEntityCrystalFurnace.getItemBurnTime(par0ItemStack) > 0;
    }

    public boolean isUseableByPlayer(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        return this.world.getTileEntity(new net.minecraft.util.math.BlockPos(this.x, this.y, this.z)) != this ? false : par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) <= 64.0;
    }

    public void openChest() {
    }

    public void closeChest() {
    }

    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 == 2 ? false : (par1 == 1 ? TileEntityCrystalFurnace.isItemFuel(par2ItemStack) : true);
    }

    public int[] getSlotsForFace(int par1) {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
        return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.BUCKET;
    }

    /*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemHoe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.item.crafting.FurnaceRecipes
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCrystalFurnace
extends TileEntity
implements ISidedInventory {
    private static final int[] slots_top = new int[]{0};
    private static final int[] slots_bottom = new int[]{2, 1};
    private static final int[] slots_sides = new int[]{1};
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;
    private String field_94130_e;

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int par1) {
        return this.furnaceItemStacks[par1];
    }

    public ItemStack decrStackSize(int par1, int par2) {
        if (this.furnaceItemStacks[par1] != null) {
            if (this.furnaceItemStacks[par1].stackSize <= par2) {
                ItemStack itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            ItemStack itemstack = this.furnaceItemStacks[par1].splitStack(par2);
            if (this.furnaceItemStacks[par1].stackSize == 0) {
                this.furnaceItemStacks[par1] = null;
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.furnaceItemStacks[par1] != null) {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.furnaceItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInventoryName() {
        return this.isCustomInventoryName() ? this.field_94130_e : "container.furnace";
    }

    public boolean isCustomInventoryName() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void setCustomInventoryName(String par1Str) {
        this.field_94130_e = par1Str;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 < 0 || b0 >= this.furnaceItemStacks.length) continue;
            this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttagcompound1);
        }
        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = TileEntityCrystalFurnace.getItemBurnTime(this.furnaceItemStacks[1]);
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94130_e = par1NBTTagCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] == null) continue;
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)i);
            this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
            nbttaglist.appendTag((NBTBase)nbttagcompound1);
        }
        par1NBTTagCompound.setTag("Items", (NBTBase)nbttaglist);
        if (this.isCustomInventoryName()) {
            par1NBTTagCompound.setString("CustomName", this.field_94130_e);
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(value=Side.CLIENT)
    public int getCookProgressScaled(int par1) {
        return this.furnaceCookTime * par1 / 150;
    }

    @SideOnly(value=Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 150;
        }
        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity() {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }
        if (!this.world.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.furnaceBurnTime = TileEntityCrystalFurnace.getItemBurnTime(this.furnaceItemStacks[1]);
                if (this.furnaceBurnTime > 0) {
                    flag1 = true;
                    if (this.furnaceItemStacks[1] != null) {
                        --this.furnaceItemStacks[1].stackSize;
                        if (this.furnaceItemStacks[1].stackSize == 0) {
                            this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt()) {
                ++this.furnaceCookTime;
                if (this.furnaceCookTime == 150) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }
            if (flag != this.furnaceBurnTime > 0) {
                flag1 = true;
                CrystalFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.world, this.x, this.y, this.z);
            }
        }
        if (flag1) {
            this.markDirty();
        }
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
        if (itemstack == null) {
            return false;
        }
        if (this.furnaceItemStacks[2] == null) {
            return true;
        }
        if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) {
            return false;
        }
        int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
        return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemstack.copy();
            } else if (this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize;
            }
            --this.furnaceItemStacks[0].stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack par0ItemStack) {
        if (par0ItemStack == null) {
            return 0;
        }
        Item item = par0ItemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem((Item)item) != Blocks.AIR) {
            Block block = Block.getBlockFromItem((Item)item);
            if (block == Blocks.WOODEN_SLAB) {
                return 150;
            }
            if (block.getMaterial() == Material.WOOD) {
                return 300;
            }
            if (block == Blocks.COAL_BLOCK) {
                return 16000;
            }
        }
        if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) {
            return 200;
        }
        if (item == Items.STICK) {
            return 100;
        }
        if (item == Items.COAL) {
            return 1600;
        }
        if (item == Items.LAVA_BUCKET) {
            return 20000;
        }
        if (item == Item.getItemFromBlock((Block)Blocks.SAPLING)) {
            return 100;
        }
        if (item == Items.BLAZE_ROD) {
            return 2400;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.CrystalCoal)) {
            return 20000;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalTreeLog)) {
            return 800;
        }
        if (item == Item.getItemFromBlock((Block)OreSpawnMain.CrystalPlanksBlock)) {
            return 400;
        }
        return GameRegistry.getFuelValue((ItemStack)par0ItemStack);
    }

    public static boolean isItemFuel(ItemStack par0ItemStack) {
        return TileEntityCrystalFurnace.getItemBurnTime(par0ItemStack) > 0;
    }

    public boolean isUseableByPlayer(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        return this.world.getTileEntity(new net.minecraft.util.math.BlockPos(this.x, this.y, this.z)) != this ? false : par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) <= 64.0;
    }

    public void openChest() {
    }

    

    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 == 2 ? false : (par1 == 1 ? TileEntityCrystalFurnace.isItemFuel(par2ItemStack) : true);
    }

    public int[] getSlotsForFace(int par1) {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
        return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.BUCKET;
    }

    public void openChest()

