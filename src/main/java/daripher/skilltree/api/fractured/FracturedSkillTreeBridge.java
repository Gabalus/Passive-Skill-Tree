package daripher.skilltree.api.fractured;

import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.network.NetworkDispatcher;
import daripher.skilltree.network.message.SyncPlayerSkillsMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = SkillTreeMod.MOD_ID)
public final class FracturedSkillTreeBridge {
  private FracturedSkillTreeBridge() {}

  @SubscribeEvent
  public static void onPassivePointAward(FracturedPassivePointAwardEvent event) {
    ServerPlayer player = event.getPlayer();
    int amount = Math.max(0, event.getAmount());
    if (amount <= 0 || player == null || player.level().isClientSide) {
      return;
    }

    IPlayerSkills skills = PlayerSkillsProvider.get(player);
    skills.grantSkillPoints(amount);
    sync(player);

    player.sendSystemMessage(
        Component.literal("Gained " + amount + " Passive Skill Tree point" + (amount == 1 ? "" : "s") + ".")
            .withStyle(ChatFormatting.AQUA));
    if (!event.getReason().isBlank()) {
      player.sendSystemMessage(Component.literal(event.getReason()).withStyle(ChatFormatting.GRAY));
    }
  }

  @SubscribeEvent
  public static void onAscendancyUnlock(FracturedAscendancyUnlockEvent event) {
    ServerPlayer player = event.getPlayer();
    if (player == null || player.level().isClientSide || event.getAscendancyId().isBlank()) {
      return;
    }

    // MVP: mark the event as handled and notify the player.
    // Follow-up patch should connect this to native class/tree selection data once the target data format is mapped.
    event.setUnlocked(true);
    player.sendSystemMessage(
        Component.literal("Ascendancy unlocked: " + event.getAscendancyId())
            .withStyle(ChatFormatting.GOLD));
  }

  private static void sync(ServerPlayer player) {
    if (NetworkDispatcher.network_channel == null) {
      return;
    }
    NetworkDispatcher.network_channel.send(
        PacketDistributor.PLAYER.with(() -> player), new SyncPlayerSkillsMessage(player));
  }
}
