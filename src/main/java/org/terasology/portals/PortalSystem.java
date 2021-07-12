// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.portals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.entitySystem.entity.EntityManager;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterMode;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.logic.common.ActivateEvent;
import org.terasology.engine.registry.In;
import org.terasology.engine.world.block.BlockComponent;
import org.terasology.spawning.SpawnerComponent;

/**
 * System that handles interactions with portals
 * TODO: Add a game hook for world creation that at the end adds a portal by the player start location (only runs once per world)
 *
 * @author Rasmus 'Cervator' Praestholm <cervator@gmail.com>
 */
@RegisterSystem(RegisterMode.AUTHORITY)
public class PortalSystem extends BaseComponentSystem {

    private static final Logger logger = LoggerFactory.getLogger(PortalSystem.class);

    @In
    private EntityManager entityManager;

    @Override
    public void initialise() {
    }

    @Override
    public void shutdown() {
    }

    @ReceiveEvent(components = {PortalComponent.class})
    public void onActivate(ActivateEvent event, EntityRef entity) {
        logger.info("Activating PortalSystem!");
        // Not sure if this will be needed, but handy to remember how it is done
        PortalComponent portal = entity.getComponent(PortalComponent.class);
        BlockComponent block = entity.getComponent(BlockComponent.class);

        // Activating a portal simply toggles spawning - which is as simple as attaching or detaching that component
        if (entity.hasComponent(SpawnerComponent.class)) {
            logger.info("Found a portal with a spawner, so removing it");
            entity.removeComponent(SpawnerComponent.class);
        } else {
            logger.info("Found a portal withOUT a spawner, so adding one");
            SpawnerComponent spawner = new SpawnerComponent();
            entity.addComponent(spawner);
            entity.saveComponent(spawner);
        }
    }
}


