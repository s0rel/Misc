package org.example.abstractfactory.abstractfactoryv1.factory;

public abstract class Factory {
    public static Factory getFactory(Class clazz) {
        Factory factory = null;
        try {
            factory = (Factory) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    public abstract Link createLink(String caption, String url);

    public abstract Tray createTray(String caption);

    public abstract Page createPage(String caption, String url);
}
