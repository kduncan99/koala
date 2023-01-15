/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old;

import java.awt.*;

public class BorderPanel extends Container {

    private final Color _backgroundColor;
    private final Color _borderColor;
    private final int _marginPixels;

    public BorderPanel(
        final int marginPixels
    ) {
        this(marginPixels, null, null);
    }

    public BorderPanel(
        final int marginPixels,
        final Color backgroundColor,
        final Color borderColor
    ) {
        _backgroundColor = backgroundColor;
        _borderColor = borderColor;
        _marginPixels = marginPixels;

        setLayout(new BorderLayout());

        var topDim = new Dimension(2 * marginPixels, marginPixels);
        var sideDim = new Dimension(marginPixels, 0);

        var top = new Panel();
        top.setMinimumSize(topDim);
        top.setPreferredSize(topDim);
        add(top, BorderLayout.NORTH);

        var bottom = new Panel();
        bottom.setMinimumSize(topDim);
        bottom.setPreferredSize(topDim);
        add(bottom, BorderLayout.SOUTH);

        var left = new Panel();
        left.setMinimumSize(sideDim);
        left.setPreferredSize(sideDim);
        add(left, BorderLayout.WEST);

        var right = new Panel();
        right.setMinimumSize(sideDim);
        right.setPreferredSize(sideDim);
        add(right, BorderLayout.EAST);
    }

    @Override
    public Component add(
        final Component component
    ) {
        add(component, BorderLayout.CENTER);
        return component;
    }

    @Override
    public void paint(
        final Graphics graphics
    ) {
        //TODO debugs
        if (_marginPixels == 3) {
            var comps = getComponents();
            System.out.println("----------------------------- size=" + getSize());
            for (var c : comps) {
                System.out.println("Comp dim=" + c.getSize() + " pref=" + c.getPreferredSize() + " loc=" + c.getLocation());
            }
        }
        //TODO end debugs

        if (_backgroundColor != null) {
            graphics.setColor(_backgroundColor);
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (_borderColor != null) {
            graphics.setColor(_borderColor);
            graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        super.paint(graphics);
    }
}
