
public class node {
	int aX;
	int aY;
	int bX;
	int bY;
	double fValue;
	int level;
	node parent;
	
	public node(int aX, int aY, int bX, int bY, double fValue, int level, node parent){
		this.aX = aX;
		this.aY = aY;
		this.bX = bX;
		this.bY = bY;
		this.fValue = fValue;
		this.level = level;
		this.parent = parent;
	}
}
