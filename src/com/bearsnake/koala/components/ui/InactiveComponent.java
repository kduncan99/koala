/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

/**
 * An InactiveComponent is an indicator which simply displays static information,
 * updated only by external means.
 * A Control cannot be an InactiveComponent.
 * <p>
 * Currently there is nothing an InactiveComponent does apart from the base class;
 * however, all components which do nothing should extend this class anyway, in case this changes in the future.
 */
public abstract class InactiveComponent extends Component {}
