package dev.ipsych0.myrinnia.skills;

public enum SkillCategory {
	
	Leatherwork("Leatherwork"), CraftingOther("Other"), Fish("Fish"), Ores("Ores"), Trees("Trees"), BountyTargets("Targets");
	
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
