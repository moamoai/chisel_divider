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
    val ready     = Output(UInt(1.W))
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

  val r_ready    = RegInit(0.U(1.W))
  val r_counter  = RegInit(16.U(5.W))
  val r_dividend = RegInit(0.U(16.W))
  val r_quotient = RegInit(0.U(16.W))

// // substract only
//   when(valid === 1.U) {
//     r_ready    := 0.U
//     r_dividend := dividend
//     r_quotient := 0.U
//   }.elsewhen(r_dividend >= divisor){
//     r_dividend := r_dividend - divisor
//     r_quotient := r_quotient + 1.U
//   }.otherwise {
//     r_ready    := 1.U
//   }

  // shift + substract
  when(valid === 1.U) {
    r_ready    := 0.U
    r_counter  := 16.U
    r_dividend := dividend
    r_quotient := 0.U
  }.elsewhen(r_counter!=0.U){
    when(r_dividend >= (divisor<<(r_counter-1.U))){
      r_dividend    := r_dividend - (divisor<<(r_counter-1.U))
      r_quotient    := r_quotient + (1.U<<(r_counter-1.U))
    }.otherwise {
    }
    r_counter    := r_counter - 1.U
  }.otherwise {
    r_ready    := 1.U
  }
  
  remainder := r_dividend
  quotient  := r_quotient

  // Output
  io.ready     := r_ready
  io.quotient  := quotient
  io.remainder := remainder
}

class DividerTop extends Module {
  val io = IO(new Bundle {
    val valid     = Input (UInt(1.W))
    val dividend  = Input (UInt(16.W))
    val divisor   = Input (UInt(16.W))
    val quotient  = Output(UInt(16.W))
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