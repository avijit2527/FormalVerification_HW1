import scala.util.Try
import scala.io.Source
import scala.collection.mutable

object Main {  

    def and(args: Expr*): Expr = And(args.toList)
    def or(args: Expr*): Expr = Or(args.toList)
    def not(arg: Expr): Expr = Not(arg)
    def var_(name: String) : Variable = Variable(name)
    def lit(value: Boolean): Expr = BoolLit(value)
    def a = var_("a")
    def b = var_("b")
    def c = var_("c")
    def d = var_("d")

    def testEvaluate = {

        val m1 = Map[Variable, Boolean]()                             //empty map
        val m2 = Map(a -> true)                                       //1 variable
        val m3 = Map(a -> true, b -> false)                           //2 variables
        val m4 = Map(a -> true, b -> false, c -> true, d -> false)    //All variables
        val m5 = Map(a -> true, b -> true, c -> true, d -> false)     //All variables
        val m6 = Map(a -> false, b -> false)                          //All variables
        val m7 = Map(a -> true, b -> false, c -> true)                //3 Variables
        val exp1 = and(a, b)
        val exp2 = and(a, b)
        val exp3 = and(a, b, or(c, d))
        val exp4 = or(b, and(a,b,c), and(lit(false), lit(true)))
        val exp5 = and(a)
        val exp6 = or(and(a, b, c), lit(false), not(c), or(b, not(d)))
        assert(Evaluation.evaluate(exp1, m1) == None, "Test 1 Failed")
        assert(Evaluation.evaluate(exp1, m2) == None, "Test 2 Failed")
        assert(Evaluation.evaluate(exp5, m1) == None, "Test 3 Failed")
        assert(Evaluation.evaluate(exp5, m2) == Some(true), "Test 4 Failed")
        assert(Evaluation.evaluate(exp2, m2) == None, "Test 5 Failed")
        assert(Evaluation.evaluate(exp2, m3) == Some(false), "Test 6 Failed")
        assert(Evaluation.evaluate(exp3, m6) == None, "Test 7 Failed")
        assert(Evaluation.evaluate(exp3, m4) == Some(false), "Test 8 Failed")
        assert(Evaluation.evaluate(exp4, m4) == Some(false), "Test 9 Failed")
        assert(Evaluation.evaluate(exp4, m5) == Some(true), "Test 10 Failed")
        assert(Evaluation.evaluate(exp6, m7) == None, "Test 11 Failed")
    }

    def testEquivalence = {

        val exp1 = and(a,b)
        val exp2 = not(or(not(a), not(b)))
        assert(Evaluation.areEquivalent(exp1, exp2) == true)
        assert(Evaluation.areEquivalent(exp1, not(exp2)) == false)

        val exp3 = and(a,b,c,d)
        val exp4 = and(and(a,b), and(c,d), lit(true))
        assert(Evaluation.areEquivalent(exp3, exp4) == true)
        assert(Evaluation.areEquivalent(exp3, not(exp4)) == false)

        val exp5 = and(or(a,b),and(c,not(d)))
        val exp6 = and(a,b,c,not(d))
        assert(Evaluation.areEquivalent(exp5, not(exp6)) == false)
    }
    
    def testSimplify = {

        val exp1 = Not(Not(a))
        val exp2 = not(or(not(a), not(b)))


      
    }


    def main(args: Array[String]) 
    { 

        testEvaluate
        testEquivalence
        testSimplify
        /*
            Add more tests here
        */
        println("All Tests Passed.")
    } 
}  
