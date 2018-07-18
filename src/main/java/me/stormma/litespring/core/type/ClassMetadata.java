package me.stormma.litespring.core.type;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/18
 */
public interface ClassMetadata {

    String getClassName();

    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();
}
