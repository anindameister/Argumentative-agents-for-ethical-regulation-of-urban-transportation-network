arguments=['a','b','c','d','e']
attackRelationList=[['a','b'],['c','b'],['c','d'],['d','c'],['d','e'],['e','e']]


def theSubset():
    sizeOfSubset=int(input("enter the size of subset: "))
    # subset=[]
    subsetList=[]
    # pdb.set_trace()
    for i in range(0,sizeOfSubset):
        if sizeOfSubset==0:
            subsetList.append([])
        elif sizeOfSubset>0:

            subset=input("enter the argument for the subset: ")
            subsetList.append(subset)

    print("your subset is: ",subsetList)
    # print("the dataType of your subset is: ",type(subsetList))
    return subsetList


def conflictFree(subsetList,attackRelationList):
   
    for r in attackRelationList:
        if (r[0] in subsetList) and (r[1] in subsetList):
            return False
    return True

def acceptibility(arguments,attackRelationList,subsetList):
  
       
    attackers = []
    for a in subsetList:
        for r in attackRelationList:
            if r[1] == a and not r[0] in attackers:
                attackers.append(r[0])
    for a in attackers:
        notFound = True
        for r in attackRelationList:
            if r[1] == a and r[0] in subsetList:
                notFound = False
                break
        if notFound:
            return False
    return True

def admissibility(arguments,attackRelationList,subsetList):
    return conflictFree(subsetList,attackRelationList) and acceptibility(arguments, attackRelationList, subsetList)


def completeExtension(arguments,attackRelationList,subsetList):
    
    d = dict()
    
    if not admissibility(arguments,attackRelationList,subsetList):
        return False
    for a in arguments:
        d[a] = "undecided"
    for a in subsetList:
        if d[a]!="out":
            d[a]="in"
            for r in attackRelationList:
                if r[0]==a:
                    d[r[1]]="out"
    
    for a in arguments:
        if d[a]!="out":
            d[a]="in"
            for r in attackRelationList:
                if r[0]==a:
                    d[r[1]]="out"
    print(d)
    for a in d.keys():
        if d[a] != "out" and a in subsetList:
            return True


    return False

      



def stableExtension(arguments,subsetList,attackRelationList):
    if not completeExtension(arguments,attackRelationList,subsetList):
        return False
    attacked=[]
    for a in subsetList:
        for r in attackRelationList:
            if (r[0]==a) and not (r[1] in attacked):
                attacked.append(r[1])
    return len(arguments)-len(subsetList)==len(attacked)


def preferredExtension(arguments,subsetList,attackRelationList):
    
    if not completeExtension(arguments,attackRelationList,subsetList):
        return False
    test=set(arguments).difference(set(subsetList))
    for a in test:
        temp=subsetList
        temp.append(a)
        if completeExtension(arguments,attackRelationList,temp):
            return False
    return True    



def main():
    subsetList=theSubset()
    print(conflictFree(subsetList,attackRelationList))
    print(acceptibility(arguments,attackRelationList,subsetList))
    print(admissibility(arguments,attackRelationList,subsetList))
    print(completeExtension(arguments,attackRelationList,subsetList))
    print(stableExtension(arguments,subsetList,attackRelationList))
    print(preferredExtension(arguments,subsetList,attackRelationList))
    

main()  