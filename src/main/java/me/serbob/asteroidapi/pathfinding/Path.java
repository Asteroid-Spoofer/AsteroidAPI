package me.serbob.asteroidapi.pathfinding;

import lombok.Getter;
import me.serbob.asteroidapi.registries.FakePlayerEntity;
import org.bukkit.util.Vector;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

@Getter
public class Path {
    private final Deque<Node> nodes;
    private int nextNodeIndex;

    public Path(Node[] nodes) {
        this.nodes = new ArrayDeque<>();
        this.nodes.addAll(Arrays.asList(nodes));
    }

    public Node getNode(int index) {
        return (Node) nodes.toArray()[index];
    }

    public int size() {
        return nodes.size();
    }

    public Vector getPosAtNode(FakePlayerEntity fake, int index) {
        try {
            Node node = getNode(index);
            return new Vector(node.getX() + 0.5D, node.getY(), node.getZ() + 0.5D);
        } catch (IndexOutOfBoundsException e) {
            return fake.getEntityPlayer().getLocation().toVector();
        }
    }

    public Vector getNextPos(FakePlayerEntity fakePlayer) {
        return getPosAtNode(fakePlayer, nextNodeIndex);
    }

    public Vector getNextNodePos() {
        return getNodePos(nextNodeIndex);
    }

    public Vector getNodePos(int index) {
        Node node = getNode(index);
        return new Vector(node.getX(), node.getY(), node.getZ());
    }

    public void advance() {
        nextNodeIndex++;
    }

    public boolean notStarted() {
        return nextNodeIndex <= 0;
    }

    public boolean isDone() {
        return nextNodeIndex >= nodes.size() || nodes.isEmpty();
    }

    public void replaceNode(int index, Node node) {
        Node[] array = nodes.toArray(new Node[0]);
        array[index] = node;
        nodes.clear();
        nodes.addAll(Arrays.asList(array));
    }

    public void clean() {
        if (!nodes.isEmpty()) {
            nodes.removeFirst();
        }
    }
}
