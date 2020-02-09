/*
 *
 * Divider Module
 *
 */

package simple

import chisel3._
import chisel3.util._

class Divider extends Module {
  val io = IO(new Bundle {
//    val fn = Input(UInt(2.W))
    val dividend  = Input (UInt(16.W))
    val divisor   = Input (UInt(16.W))
    val quotient  = Output(UInt(16.W))
    val remainder = Output(UInt(16.W))
  })

  // Use shorter variable names
  val dividend  = io.dividend 
  val divisor   = io.divisor  
  val quotient  = Wire(UInt (16.W))
  val remainder = Wire(UInt (16.W))

  // some default value is needed
  quotient  := 0.U
  remainder := 0.U

  quotient  := dividend / divisor
  remainder := dividend % divisor

  // Output
  io.quotient  := quotient
  io.remainder := remainder
}

class DividerTop extends Module {
  val io = IO(new Bundle {
    val dividend = Input (UInt(16.W))
    val divisor  = Input (UInt(16.W))
    val quotient = Output(UInt(16.W))
    val remainder = Output(UInt(16.W))
  })

  val div = Module(new Divider())

  // Map switches to the ALU input ports
  // div.io.fn := io.sw(1, 0)
  div.io.dividend := io.dividend
  div.io.divisor  := io.divisor

  // And the result to the LEDs (with 0 extension)
  io.quotient  := div.io.quotient
  io.remainder := div.io.remainder
}

// Generate the Verilog code by invoking the Driver
object DividerMain extends App {
  println("Generating the Divider hardware")
  chisel3.Driver.execute(Array("--target-dir", "generated"), () => new DividerTop())
}