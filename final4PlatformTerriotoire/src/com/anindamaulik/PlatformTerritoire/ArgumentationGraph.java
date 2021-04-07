package com.anindamaulik.PlatformTerritoire;

import java.util.Set;

public class ArgumentationGraph {
    private Set<Argument> setOfArguments;

    private Set<Couple> Attacks;

    public ArgumentationGraph(Set<Argument> setOfArguments, Set<Couple> Attacks)
    {
        this.setOfArguments = setOfArguments;
        this.Attacks = Attacks;
    }

    public Set<Argument> getSetOfArguments()
    {

        return this.setOfArguments;
    }

    public void setSetOfArguments( Set<Argument> setOfArguments ) {

        this.setOfArguments = setOfArguments;
    }

    public Set<Couple> getAttacks()
    {

        return this.Attacks;
    }

    public void setAttacks(Set<Couple> Attacks)
    {

        this.Attacks = Attacks;
    }

    public Set<Set<Argument>> computeExtensions(Extension ex)
    {

        return ex.computeExtensions(this) ;
    };




}



