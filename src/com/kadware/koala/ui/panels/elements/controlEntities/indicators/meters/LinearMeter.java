/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.Koala;
import com.kadware.koala.Range;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * This is a vertically-oriented linear meter.
 * The layout is as such:
 * -----
 * | | |
 * |a|b|
 * | | |
 * -----
 * where a is a gradient text display with gradient markers at periodic intervals
 *   and b is a rectangle
 *
 * Since Indicator is a Pane, we have to set up an HBox inside ourselves to accomplish this.
 */
public class LinearMeter extends Meter {

    public enum Mode {
        BAR,
        DOT,
        LINE,
    }

    private final Color _fillColor;
    private final Color _bgColor;
    private final Mode _mode;
    private final Node _node;

    public LinearMeter(
        final CellDimensions cellDimensions,
        final String legend,
        final Color color,
        final Mode mode,
        final Range<Double> range,
        final GradientInfo info
    ) {
        super(cellDimensions, legend, Orientation.HORIZONTAL, range, info);

        _fillColor = color.brighter();
        _bgColor = color.darker().darker();
        _mode = mode;

        var insetPixels = 5;
        var node = (Node) null;
        switch (mode) {
            case BAR -> {
                var height = getLayoutPane().getGraphPane().getPixelDimensions().getHeight();
                var rect = new Rectangle(0, height - 2 * insetPixels);
                rect.setLayoutY(insetPixels);
                rect.setFill(_fillColor);
                node = rect;
            }
            case DOT -> {
                var h = getLayoutPane().getGraphPane().getPixelDimensions().getHeight();
                var radius = h * 0.2;
                var ypos = h * 0.5;
                var circle = new Circle(radius);
                circle.setFill(_fillColor);
                circle.setLayoutY(ypos);
                node = circle;
            }
            case LINE -> {
                var height = getLayoutPane().getGraphPane().getPixelDimensions().getHeight();
                var rect = new Rectangle(3, height - 2 * insetPixels);
                rect.setLayoutX(0);
                rect.setLayoutY(insetPixels);
                rect.setFill(_fillColor);
                node = rect;
            }
        }
        _node = node;
        var bf = new BackgroundFill(_bgColor, CornerRadii.EMPTY, new Insets(insetPixels, 0, insetPixels, 0));
        var b = new Background(bf);
        getLayoutPane().getGraphPane().setBackground(b);
        getLayoutPane().getGraphPane().getChildren().add(node);
    }

    @Override
    public void repaint() {
        var gp = getLayoutPane().getGraphPane();
        var x = gp.findHorizontalGraphCoordinate(_value);
        switch (_mode) {
            case BAR -> {
                var rect = (Rectangle) _node;
                rect.setWidth(x);
            }
            case DOT -> {
                var circle = (Circle) _node;
                var leftCenterLimit = circle.getRadius();
                var rightCenterLimit = gp.getWidth() - circle.getRadius();
                circle.setLayoutX(Koala.getBounded(leftCenterLimit, x, rightCenterLimit));
            }
            case LINE -> {
                var line = (Rectangle) _node;
                var leftCenterLimit = line.getWidth() / 2;
                var rightCenterLimit = gp.getWidth() - line.getWidth() / 2;
                line.setLayoutX(Koala.getBounded(leftCenterLimit, x, rightCenterLimit));
            }
        }
    }
}
