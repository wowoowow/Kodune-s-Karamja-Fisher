package Util;


import Main.Fisher;

public abstract class Node {
    protected final Fisher c;

    public Node(Fisher c) {this.c = c;}
    public abstract boolean validate();
    public abstract int execute();

}
