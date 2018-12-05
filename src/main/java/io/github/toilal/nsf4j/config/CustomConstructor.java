package io.github.toilal.nsf4j.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomConstructor extends Constructor {
    public CustomConstructor() {
        this.yamlConstructors.put(new Tag(Path.class), new PathConstruct());
        this.addTypeDescription(new TypeDescription(PathConstruct.class, new Tag(Path.class)));
    }

    @Override
    protected Object newInstance(Class<?> ancestor, Node node, boolean tryDefault) throws InstantiationException {
        if (Path.class.isAssignableFrom(ancestor)) {
            return this.yamlConstructors.get(new Tag(Path.class)).construct(node);
        }
        return super.newInstance(ancestor, node, tryDefault);
    }

    private class PathConstruct extends AbstractConstruct {
        @Override
        public Object construct(Node node) {
            String pathStr = constructScalar((ScalarNode) node);
            return Paths.get(pathStr);
        }
    }
}
