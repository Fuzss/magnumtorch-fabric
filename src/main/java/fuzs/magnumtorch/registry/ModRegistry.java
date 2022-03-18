package fuzs.magnumtorch.registry;

import com.google.common.collect.ImmutableSet;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.block.MagnumTorchBlock;
import fuzs.puzzleslib.registry.RegistryManager;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(MagnumTorch.MOD_ID);
    public static final Block DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("diamond_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final Block EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("emerald_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final Block AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("amethyst_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final PoiType DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(Registry.POINT_OF_INTEREST_TYPE, "diamond_magnum_torch", () -> PointOfInterestHelper.register(new ResourceLocation(MagnumTorch.MOD_ID, "diamond_magnum_torch"), 0, 1, ImmutableSet.copyOf(DIAMOND_MAGNUM_TORCH_BLOCK.getStateDefinition().getPossibleStates())));
    public static final PoiType EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(Registry.POINT_OF_INTEREST_TYPE, "emerald_magnum_torch", () -> PointOfInterestHelper.register(new ResourceLocation(MagnumTorch.MOD_ID, "emerald_magnum_torch"), 0, 1, ImmutableSet.copyOf(EMERALD_MAGNUM_TORCH_BLOCK.getStateDefinition().getPossibleStates())));
    public static final PoiType AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(Registry.POINT_OF_INTEREST_TYPE, "amethyst_magnum_torch", () -> PointOfInterestHelper.register(new ResourceLocation(MagnumTorch.MOD_ID, "amethyst_magnum_torch"), 0, 1, ImmutableSet.copyOf(AMETHYST_MAGNUM_TORCH_BLOCK.getStateDefinition().getPossibleStates())));

    public static void touch() {

    }
}