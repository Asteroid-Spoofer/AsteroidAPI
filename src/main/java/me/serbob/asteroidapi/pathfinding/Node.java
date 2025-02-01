package me.serbob.asteroidapi.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Node {
    private int x;
    private int y;
    private int z;

    public Node(Position3D pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public Position3D getBlockPos() {
        return new Position3D(x, y, z);
    }

    public boolean equals(Node node) {
        return equals(node.x, node.y, node.z);
    }

    public boolean equals(Position3D pos) {
        return equals(pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean equals(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }
}
