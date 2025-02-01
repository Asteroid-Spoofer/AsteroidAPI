package me.serbob.asteroidapi.actions.example;

import lombok.Getter;
import lombok.Setter;
import me.serbob.asteroidapi.actions.abstracts.Action;
import me.serbob.asteroidapi.actions.enums.ActionType;
import me.serbob.asteroidapi.actions.enums.Priority;
import me.serbob.asteroidapi.actions.util.Result;
import me.serbob.asteroidapi.enums.Pose;
import me.serbob.asteroidapi.looking.Target;
import me.serbob.asteroidapi.pathfinding.Path;
import me.serbob.asteroidapi.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

/*
 * This is usually the action I'm using for all of my other systems like
 * combat, following player, etc.
 */

@Getter
@Setter
public class PathingAction extends Action {
    private final Location destination;
    private final Location groundDestination;
    private final MovementType movementType;
    private Path path;

    private double arrivalDistance = 0.5;
    private boolean shouldLookAtDestination = true;
    private Priority lookingPriority = Priority.NORMAL;

    // Stuck detection
    private Location previousPosition;
    private Location startPosition;
    private int stuckTime;
    private int maxStuckTime = 20;
    private double stuckThreshold = 0.01;
    private StuckAction stuckAction = StuckAction.STOP;

    private int ticksSinceStart;

    public PathingAction(Location destination, MovementType movementType) {
        super(ActionType.LOCATION);
        this.destination = destination;
        this.path = null;
        this.groundDestination = LocationUtils.findSolidBlock(destination, BlockFace.DOWN)
                .getRelative(BlockFace.UP).getLocation();
        this.movementType = movementType;
    }

    public PathingAction(Location destination, Path path, MovementType movementType) {
        super(ActionType.LOCATION);
        this.destination = destination;
        this.path = path;
        this.groundDestination = LocationUtils.findSolidBlock(destination, BlockFace.DOWN)
                .getRelative(BlockFace.UP).getLocation();
        this.movementType = movementType;
    }

    public PathingAction setArrivalDistance(double distance) {
        this.arrivalDistance = distance;
        return this;
    }

    public PathingAction setLookAtDestination(boolean shouldLook, Priority priority) {
        this.shouldLookAtDestination = shouldLook;
        this.lookingPriority = priority;
        return this;
    }

    public PathingAction setStuckDetection(int maxTime, double threshold, StuckAction action) {
        this.maxStuckTime = maxTime;
        this.stuckThreshold = threshold;
        this.stuckAction = action;
        return this;
    }

    @Override
    public void onStart(StartType startType) {
        startPosition = getFakePlayer().getEntityPlayer().getLocation().clone();
        initializeMovement();
        updateDestination();
    }

    private void initializeMovement() {
        getFakePlayer().getOverrides().setJumping(movementType.name().contains("JUMP"));

        switch (movementType) {
            case WALK:
            case SPRINT:
            case SPRINT_JUMP:
                getFakePlayer().getOverrides().setPose(Pose.STANDING);
                break;
            case SNEAK:
                getFakePlayer().getOverrides().setPose(Pose.SNEAKING);
                break;
            case FLY:
                getFakePlayer().getOverrides().setPose(Pose.FLYING);
                break;
        }
    }

    @Override
    public void onUpdate() {
        updateSprinting();

        if (++ticksSinceStart % 10 == 0) {
            updateDestination();
        }

        checkIfStuck();
        checkIfArrived();
    }

    private void updateSprinting() {
        if (!getFakePlayer().getEntityPlayer().isSprinting()) {
            getFakePlayer().getEntityPlayer().setSprinting(movementType.name().contains("SPRINT"));
        }
    }

    private void updateDestination() {
        path = getFakePlayer().getNavigation().moveTo(destination, path);

        if (shouldLookAtDestination) {
            Location lookPosition = groundDestination.clone();
            getFakePlayer().getLookController().addTarget("movement_target",
                    new Target(lookPosition, lookingPriority));
        }
    }

    private void checkIfStuck() {
        if (previousPosition != null) {
            Location currentPosition = getFakePlayer().getEntityPlayer().getLocation().clone();
            currentPosition.setY(previousPosition.getY());

            if (previousPosition.distanceSquared(currentPosition) < stuckThreshold) {
                if (++stuckTime >= maxStuckTime) {
                    handleStuckState();
                }
            } else {
                stuckTime = 0;
            }
        }

        previousPosition = getFakePlayer().getEntityPlayer().getLocation().clone();
    }

    private void handleStuckState() {
        stuckTime = 0;

        switch (stuckAction) {
            case STOP:
                getFakePlayer().getNavigation().stop();
                markAsFinished(new Result<>(Result.Type.FAILURE));
                break;
            case RESTART:
                getFakePlayer().getNavigation().stop();
                getFakePlayer().getNavigation().moveTo(destination);
                break;
            case REVERSE:
                getFakePlayer().getNavigation().stop();
                getFakePlayer().getNavigation().moveTo(startPosition);
                break;
            case TELEPORT:
                getFakePlayer().getNavigation().stop();
                getFakePlayer().getEntityPlayer().teleport(destination);
                break;
            case IGNORE:
                break;
        }
    }

    private boolean checkIfArrived() {
        if (arrivalDistance <= 0F) {
            if (path != null && path.isDone()) {
                markAsFinished(new Result<>(Result.Type.SUCCESS));
                return true;
            }
        } else if (groundDestination.distanceSquared(getFakePlayer().getEntityPlayer().getLocation()) <= arrivalDistance) {
            markAsFinished(new Result<>(Result.Type.SUCCESS));
            return true;
        }
        return false;
    }

    @Override
    public void onStop(StopType stopType) {
        getFakePlayer().getNavigation().stop();
        getFakePlayer().getMovement().stopMovement();
        if (shouldLookAtDestination) {
            getFakePlayer().getLookController().removeTarget("movement_target");
        }
    }

    @Override
    public boolean canStart(StartType startType) {
        return true;
    }

    @Override
    public boolean canStop(StopType stopType) {
        return true;
    }

    public enum StuckAction {
        STOP,
        RESTART,
        REVERSE,
        TELEPORT,
        IGNORE
    }

    public enum MovementType {
        WALK,
        SNEAK,
        SPRINT,
        SPRINT_JUMP,
        FLY
    }
}
