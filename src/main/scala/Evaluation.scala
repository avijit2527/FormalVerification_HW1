
object Evaluation {

    def getAllVariables(e: Expr) : Set[Variable] = {
        e match {
            case Variable(name) => Set(Variable(name))
            case BoolLit(value) => Set.empty
            case And(args) =>  var retval: Set[Variable] =  Set.empty ; for(a <- args) {retval = getAllVariables(a) ++ retval}; retval
            case Or(args) => var retval: Set[Variable] = Set.empty ; for(a <- args) {retval = getAllVariables(a) ++ retval}; retval
            case Not(v) => getAllVariables(v)
        }
    }

    def evaluate(e: Expr, m: Map[Variable, Boolean]) : Option[Boolean] = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function should return the boolean value      //
        // the expression e evaluates given the input variable       //
        // assignments in the map m,i.e it should return Some(value) //
        // If any of the variables does not have an assignment in    //
        // the map m, this function should return None.              //
        ///////////////////////////////////////////////////////////////
        
        var allVariables = getAllVariables(e)
        
        if (m.isEmpty) return None
        for (key <- allVariables)
        {
            if (m.get(key) == None) { return None } 
        }
        
        var temp : Boolean = e match {
            case Variable(name) => m.get(Variable(name)).get
            case BoolLit(value) => value
            case And(args) => var retval: Boolean =  evaluate(args(0), m).get ; for(a <- args) {retval = evaluate(a, m).get && retval}; retval
            case Or(args) =>  var retval: Boolean =  evaluate(args(0), m).get ; for(a <- args) {retval = evaluate(a, m).get || retval}; retval
            case Not(args) => !evaluate(args, m).get
        }
        
        return Some(temp)
    }

    def simplify(e: Expr) : Expr = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function should return the simplified         //
        // expression e reduces to given the simplification rules    //
        // mentioned in the assignment document                      //
        ///////////////////////////////////////////////////////////////

        e match {        
            case Variable(name) => Variable(name)
            case BoolLit(value) => BoolLit(value)
            case And(args) => {
                if (args.contains(BoolLit(false))){println(args);println(e);println(); return BoolLit(false)}
                var unique_args = args
                for (a <- args){
                    if (args.contains(Not(a))) {return BoolLit(false)}
                }
                if (args.contains(BoolLit(true))){unique_args = args.filter(_ != BoolLit(true))}
                unique_args = unique_args.distinct
                //println(unique_args)
                var argumentList : List[Expr] = List.empty[Expr]
                for (a <- unique_args){
                    argumentList = argumentList :+ simplify(a)
                } 

                
                return And(argumentList)                               
            }
            case Or(args) =>  {
                if (args.contains(BoolLit(true))){return BoolLit(true)}
                var unique_args = args
                for (a <- args){
                    if (args.contains(Not(a))) {return BoolLit(true)}
                }
                if (args.contains(BoolLit(false))){unique_args = args.filter(_ != BoolLit(false))}
                unique_args = unique_args.distinct
                //println(unique_args)
                var argumentList : List[Expr] = List.empty[Expr]
                for (a <- unique_args){
                    argumentList = argumentList :+ simplify(a)
                } 

                
                return Or(argumentList)                               
            }
            
            case Not(args) => {
                args match {
                    case Not(s) => s
                    case _ => simplify(args)
                }
            }
        }
        
            
    }
    
    def getTruthValue(e : Expr, allVars : Set[Variable], m: Map[Variable, Boolean]) : List[Boolean] = {       
        var ansList : List[Boolean] = List.empty[Boolean]
        if (!allVars.isEmpty){
            val variable = allVars.head 
            ansList = ansList ++ getTruthValue(e, allVars.tail, m + (variable -> true))
            ansList = ansList ++ getTruthValue(e, allVars.tail, m + (variable -> false))       
        }else{
            val ans = evaluate(e, m).get
            ansList = ansList :+ ans
            
            }
        return ansList
    }
    
 


    def areEquivalent(e1: Expr, e2: Expr) : Boolean = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function check if the two expressions are     //
        // equivalent by enumerating their truth tables. You can     //
        // assume that the number of variables and their names will  //
        // be same in both expressions. This function returns true   //
        // if they are equivalent, false if they aren't.             //
        ///////////////////////////////////////////////////////////////
        

        val e1Var = getAllVariables(e1)
        val e2Var = getAllVariables(e2)
        
        if(e1Var != e2Var) return false
        
        val ret1 = getTruthValue(e1, e1Var, Map.empty[Variable, Boolean])
        val ret2 = getTruthValue(e2, e1Var, Map.empty[Variable, Boolean])
         
       
        return ret1 equals ret2
    }
}
