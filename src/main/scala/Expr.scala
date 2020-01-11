
abstract class Expr

case class BoolLit(value: Boolean) extends Expr {
    override def toString = value.toString()
}

case class Variable(name: String) extends Expr {
    override def toString(): String = name
} 

case class And(args: List[Expr]) extends Expr {
    override def toString(): String = {
        args.map(x => x.toString()).mkString("("," && ",")")
    }
}
case class Or(args: List[Expr]) extends Expr {
    override def toString(): String = {
        args.map(x => x.toString()).mkString("("," || ",")")
    }
}
case class Not(arg: Expr) extends Expr {
    override def toString(): String = {
        "~" + "(" + arg.toString() + ")"
    }
}

////////////////////////////////////////////////
//   IGNORE THE CASE CLASSES BELOW THIS LINE  //
////////////////////////////////////////////////

case class GateVariable(name: String) extends Expr {
    override def toString(): String = name
}

case class Buf(arg: Expr) extends Expr {
    override def toString(): String = {
        "(" + arg.toString() + ")"
    }
}

case class Nand(args: List[Expr]) extends Expr {
    override def toString(): String = {
        args.map(x => x.toString()).mkString("~("," && ",")")
    }
}

case class Nor(args: List[Expr]) extends Expr {
    override def toString(): String = {
        args.map(x => x.toString()).mkString("~("," || ",")")
    }
}

case class Xor(args: List[Expr]) extends Expr {
    override def toString(): String = {
        args.map(x => x.toString()).mkString("("," ^ ",")")
    }
}

abstract class Statement 

case class VariableDeclaration(varname: Variable, typ: Boolean) extends Statement {
    override def toString = "var: " + ( if (typ == true) "input" else "output") + " " + varname.toString + "\n"
}

case class GateDefinition(lhs: GateVariable, rhs: Expr) extends Statement {
    override def toString = lhs.toString() + " = " + rhs.toString() + "\n"
}

