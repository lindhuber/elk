/*******************************************************************************
 * Copyright (c) 2020 Kiel University and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.elk.alg.layered.intermediate.preserveorder;

import java.util.Comparator;

import org.eclipse.elk.alg.layered.graph.LNode;
import org.eclipse.elk.alg.layered.graph.LPort;
import org.eclipse.elk.alg.layered.graph.Layer;
import org.eclipse.elk.alg.layered.options.InternalProperties;

/**
 * Orders {@link LPort}s in the same layer by the of their edges {@link InternalProperties#MODEL_ORDER}
 * or the order of the nodes they connect to in the previous layer.
 * Outgoing ports are ordered before incoming ports.
 * Incoming ports choose a position that does not create conflicts with the previous layer.
 */
public class ModelOrderPortComparator implements Comparator<LPort> {

    /**
     * The previous layer.
     */
    private final Layer previousLayer;

    /**
     * Creates a comparator to compare {@link LPort}s in the same layer.
     * 
     * @param previousLayer The previous layer
     */
    public ModelOrderPortComparator(final Layer previousLayer) {
        this.previousLayer = previousLayer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final LPort p1, final LPort p2) {
        // Sort incoming edges by sorting their ports by the order of the nodes they connect to.
        if (p1.getOutgoingEdges().isEmpty() && p2.getOutgoingEdges().isEmpty()) {
            LNode p1Node = p1.getIncomingEdges().get(0).getSource().getNode();
            LNode p2Node = p2.getIncomingEdges().get(0).getSource().getNode();
            if (p1Node.equals(p2Node)) {
                // In this case both incoming edges must have a model order set. Check it.
                return Integer.compare(p1.getIncomingEdges().get(0).getProperty(InternalProperties.MODEL_ORDER),
                        p2.getIncomingEdges().get(0).getProperty(InternalProperties.MODEL_ORDER));
            }
            for (LNode previousNode : previousLayer) {
                if (previousNode.equals(p1Node)) {
                    return 1;
                } else if (previousNode.equals(p2Node)) {
                    return -1;
                }
            }
        }

        // Sort outgoing edges by sorting their ports based on the model order of their edges.
        if (p1.getIncomingEdges().isEmpty() && p2.getIncomingEdges().isEmpty()) {
            return Integer.compare(p1.getOutgoingEdges().get(0).getProperty(InternalProperties.MODEL_ORDER),
                    p2.getOutgoingEdges().get(0).getProperty(InternalProperties.MODEL_ORDER));
        }
        // Sort outgoing ports before incoming ports.
        if (p1.getOutgoingEdges().isEmpty() && p2.getIncomingEdges().isEmpty()) {
            return 1;
        } else {
            return -1;
        }
    }
}
