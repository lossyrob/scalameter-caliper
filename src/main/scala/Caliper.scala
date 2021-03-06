package scalametercaliper

import com.google.caliper.Benchmark
import com.google.caliper.Param
import com.google.caliper.Runner 
import com.google.caliper.SimpleBenchmark
import com.google.caliper.UserException
import com.google.caliper.UserException.{DisplayUsageException}
import com.google.common.base.Splitter

import scala.util.Random

object TestCaliper {
  def main(args:Array[String]):Unit = {
    try {
      val default_args = Array[String]("--warmupMillis", "3000", 
                                       "--runMillis", "1000", 
                                       "--measurementType", "TIME", 
                                       "--marker", "//ZxJ/", 
                                       "scalametercaliper.Benchmarks")
      new Runner().run(default_args:_*)
      System.exit(0) // user code may have leave non-daemon threads behind!
    } catch {
      case e:DisplayUsageException  =>
        e.display()
        System.exit(0)
      case e:UserException =>
        e.display()
        System.exit(1)
    }
  }
}

class Benchmarks extends SimpleBenchmark {
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

  /**
   * Sugar to run 'f' for 'reps' number of times.
   */
  def run(reps:Int)(f: => Unit) = {
    var i = 0
    while (i < reps) { f; i += 1 }
  }

  override def setUp() {
    val len = size * size
    ints = init(len)(Random.nextInt)
    doubles = init(len)(Random.nextDouble)
  }


  def timeIntArrayWhileLoop(reps:Int) = run(reps)(intArrayWhileLoop)
  def intArrayWhileLoop = {
    val goal = ints.clone
    var i = 0
    val len = goal.length
    while (i < len) {
      val z = goal(i)
      if (z != Int.MinValue) goal(i) = z * 2
      i += 1
    }
  }
  
  def timeDoubleArrayWhileLoop(reps:Int) = run(reps)(doubleArrayWhileLoop)
  def doubleArrayWhileLoop = {
    val goal = doubles.clone
    var i = 0
    val len = goal.length
    while (i < len) {
      val z = goal(i)
      if (z != Double.NaN) goal(i) = z * 2.0
      i += 1
    }
  }

  def timeIntArrayForLoop(reps:Int) = run(reps)(intArrayForLoop)
  def intArrayForLoop = {
  val goal = ints.clone
    val len = goal.length
    for(i <- 0 to ints.length-1) {
      val z = goal(i)
      if (z != Int.MinValue) goal(i) = z * 2
    }
  }

  def timeDoubleArrayForLoop(reps:Int) = run(reps)(doubleArrayForLoop)
  def doubleArrayForLoop = {
  val goal = doubles.clone
    val len = goal.length
    for(i <- 0 to doubles.length-1) {
      val z = goal(i)
      if (z != Double.MinValue) goal(i) = z * 2.0
    }
  }

  def timefibonacci(reps:Int) = run(reps)(fibonacci(40))
  def fibonacci(n:Int):Unit = {
    if (n < 0) throw new IllegalArgumentException("n = " + n + " < 0")
    def _f(x:Int):Int = {
      if (x <= 1) n else { _f(x - 1) + _f(x - 2) }
    }
    _f(n)
  }
}
















