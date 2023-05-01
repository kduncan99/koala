/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.elements.ports.InputPort;
import com.bearsnake.koala.modules.elements.ports.OutputPort;
import com.bearsnake.koala.modules.elements.ports.Port;
import javafx.scene.Group;

public class Connection {

    private final Group _container;
    private final InputPort _destination;
    private boolean _isConnected;
    private final OutputPort _source;
    private Wire _wire = null;

    public Connection(
        final Rack rack,
        final OutputPort source,
        final InputPort destination
    ) {
        _container = rack.getRootGroup();
        _source = source;
        _destination = destination;
    }

    public boolean connect() {
        if (isConnected()) {
            return false;
        }

        if (!Port.connect(_source, _destination)) {
            return false;
        }

        var r1 = _container.sceneToLocal(_source.getJackCenterSceneCoordinates());
        var r2 = _container.sceneToLocal(_destination.getJackCenterSceneCoordinates());
        _wire = new Wire(r1, r2, _source.getWireColor());
        _container.getChildren().add(_wire);
        _wire.toFront();

        return true;
    }

    public boolean disconnect() {
        if (!isConnected()) {
            return false;
        }

        _container.getChildren().remove(_wire);
        _wire = null;
        _isConnected = false;
        return true;
    }

    public boolean isConnected() { return _isConnected; }
}
