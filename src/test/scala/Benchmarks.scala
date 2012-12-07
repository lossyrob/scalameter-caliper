package scalametercaliper

import org.scalameter.api._

import scala.util.Random

object LoopBenchmark extends PerformanceTest.Quickbenchmark {
  val size = 1000
  var ints:Array[Int] = null
  var doubles:Array[Double] = null

  /**
   * Sugar for building arrays using a per-cell init function.
   */
  def init[A:Manifest](size:Int)(init: => A) = {
    val data = Array.ofDim[A](size)
    for (i <- 0 until size) data(i) = init
    data
  }

  val len = size * size
  ints = init(len)(Random.nextInt)
  doubles = init(len)(Random.nextDouble)

  performance of "while loop" in {
    measure method "int array" in {
      using(Gen.single("ints")(ints)) in { ints =>
        val goal = ints.clone
        var i = 0
        val len = goal.length
        while (i < len) {
        val z = goal(i)
        if (z != Int.MinValue) goal(i) = z * 2
          i += 1
        }
      }
    }

    measure method "double array" in {
      using(Gen.single("doubles")(doubles)) in { doubles =>
        val goal = doubles.clone
        var i = 0
        val len = goal.length
        while (i < len) {
          val z = goal(i)
          if (z != Double.NaN) goal(i) = z * 2.0
          i += 1
        }
      }
    }
  }
}

