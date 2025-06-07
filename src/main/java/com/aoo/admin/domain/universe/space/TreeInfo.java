package com.aoo.admin.domain.universe.space;

import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.element.Element;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Getter
public class TreeInfo {
    private final Integer depth;
    private final TreeInfo parent;
    private final List<TreeInfo> children;
    private final UniverseTreeComponent universeTreeComponent;

    private TreeInfo(Integer depth, TreeInfo parent, List<TreeInfo> children, UniverseTreeComponent universeTreeComponent) {
        this.depth = depth;
        this.parent = parent;
        this.children = children;
        this.universeTreeComponent = universeTreeComponent;
    }

    public static TreeInfo create(TraversalComponents components) {
        return create(components.getUniverse(),components.getSpaces(),components.getElements());
    }

    public static TreeInfo create(Universe universe, List<Space> spaces, List<Element> elements) {
        TreeInfo root = new TreeInfo(0, null, new ArrayList<>(), universe);

        List<Space> childSpaces = new ArrayList<>();
        List<Element> childElements = new ArrayList<>();

        for (Space space : spaces) {
            if (space.isRoot()) root.addChild(space);
            else childSpaces.add(space);
        }

        for (Element element : elements) {
            if (element.isRoot()) root.addChild(element);
            else childElements.add(element);
        }

        for (TreeInfo child : root.children) {
            makeChildTree(child, childSpaces, childElements);
        }

        return root;
    }

    private static void makeChildTree(TreeInfo tree, List<Space> spaces, List<Element> elements) {
        if (tree.getUniverseTreeComponent() instanceof Element) return;

        List<Space> childSpaces = new ArrayList<>();
        List<Element> childElements = new ArrayList<>();

        for (Space space : spaces) {
            if (space.getBasicInfo().getParentSpaceId().equals(tree.getUniverseTreeComponent().getId())) tree.addChild(space);
            else childSpaces.add(space);
        }

        for (Element element : elements) {
            if (element.getBasicInfo().getParentSpaceId().equals(tree.getUniverseTreeComponent().getId())) tree.addChild(element);
            else childElements.add(element);
        }

        for (TreeInfo child : tree.children) {
            makeChildTree(child,childSpaces,childElements);
        }
    }

    public void addChild(UniverseTreeComponent universeTreeComponent) {
        this.children.add(createChild(this, universeTreeComponent));
    }

    private TreeInfo createChild(TreeInfo parent, UniverseTreeComponent universeTreeComponent) {
        TreeInfo treeInfo = new TreeInfo(parent.getDepth() + 1, parent, new ArrayList<>(), universeTreeComponent);
        universeTreeComponent.setTreeInfo(treeInfo);
        return treeInfo;
    }

    public UniverseTreeComponent getComponent(Class<? extends UniverseTreeComponent> clazz, Long id) {
        Deque<TreeInfo> queue = new ArrayDeque<>();
        queue.offer(this);
        TreeInfo treeInfo = null;

        while(!queue.isEmpty()) {
            treeInfo = queue.poll();
            UniverseTreeComponent component = treeInfo.getUniverseTreeComponent();

            if (clazz.isInstance(component) && treeInfo.getUniverseTreeComponent().getId().equals(id))
                return component;

            for (TreeInfo child : treeInfo.children) {
                queue.offer(child);
            }
        }

        return null;
    }
}
