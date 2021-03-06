package org.jetbrains.plugins.scala.lang.transformation
package implicits

import org.jetbrains.plugins.scala.extensions.{FirstChild, ImplicitConversion}
import org.jetbrains.plugins.scala.lang.psi.api.base.patterns.ScReferencePattern
import org.jetbrains.plugins.scala.lang.psi.api.expr.ScReferenceExpression
import org.jetbrains.plugins.scala.lang.psi.api.statements.ScFunction
import org.jetbrains.plugins.scala.lang.psi.impl.ScalaCode._

/**
  * @author Pavel Fatin
  */
object ExpandImplicitConversion extends AbstractTransformer {
  // TODO we need to aquire complete resolve result, not the bare element to account for substitutor
  def transformation = {
    case e @ ImplicitConversion(f: ScFunction) =>
      val FirstChild(reference: ScReferenceExpression) = e.replace(code"${f.name}($e)")
      bindTo(reference, qualifiedNameOf(f))

    case e @ ImplicitConversion(p: ScReferencePattern) =>
      val FirstChild(reference: ScReferenceExpression) = e.replace(code"${p.name}($e)")
      bindTo(reference, qualifiedNameOf(p))
  }
}
