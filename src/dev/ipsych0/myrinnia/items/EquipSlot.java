package dev.ipsych0.myrinnia.items;

public enum EquipSlot{
	HELM(4), BODY(5), LEGS(6), BOOTS(7), MAINHAND(1), OFFHAND(9), EARRINGS(0), CAPE(10), AMULET(8), GLOVES(2), RING_LEFT(3), RING_RIGHT(11), NONE(12);
	
	private int slotId;
	
	EquipSlot(int slotId) {
		this.slotId = slotId;
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	
	
}
