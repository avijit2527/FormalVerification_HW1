import org.scalatest.FlatSpec

class SimplificationSpec extends FlatSpec {

    def and(args: Expr*): Expr = And(args.toList)
    def or(args: Expr*): Expr = Or(args.toList)
    def not(arg: Expr): Expr = Not(arg)
    def var_(name: String) : Variable = Variable(name)
    def a = var_("a")
    def b = var_("b")
    def c = var_("c")
    def d = var_("d")
    def e = var_("e")
    def f = var_("f")
    def g = var_("g")
    def lit(value: Boolean): Expr = BoolLit(value)

    /*
        Simplification Rules 
        1. And(e, false) = false
        2. Or(e, true) = true
        3. And(e, e) = e
        4. Or(e, e) = e
        5. And(e, !e) = false
        6. Or(e, !e) = true
        7. And(e, true) = e
        8. Or(e, false) = e
        9. And(e) = Or(e) = e
        10. Not(false) = true and Not(true) = false

    */

    //Visible Test Cases
    
    "Test 1 - Basic" should "run successfully" in {
        val exp = not(not(a))
        val simpleExp = a
        val m = Map( a-> true)
        assert(Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }

    "Test 2 - Rule 1" should "simplify as per And(e, false) = false" in {
        val exp = and(and(a, not(b), c), and(d, not(e), lit(false)), or(lit(true), lit(false)))
        val simpleExp = lit(false)
        val m = Map( a-> true, b->false, c->true, d->true, e->false)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }

    "Test 3 - Rule 2" should "simplify as per Or(e, true) = true" in {
        val exp = or(and(a, lit(false)), not(lit(true)), or(or(b, c, lit(true)), not(d)))
        val simpleExp = lit(true)
        val m = Map( a-> true, b->false, c->true, d->true)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }

    "Test 4 - Rule 3 and Rule 4 and Rule 9" should "simplify as per And(e, e) = e and Or(e, e) = e and And(e) = Or(e) = e" in {
        val exp = and(or(and(a,b), and(a,b), and(c, c, d, d), or(b)))
        val simpleExp = or(and(a,b), and(c,d), b)
        val m = Map( a-> true, b->false, c->true, d->true)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }

    "Test 5 - Rule 5 and Rule 6 (Trivial)" should "simplify as per And(e, !e) = false and Or(e, !e) = true" in {
        val exp = and(a, not(and(lit(true), a)))
        val simpleExp = lit(false)
        val m = Map( a-> true, b->false, c->true, d->true)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))

        val exp2 = or(and(a, not(and(lit(true), a))), and(a, not(b)), not(and(a, not(b))), c)
        val simpleExp2 = lit(true)
        assert (Evaluation.simplify(exp2) == simpleExp2)
        assert(Evaluation.evaluate(exp2, m) == Evaluation.evaluate(Evaluation.simplify(exp2), m))
    }

    "Test 6 - Rule 5 and Rule 6 and Rule 12(Non-Trivial)" should "simplify as per above" in {

        val exp = or(and(a, not(and(lit(true), a))), c, not(or(and(a, not(b)), not(and(a, not(b))))))
        val simpleExp = c
        val m = Map( a-> true, b->false, c->true, d->true)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }


    "Test 7 - Rule 7 and Rule 8" should "simplify as per And(e, true) = e and Or(e, false) = e" in {

        val exp = or(and(a, b, lit(true)), and(or(b, lit(false)), c))
        val simpleExp = or(and(a, b), and(b, c))
        val m = Map( a-> true, b->false, c->true, d->true)
        assert (Evaluation.simplify(exp) == simpleExp)
        assert(Evaluation.evaluate(exp, m) == Evaluation.evaluate(Evaluation.simplify(exp), m))
    }


}