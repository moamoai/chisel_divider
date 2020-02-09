package simple

import chisel3._
import chisel3.iotesters.PeekPokeTester

/**
 * Test the Divider design
 */

class DividerTester(dut: Divider) extends PeekPokeTester(dut) {

  // This is exhaustive testing, which usually is not possible
  for (dividend  <- 8 to 16) {
    for (divisor <- 4 to 4) {
      poke(dut.io.dividend, dividend)
      poke(dut.io.divisor , divisor)
      step(1)
      val exp_quotient  = dividend / divisor
      val exp_remainder = dividend % divisor
      expect(dut.io.quotient , exp_quotient)
      expect(dut.io.remainder, exp_remainder)

      var quotient  = peek(dut.io.quotient )
      var remainder = peek(dut.io.remainder)
      println(f"dividend[0x$dividend%08x] divisor[0x$divisor%08x] quotient[0x$exp_quotient%08x] remainder[0x$remainder%08x]")
    }
  }
}

object DividerTester extends App {
  println("Testing the Divider")
  val param = Array("--target-dir", "generated", "--generate-vcd-output", "on")
  iotesters.Driver.execute(Array[String](), () => new Divider()) {
    c => new DividerTester(c)
  }
}
