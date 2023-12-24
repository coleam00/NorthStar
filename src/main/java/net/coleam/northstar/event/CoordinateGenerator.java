package net.coleam.northstar.event;

import java.util.UUID;

public class CoordinateGenerator {

    public static int MAX_COORDINATE = 100000;
    public static int MIN_DISTANCE = 100;

    public static int[] generateCoordinates(UUID uuid) {
        int hash = uuid.hashCode();

        int x = hash % MAX_COORDINATE;
        int z = (hash / MAX_COORDINATE) % MAX_COORDINATE;

        // Ensure coordinates are at least MIN_DISTANCE away from the origin
        x = (x > 0) ? x + MIN_DISTANCE : x - MIN_DISTANCE;
        z = (z > 0) ? z + MIN_DISTANCE : z - MIN_DISTANCE;

        // Keep coordinates within bounds
        x = Math.min(x, MAX_COORDINATE);
        z = Math.min(z, MAX_COORDINATE);

        return new int[]{x, z};
    }
}
