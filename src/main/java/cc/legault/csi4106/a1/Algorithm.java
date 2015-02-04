package cc.legault.csi4106.a1;

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

}
