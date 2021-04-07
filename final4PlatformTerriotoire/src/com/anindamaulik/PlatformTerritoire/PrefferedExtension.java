package com.anindamaulik.PlatformTerritoire;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PrefferedExtension<T> implements Extension{
    private Set<Set<Argument>> PEXT = new HashSet<Set<Argument>>();

    @Override
    public Set<Set<Argument>> computeExtensions(ArgumentationGraph arg) {

        Set<Argument> A = arg.getSetOfArguments();
        Set<Couple> R = arg.getAttacks();
        HashMap<T, String> mu = new HashMap<T, String>();

        A.forEach(x -> {
            mu.put((T) x.getA(), "BLANK");
        });

        this.find_preffered_extensions((HashMap<String, String>) mu, A, R);

        return this.PEXT;

    }

    private void find_preffered_extensions(HashMap<String, String> mu, Set<Argument> A, Set<Couple> R)
    {
        boolean if1 = PrefferedExtension.areAllDifferentFrom(A, mu, "BLANK");
        if (if1)
        {
            boolean if2 = PrefferedExtension.areAllDifferentFrom(A, mu, "MUST_OUT");
            if (if2)
            {
                Set<Argument> S = PrefferedExtension.selectIn(A, mu);
                boolean if3 = PrefferedExtension.notIncludedIn(S, this.PEXT);
                if (if3) {
                    this.PEXT.add(S);
                }
            }
        }
        else
        {
            Argument xp = this.selectAnyBlank(A, mu);
            HashMap<String, String> muP = this.inTrans(xp, mu, R);
            this.find_preffered_extensions(muP, A, R);
            muP = this.undecTrans(xp, mu);
            this.find_preffered_extensions(muP, A, R);

        }
    }




    private static boolean areAllDifferentFrom(Set<Argument> A, HashMap<String, String> mu, String s)
    {
        for (Argument x: A) if (mu.get(x.getA()) == s) return false;
        return true;
    }


    private static Set<Argument> selectIn(Set<Argument> A, HashMap<String, String> mu)
    {
        Set<Argument> S = new HashSet<Argument>();
        for (Argument x: A) if (mu.get(x.getA()) == "IN") S.add(x);
        return S;
    }

    private static boolean notIncludedIn(Set<Argument> S, Set<Set<Argument>> P) {
        for (Set<Argument> T: P) if (PrefferedExtension.isPartOf(S, T)) return false;
        return true;
    }

    private static boolean isPartOf(Set<Argument> S, Set<Argument> T) {
        for (Argument x: S) if (!(T.contains(x))) return false;
        return true;
    }

    private Argument selectAnyBlank(Set<Argument> A, HashMap<String, String> mu)
    {
        for (Argument x : A)
        {
            String value = mu.get(x.getA());
            if (value == "BLANK") return x;
        }
        return null;
    }


    private HashMap<String, String> inTrans(Argument xp, HashMap<String, String> mu, Set<Couple> R) {
        HashMap<String, String> muP = new HashMap<String, String>(mu);
        muP.put((String) xp.getA(), "IN");
        for (Couple cr : R) if (cr.getX().getA() == xp.getA()) muP.put((String) cr.getY().getA(), "OUT");
        for (Couple cr : R) if ((cr.getY().getA() == xp.getA())&&(muP.get(cr.getX().getA()) != "OUT")) muP.put((String) cr.getX().getA(), "MUST_OUT");
        return muP;
    }


    private HashMap<String, String> undecTrans(Argument xp, HashMap<String, String> mu)
    {
        HashMap<String, String> muP = new HashMap<String, String>(mu);
        muP.put((String) xp.getA(), "UNDEC");
        return muP;
    }

}
