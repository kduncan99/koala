/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.elements.ports.DestinationPort;
import com.bearsnake.koala.modules.elements.ports.SourcePort;
import com.bearsnake.koala.modules.elements.ports.ActivePort;
import javafx.scene.Group;

public class Connection {

    private final Group _container;
    private final DestinationPort _destination;
    private boolean _isConnected;
    private final SourcePort _source;
    private Wire _wire = null;

    public Connection(
        final Rack rack,
        final SourcePort source,
        final DestinationPort destination
    ) {
        _container = rack.getRootGroup();
        _source = source;
        _destination = destination;
    }

    public boolean connect() {
        if (isConnected()) {
            return false;
        }

        if (!ActivePort.connect(_source, _destination)) {
            return false;
        }

        var r1 = _container.sceneToLocal(_source.getJackCenterSceneCoordinates());
        var r2 = _container.sceneToLocal(_destination.getJackCenterSceneCoordinates());
        _wire = new Wire(r1, r2, _source.getWireColor());
        _container.getChildren().add(_wire);
        _wire.toFront();
        _isConnected = true;

        return true;
    }

    public boolean disconnect() {
        if (!isConnected()) {
            return false;
        }

        ActivePort.disconnect(_source, _destination);
        _container.getChildren().remove(_wire);
        _wire = null;
        _isConnected = false;
        return true;
    }

    public DestinationPort getDestination() { return _destination; }
    public SourcePort getSource() { return _source; }
    public boolean isConnected() { return _isConnected; }
}
