package me.serbob.asteroidapi.scheduler;

import me.serbob.asteroidapi.task.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a scheduling utility for managing tasks associated with player UUIDs.
 */
public class Schedule {
    /**
     * Singleton instance of the Schedule class.
     */
    public static final Schedule INSTANCE = new Schedule();

    /**
     * Public constructor to enforce singleton pattern.
     */
    public Schedule() {}

    /**
     * Map to store UUIDs and their associated task IDs.
     */
    private static final Map<UUID, List<Task>> taskIdMap = new ConcurrentHashMap<>();

    /**
     * Gets the mapping of UUIDs to their associated task IDs.
     *
     * @return The map containing UUIDs and their associated task IDs.
     */
    public Map<UUID, List<Task>> getTaskIdMap() {
        return taskIdMap;
    }

    /**
     * Adds a task ID to the list associated with a specific UUID.
     *
     * @param uuid    The UUID of the player.
     * @param task  The task ID to be added.
     */
    public void addTaskToUuid(UUID uuid, Task task) {
        taskIdMap.computeIfAbsent(uuid, k -> new LinkedList<>()).add(task);
    }

    /**
     * Removes a task ID from the list associated with a specific UUID.
     *
     * @param uuid    The UUID of the player.
     * @param task  The task ID to be removed.
     */
    public void removeTaskFromUuid(UUID uuid, Task task) {
        List<Task> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            taskList.remove(task);
        }
    }

    /**
     * Cancels all tasks associated with a specific UUID.
     *
     * @param uuid  The UUID of the player.
     */
    public void cancelAllTasks(UUID uuid) {
        List<Task> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
                task.cancel();
            }
        }
    }

    /**
     * Cancels and deletes all tasks associated with a specific UUID.
     *
     * @param uuid  The UUID of the player.
     */
    public void cancelAndDeleteAllTasks(UUID uuid) {
        List<Task> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
                task.cancel();
            }
            taskIdMap.remove(uuid);
        }
    }
}

