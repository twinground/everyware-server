package com.everyware;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class WorldRepository {
    private final ConcurrentHashMap<String, World> worldMap;
    @Getter
    private final Collection<World> worlds;

    public WorldRepository() {
        worldMap = new ConcurrentHashMap<>(Stream.of(World.create("capstone"), World.create("ang"), World.create("kya"))
                .collect(Collectors.toMap(World::getName, Function.identity())));
        worlds = Collections.unmodifiableCollection(worldMap.values());
    }

    public World getWorld(String name){
        return worldMap.get(name);
    }
    public void remove(WebSocketSession session) {
        this.worlds.parallelStream().forEach(world -> world.remove(session));
    }
}
