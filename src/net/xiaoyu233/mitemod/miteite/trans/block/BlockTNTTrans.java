//package net.xiaoyu233.mitemod.miteite.trans.block;
//
//import net.minecraft.*;
//import net.xiaoyu233.fml.asm.annotations.Marker;
//import net.xiaoyu233.fml.asm.annotations.Transform;
//
//@Transform(BlockTNT.class)
//public class BlockTNTTrans extends Block {
//    @Marker
//    protected BlockTNTTrans(int par1, Material par2Material, BlockConstants constants) {
//        super(par1, par2Material, constants);
//    }
//
//    public int dropBlockAsEntityItem(BlockBreakInfo info) {
//        if (info.getResponsiblePlayer() != null) {
//            a(info.world, info.x, info.y, info.z, info.getMetadata(), info.getResponsiblePlayer());
//        }
//        if (info.wasExploded()){
//            return 0;
//        }
//        return super.dropBlockAsEntityItem(info);
//    }
//
//    public static void a(World par1World, int par2, int par3, int par4, int par5, EntityLiving par6EntityLivingBase) {
//        if (!par1World.I && (par5 & 1) == 1) {
//            EntityTNTPrimed var7 = new EntityTNTPrimed(par1World, (float)par2 + 0.5F, (float)par3 + 0.5F, (float)par4 + 0.5F, par6EntityLivingBase);
//            par1World.d(var7);
//            par1World.a(var7, "random.fuse", 1.0F, 1.0F);
//        }
//
//    }
//}
