package me.serbob.asteroidapi.Scheduler;

import org.bukkit.Bukkit;

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
    private static final Map<UUID, List<Integer>> taskIdMap = new ConcurrentHashMap<>();

    /**
     * Gets the mapping of UUIDs to their associated task IDs.
     *
     * @return The map containing UUIDs and their associated task IDs.
     */
    public Map<UUID, List<Integer>> getTaskIdMap() {
        return taskIdMap;
    }

    /**
     * Adds a task ID to the list associated with a specific UUID.
     *
     * @param uuid    The UUID of the player.
     * @param taskId  The task ID to be added.
     */
    public void addTaskToUuid(UUID uuid, int taskId) {
        taskIdMap.computeIfAbsent(uuid, k -> new LinkedList<>()).add(taskId);
    }

    /**
     * Removes a task ID from the list associated with a specific UUID.
     *
     * @param uuid    The UUID of the player.
     * @param taskId  The task ID to be removed.
     */
    public void removeTaskFromUuid(UUID uuid, int taskId) {
        List<Integer> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            taskList.remove(Integer.valueOf(taskId));
        }
    }

    /**
     * Cancels all tasks associated with a specific UUID.
     *
     * @param uuid  The UUID of the player.
     */
    public void cancelAllTasks(UUID uuid) {
        List<Integer> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            for (Integer taskId : taskList) {
                Bukkit.getScheduler().cancelTask(taskId);
            }
        }
    }

    /**
     * Cancels and deletes all tasks associated with a specific UUID.
     *
     * @param uuid  The UUID of the player.
     */
    public void cancelAndDeleteAllTasks(UUID uuid) {
        List<Integer> taskList = taskIdMap.get(uuid);
        if (taskList != null && !taskList.isEmpty()) {
            for (Integer taskId : taskList) {
                Bukkit.getScheduler().cancelTask(taskId);
            }
            taskIdMap.remove(uuid);
        }
    }
}

