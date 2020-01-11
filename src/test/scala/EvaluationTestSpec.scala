import org.scalatest.FlatSpec
import scala.util.Try
import scala.io.Source

class EvaluationTestSpec extends FlatSpec {

    def and(args: Expr*): Expr = And(args.toList)
    def or(args: Expr*): Expr = Or(args.toList)
    def not(arg: Expr): Expr = Not(arg)
    def var_(name: String) : Variable = Variable(name)
    def lit(value: Boolean): Expr = BoolLit(value)
    def a = var_("a")
    def b = var_("b")
    def c = var_("c")
    def d = var_("d")
    def e = var_("e")
    def f = var_("f")
    def g = var_("g")
    def h = var_("h")

    val path = "testcases/evaluation/"

    def getTestCase(file: String, testnum: Int): (Boolean, Expr) = {
        val inputfile = Source.fromFile(path + file)  
        val inputfilecontent   = inputfile.mkString
        val rawFile = ScalaParser.parseStatement(inputfilecontent)
        ScalaInterpreter.cleanup
        rawFile match {
            case Some(text1) =>
                ScalaInterpreter.interpretStmt(text1)
                val expressions1 = ScalaInterpreter.outputVarMap
                var testcases = new Array[Expr](expressions1.size)
                var index = 0
                expressions1.foreach {
                    case(output, exp) =>
                        testcases(index) = exp
                        index += 1
                }
                (true, testcases(testnum))
            case None => assert(false, "Parse error"); (false, BoolLit(false))
        }                                        
    }

    //Visible Test Cases


    "Evaluation Test 1 - " should "run successfully" in {
        val exp = or(and(a, b), lit(false), not(c))
        val m1 = Map(a -> true, b -> false, c -> true) 
        val m2 = Map(a -> true, b -> true, c -> true) 
        val m3 = Map(a -> true, b -> false, c -> false) 
        assert(Evaluation.evaluate(exp, m1) == Some(false), "Subtest 1 failed")
        assert(Evaluation.evaluate(exp, m2) == Some(true), "Subtest 2 failed")
        assert(Evaluation.evaluate(exp, m3) == Some(true), "Subtest 3 failed")
    }

    "Evaluation Test 2 - " should "run successfully" in {
        val exp = or(and(a, b, c), lit(false), not(c), or(b, not(d)))
        val m1 = Map(a -> true, b -> false, c -> true) 
        val m2 = Map(a -> true, b -> true, c -> true, d -> true) 
        val m3 = Map(a -> true, b -> false, c -> false, d -> true) 
        val m4 = Map(a -> true, b -> false, c -> true, d->true) 
        val m5 = Map(a -> true, b -> false, c -> true, d->false)
        assert(Evaluation.evaluate(exp, m1) == None, "Subtest 1 failed") 
        assert(Evaluation.evaluate(exp, m2) == Some(true), "Subtest 2 failed")
        assert(Evaluation.evaluate(exp, m3) == Some(true), "Subtest 3 failed")
        assert(Evaluation.evaluate(exp, m4) == Some(false), "Subtest 4 failed")
        assert(Evaluation.evaluate(exp, m5) == Some(true), "Subtest 5 failed")
    }

    "Evaluation Test 3 - " should "run successfully" in {
        info("Checking -> test3.txt")
        val testcase = getTestCase("test3.txt", 0)
        testcase match {
            case (success, exp) =>
                if(success) {
                    val m1 = Map(a -> true, b -> false, c -> true, d->true) 
                    val m2 = Map(a -> true, b -> true, c -> true, d -> true, e->false, f->true, g->true)
                    val m3 = Map(a -> true, b -> true, c -> false, d -> true, e->false, f->true, g->true) 
                    val m4 = Map(a -> true, b -> true, c -> false, d -> true, e->true, f->false, g->true)
                    val m5 = Map(a -> true, b -> false, c -> true, d -> true, e->true, f->true, g->false) 
                    assert(Evaluation.evaluate(exp, m1) == None, "Subtest 1 failed")
                    assert(Evaluation.evaluate(exp, m2) == Some(false), "Subtest 2 failed")
                    assert(Evaluation.evaluate(exp, m3) == Some(false), "Subtest 3 failed")
                    assert(Evaluation.evaluate(exp, m4) == Some(false), "Subtest 4 failed")
                    assert(Evaluation.evaluate(exp, m5) == Some(true), "Subtest 5 failed")
                } else {
                    assert(false, "Testcase Error")
                }
        }
    }

}