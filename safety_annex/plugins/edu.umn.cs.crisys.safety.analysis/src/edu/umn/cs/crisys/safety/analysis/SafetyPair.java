package edu.umn.cs.crisys.safety.analysis;

public class SafetyPair<NestedDotID,String> {
	
	  private final NestedDotID left;
	  private final String right;

	  public SafetyPair(NestedDotID left, String right) {
	    this.left = left;
	    this.right = right;
	  }

	  public NestedDotID getLeft() { return left; }
	  public String getRight() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof SafetyPair)) return false;
	    SafetyPair pairo = (SafetyPair) o;
	    return this.left.equals(pairo.getLeft()) &&
	           this.right.equals(pairo.getRight());
	  }

}
