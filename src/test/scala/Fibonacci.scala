package scalametercaliper

import org.scalameter.api._

import scala.util.Random

object FibonacciBenchmark extends PerformanceTest.Quickbenchmark {
  performance of ("fibonacci") in {
    using(Gen.single("limit")(40)) in { n =>
      if (n < 0) throw new IllegalArgumentException("n = " + n + " < 0")
      def _f(x:Int):Int = {
        if (x <= 1) n else { _f(x - 1) + _f(x - 2) }
      }
      _f(n)
    }
  }
}

