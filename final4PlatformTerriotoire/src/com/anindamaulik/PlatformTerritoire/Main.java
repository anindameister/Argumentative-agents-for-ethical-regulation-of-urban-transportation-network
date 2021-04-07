package com.anindamaulik.PlatformTerritoire;

import java.util.*;

public class Main<T> {
    public static void main(String[] args) {
        Main abstractArgumentationFramework=new Main();
        abstractArgumentationFramework.mainly();



    }

    public void mainly() {

        HashMap<T,Argument>mA=inputArguments();

        ArrayList<Couple> kcatta=inputAttacks(mA);

        Argument[] lS=inputSubset(mA);

        System.out.println(conflictFree(lS,kcatta));
        System.out.println(acceptablity(lS,kcatta)); //Argument[] sL,ArrayList<Couple> attack,HashMap<String,Argument>aM

        System.out.println(admissibility(lS,kcatta));
        System.out.println(completeExtension(lS,kcatta,mA));
        System.out.println(stableExtension(lS,kcatta,mA));
        System.out.println(preferredExtension(lS,kcatta,mA));



        Set<Couple> hSet = new HashSet<Couple>();
        for (Couple x : kcatta)
            hSet.add(x);
        ArrayList<Argument>argumentArrayList=new ArrayList<>(mA.values());
        Set<Argument> hSet1 = new HashSet<Argument>();
        for (Argument y : argumentArrayList)
            hSet1.add(y);
        ArgumentationGraph argG = new ArgumentationGraph(hSet1, hSet);

        PrefferedExtension ext = new PrefferedExtension();

        Set<Set<Argument>> SE = argG.computeExtensions(ext);

        for (Set<Argument> m : SE) {
            System.out.println("Preffered extension composed by arguments : ");
            for (Argument arg : m) {
                System.out.println(arg.getA());
            }
        }
        System.out.println(SE);



    }

    public  HashMap<T,Argument> inputArguments() {


        Scanner myobj=new Scanner(System.in);
        System.out.println("enter the number of arguments");
        int numberOfArguments=myobj.nextInt();

        Argument[]argumentList=new Argument[numberOfArguments];
        HashMap<T,Argument>argumentMap=new HashMap<>();

        for(int i=0;i<numberOfArguments;i++) {
            Scanner obj2=new Scanner(System.in);
            System.out.println("enter your arguments one by one");
            String argumentName=obj2.next();
            T targumentName= (T) argumentName;
            argumentList[i]=new Argument(argumentName);
            argumentMap.put(targumentName, new Argument(argumentName));

        }return argumentMap;

    }

    public  ArrayList<Couple> inputAttacks(HashMap<T, Argument> aM) {



        Scanner myobj2=new Scanner(System.in);
        System.out.println("enter the number of attack relations");

        int numberOfattacks=myobj2.nextInt();

        ArrayList<Couple>attackRelationList=new ArrayList<>();

        for (int i=0;i<numberOfattacks;i++) {
            Scanner myobj3=new Scanner(System.in);
            System.out.println("enter the attacks: ");
            String input=myobj3.next();
            String[]attacks=input.split(",");


            Couple attackings=new Couple(aM.get(attacks[0]),aM.get(attacks[1]));
            attackRelationList.add(attackings);

        }return attackRelationList;


    }

    public  Argument[] inputSubset(HashMap<T, Argument> aM) {



        Scanner sizeSubset=new Scanner(System.in);
        System.out.println("enter the size of the subset: ");
        int sizeOfSubset=sizeSubset.nextInt();

        Argument[]subsetList=new Argument[sizeOfSubset];

        for(int i=0;i<sizeOfSubset;i++) {
            Scanner subsetting=new Scanner(System.in);
            System.out.println("enter the argument of the subset: ");
            String subsettling=subsetting.next();



            subsetList[i]=aM.get(subsettling);



        }return subsetList;
    }


