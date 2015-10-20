/*******************************************************************************
 * Copyright (c) 2011, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.core.service;

import java.util.List;

import org.eclipse.elk.core.config.ILayoutConfig;
import org.eclipse.elk.graph.KGraphElement;
import org.eclipse.elk.graph.KNode;
import org.eclipse.elk.graph.properties.MapPropertyHolder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * A layout mapping describes the relation between a graphical diagram and the layout graph
 * that is created by diagram layout managers.
 *
 * @param <T> the type of diagram parts to store in the mapping
 * @author msp
 * @kieler.design proposed by msp
 * @kieler.rating yellow 2012-07-19 review KI-20 by cds, jjc
 */
public class LayoutMapping<T> extends MapPropertyHolder {
    
    /** the serial version UID. */
    private static final long serialVersionUID = 4066018168889912586L;
    
    /** the bidirectional mapping of layout graph elements to diagram parts. */
    private final BiMap<KGraphElement, T> graphElemMap = HashBiMap.create();
    /** additional layout configurations for specification of layout options. */
    private final List<ILayoutConfig> layoutConfigs = Lists.newLinkedList();
    /** the top-level parent node of the layout graph. */
    private KNode layoutGraph;
    /** the top-level diagram part. */
    private T parentElement;
    
    /**
     * Returns the bidirectional mapping of layout graph elements to diagram parts.
     * 
     * @return the graph element map
     */
    public BiMap<KGraphElement, T> getGraphMap() {
        return graphElemMap;
    }
    
    /**
     * Set the top-level parent node of the layout graph.
     * 
     * @param layoutGraph the layout graph
     */
    public void setLayoutGraph(final KNode layoutGraph) {
        this.layoutGraph = layoutGraph;
    }
    
    /**
     * Returns the top-level parent node of the layout graph.
     * 
     * @return the layout graph
     */
    public KNode getLayoutGraph() {
        return layoutGraph;
    }
    
    /**
     * Returns the additional layout configurations for specification of layout options. The
     * returned list is initially empty.
     * 
     * @return the layout configurations
     */
    public List<ILayoutConfig> getLayoutConfigs() {
        return layoutConfigs;
    }
    
    /**
     * Set the top-level diagram part.
     * 
     * @param parentElem the parent diagram part
     */
    public void setParentElement(final T parentElem) {
        this.parentElement = parentElem;
    }
    
    /**
     * Returns the top-level diagram part.
     * 
     * @return the parent diagram part
     */
    public T getParentElement() {
        return parentElement;
    }

}