/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1;

/**
 * Types of Uninformed Search Algorithms implemented.
 */
public enum Algorithm {

    BFS("Breadth-First Search"),
    DFS("Depth-First Search"),
    UNIFORM_COST("Uniform Cost Search");

    private final String name;

    Algorithm(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    /**
     * Identify an enum value from its name.
     * @param name The name by which to identify the enum value.
     * @return The enum value or null, if not found.
     */
    public static Algorithm fromName(String name){
        for(Algorithm a: values())
            if(a.name.equals(name))
                return a;
        return null;
    }

}
