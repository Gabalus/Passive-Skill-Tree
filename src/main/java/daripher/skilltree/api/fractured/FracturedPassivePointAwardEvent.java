package daripher.skilltree.api.fractured;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class FracturedPassivePointAwardEvent extends Event {
  private final ServerPlayer player;
  private final FracturedProgressionSource source;
  private int amount;
  private String reason;

  public FracturedPassivePointAwardEvent(
      ServerPlayer player, int amount, FracturedProgressionSource source, String reason) {
    this.player = player;
    this.amount = Math.max(0, amount);
    this.source = source;
    this.reason = reason == null ? "" : reason;
  }

  public ServerPlayer getPlayer() {
    return player;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = Math.max(0, amount);
  }

  public FracturedProgressionSource getSource() {
    return source;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason == null ? "" : reason;
  }
}