    public static boolean conflictFree(Argument[] sL, ArrayList<Couple> attack) {
        for (Couple c:attack){
            boolean contains1=Arrays.stream(sL).anyMatch(c.getX()::equals);
            boolean contains2= Arrays.stream(sL).anyMatch(c.getY()::equals);
            if ((contains1==true) && (contains2==true)){
                return false;
            }
        }
        return true;
    }

    public static Boolean acceptablity(Argument[] sL,ArrayList<Couple> attack){

        ArrayList<Argument>attackers=new ArrayList<>();
        for(Argument a:sL){
            for(Couple c:attack){
                if((c.getY()==a)&&(attackers.contains(c.getX())==false)){
                    attackers.add(c.getX());
                }
            }
        }
        for(Argument b:attackers){
            boolean notFound=true;
            for(Couple d:attack){
                if((d.getY()==b)&&(Arrays.stream(sL).anyMatch(d.getX()::equals))==true){
                    notFound=false;
                    break;
                }
            }if (notFound){
                return false;
            }
        }return true;

    }
    public static boolean admissibility(Argument[] sL,ArrayList<Couple> attack){
        return (conflictFree(sL,attack))&&(acceptablity(sL,attack));
    }

    public boolean completeExtension(Argument[] sL,ArrayList<Couple> attack,HashMap<T,Argument>aM){
        ArrayList<Argument> slList=new ArrayList<Argument>(Arrays.asList(sL));
        HashMap<Argument,T>d=new HashMap<>();

        ArrayList<Argument>argumentArrayList=new ArrayList<>(aM.values());

        if (admissibility(sL,attack)==false){
            return false;
        }for (Argument a:argumentArrayList){
            d.put(a,(T) "undecided");

        }for (Argument a:sL){
            if (d.get(a)!="out"){
                d.replace(a,(T) "in");
                for (Couple r:attack){
                    if (r.getX()==a){
                        d.replace(r.getY(),(T) "out");
                    }
                }


            }
        }for (Argument a:argumentArrayList){
            if(d.get(a)!="out"){
                d.replace(a,(T) "in");
                for (Couple r:attack){
                    if(r.getX()==a){
                        d.replace(r.getY(), (T) "out");


                    }
                }
            }
        }
        System.out.println(d);
//        for (Map.Entry<Argument,String>entry:d.entrySet()){
        for (Argument a:d.keySet()){
            if ((d.get(a)!="out") && ((slList.contains(a)))){
                return true;
            }
        }return false;
    }
    public boolean stableExtension(Argument[] sL,ArrayList<Couple> attack,HashMap<T,Argument>aM){
        ArrayList<Argument>argumentArrayList=new ArrayList<>(aM.values());
        if (completeExtension(sL,attack,aM)==false){
            return false;

        }ArrayList<Argument>attacked=new ArrayList<>();
        for(Argument a:sL){
            for(Couple r:attack){
                if((r.getX()==a) && (!(attacked.contains(r.getY())))){
                    attacked.add(r.getY());


                }
            }
        }
        return argumentArrayList.size()-sL.length==attacked.size();
    }

    public boolean preferredExtension(Argument[] sL,ArrayList<Couple> attack,HashMap<T,Argument>aM){
        ArrayList<Argument>argumentArrayList=new ArrayList<>(aM.values());
        if (completeExtension(sL,attack,aM)==false){
            return false;

        }

        ArrayList<Argument> test = new ArrayList<>();
        test=argumentArrayList;
        for(int i=0;i<test.size();i++) {
            if(test.get(i).equals(sL)==true) {
                test.remove(i);
            }

        }
//        Argument[] temp =sL;
        ArrayList<Argument> temporary = new ArrayList<>(List.of(sL));


        for (Argument x:test) {

            temporary.add(x);
            if (completeExtension(sL,attack,aM)==false){
                return false;

            }


        }return true;




    }
}
