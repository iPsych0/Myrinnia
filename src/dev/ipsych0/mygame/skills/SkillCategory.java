package dev.ipsych0.mygame.skills;

public enum SkillCategory {
	
	CraftingOther("Other"), Fish("Fish"), Ores("Ores"), Trees("Trees");
	
	private String name;
	
	SkillCategory(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
