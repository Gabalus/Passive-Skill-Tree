package daripher.skilltree.api.fractured;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class FracturedAscendancyUnlockEvent extends Event {
  private final ServerPlayer player;
  private final String ascendancyId;
  private final FracturedProgressionSource source;
  private boolean unlocked;

  public FracturedAscendancyUnlockEvent(
      ServerPlayer player, String ascendancyId, FracturedProgressionSource source) {
    this.player = player;
    this.ascendancyId = ascendancyId == null ? "" : ascendancyId;
    this.source = source;
  }

  public ServerPlayer getPlayer() {
    return player;
  }

  public String getAscendancyId() {
    return ascendancyId;
  }

  public FracturedProgressionSource getSource() {
    return source;
  }

  public boolean isUnlocked() {
    return unlocked;
  }

  public void setUnlocked(boolean unlocked) {
    this.unlocked = unlocked;
  }
}
