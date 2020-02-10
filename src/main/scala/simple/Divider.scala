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
    val valid     = Input (UInt(1.W))
    val dividend  = Input (UInt(16.W))
    val divisor   = Input (UInt(16.W))
    val quotient  = Output(UInt(16.W))
    val remainder = Output(UInt(16.W))
  })

  // Use shorter variable names
  val dividend  = io.dividend 
  val divisor   = io.divisor  
  val valid     = io.valid
  val quotient  = Wire(UInt (16.W))
  val remainder = Wire(UInt (16.W))

  // some default value is needed
  // quotient  := 0.U
  // remainder := 0.U

//  quotient  := dividend / divisor
//  remainder := dividend % divisor

  val r_dividend = RegInit(0.U(16.W))
  val r_quotient = RegInit(0.U(16.W))

  when(valid === 1.U) {
    r_dividend := dividend
    r_quotient := 0.U
  }.elsewhen(r_dividend >= divisor){
    r_dividend := r_dividend - divisor
    r_quotient := r_quotient + 1.U
  }.otherwise {
  }

//   for (i <- 1 to 1 by -1) {
//     when(r_dividend >= (divisor<<i)) {
//       r_dividend := r_dividend - (divisor<<i)
//       r_quotient := r_quotient + (1.U<<i)
//     // }.elsewhen(stateReg === full){
//     }.otherwise {
//     }
//     // There should not be an otherwise state
//   }
  remainder := r_dividend
  quotient  := r_quotient

  // Output
  io.quotient  := quotient
  io.remainder := remainder
}

class DividerTop extends Module {
  val io = IO(new Bundle {
    val valid     = Input (UInt(1.W))
    val dividend = Input (UInt(16.W))
    val divisor  = Input (UInt(16.W))
    val quotient = Output(UInt(16.W))
    val remainder = Output(UInt(16.W))
  })

  val div = Module(new Divider())

  // Map switches to the ALU input ports
  // div.io.fn := io.sw(1, 0)
  div.io.valid    := io.valid
  div.io.dividend := io.dividend
  div.io.divisor  := io.divisor

  // And the result to the LEDs (with 0 extension)
  io.quotient  := div.io.quotient
  io.remainder := div.io.remainder
}

// Generate the Verilog code by invoking the Driver
object DividerMain extends App {
  // println("1<<2 = "+(1<<2))
  println("Generating the Divider hardware")
  chisel3.Driver.execute(Array("--target-dir", "generated"), () => new DividerTop())
}