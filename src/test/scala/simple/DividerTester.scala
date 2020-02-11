package simple

import chisel3._
import chisel3.iotesters.PeekPokeTester

/**
 * Test the Divider design
 */

class DividerTester(dut: Divider) extends PeekPokeTester(dut) {

  // This is exhaustive testing, which usually is not possible
//  for (dividend  <- 8 to 36 by 1) {
//    for (divisor <- 3 to 5) {
  for (i  <- 1 to 1000 by 1) {
    for (j <- 1 to 1) {
      var dividend = rnd.nextInt((1<<16)) 
      var divisor  = rnd.nextInt((1<<16)-1) + 1
      // var divisor  = rnd.nextInt((1<<8)-1) + 1
      poke(dut.io.valid, 1)
      poke(dut.io.dividend, dividend)
      poke(dut.io.divisor , divisor)
      step(1)
      poke(dut.io.valid, 0)
      var ready = peek(dut.io.ready)
      while (ready == 0){
        step(1)
        ready = peek(dut.io.ready)
      }
      val exp_quotient  = dividend / divisor
      val exp_remainder = dividend % divisor
      expect(dut.io.quotient , exp_quotient)
      expect(dut.io.remainder, exp_remainder)

      var quotient   = peek(dut.io.quotient )
      var remainder  = peek(dut.io.remainder)
      println(f"dividend[0x$dividend%08x] divisor[0x$divisor%08x] quotient[0x$quotient%08x] remainder[0x$remainder%08x]")
      step(2)

      // var r_dividend = peek(dut.r_dividend)
      // var r_quotient = peek(dut.r_quotient)
      // println(f"r_dividend[0x$r_dividend%08x] r_quotient[0x$r_quotient%08x]")
    }
  }
}

object DividerTester extends App {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  println("Testing the Divider")
  val param = Array("--target-dir", "generated", "--generate-vcd-output", "on")

  iotesters.Driver.execute(Array[String]("--generate-vcd-output", "on"), () => new Divider()) {
    c => new DividerTester(c)
  }
}
