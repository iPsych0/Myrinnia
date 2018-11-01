package dev.ipsych0.myrinnia.items;

public enum EquipSlot{
	Helm(4), Body(5), Legs(6), Boots(7), Mainhand(1), Offhand(9), Earrings(0), Cape(10), Amulet(8), Gloves(2), Ring_Left(3), Ring_Right(11), None(12);
	
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
