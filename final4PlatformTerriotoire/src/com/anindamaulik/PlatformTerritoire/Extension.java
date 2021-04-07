package com.anindamaulik.PlatformTerritoire;

import java.util.Set;

public interface Extension {
    public  Set<Set<Argument>> computeExtensions(ArgumentationGraph arg);
}
