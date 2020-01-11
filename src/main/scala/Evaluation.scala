
object Evaluation {

    def evaluate(e: Expr, m: Map[Variable, Boolean]) : Option[Boolean] = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function should return the boolean value      //
        // the expression e evaluates given the input variable       //
        // assignments in the map m,i.e it should return Some(value) //
        // If any of the variables does not have an assignment in    //
        // the map m, this function should return None.              //
        ///////////////////////////////////////////////////////////////

        //Remove this exception once you implement this 
        throw new java.lang.UnsupportedOperationException("evaluate not implemented")
    }

    def simplify(e: Expr) : Expr = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function should return the simplified         //
        // expression e reduces to given the simplification rules    //
        // mentioned in the assignment document                      //
        ///////////////////////////////////////////////////////////////

        //Remove this exception once you implement this 
        throw new java.lang.UnsupportedOperationException("simplify not implemented")
    }


    def areEquivalent(e1: Expr, e2: Expr) : Boolean = { 
        ///////////////////////////////////////////////////////////////
        // TODO - This function check if the two expressions are     //
        // equivalent by enumerating their truth tables. You can     //
        // assume that the number of variables and their names will  //
        // be same in both expressions. This function returns true   //
        // if they are equivalent, false if they aren't.             //
        ///////////////////////////////////////////////////////////////
        
        //Remove this exception once you implement this 
        throw new java.lang.UnsupportedOperationException("areEquivalent not implemented")
    }
}
