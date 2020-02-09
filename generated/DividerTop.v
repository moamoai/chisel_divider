module Divider(
  input  [15:0] io_dividend,
  input  [15:0] io_divisor,
  output [15:0] io_quotient,
  output [15:0] io_remainder
);
  wire [15:0] _GEN_0; // @[Divider.scala 36:16]
  assign io_quotient = io_dividend / io_divisor; // @[Divider.scala 35:16]
  assign _GEN_0 = io_dividend % io_divisor; // @[Divider.scala 36:16]
  assign io_remainder = _GEN_0[15:0]; // @[Divider.scala 36:16]
endmodule
module DividerTop(
  input         clock,
  input         reset,
  input  [15:0] io_dividend,
  input  [15:0] io_divisor,
  output [15:0] io_quotient,
  output [15:0] io_remainder
);
  wire [15:0] div_io_dividend; // @[Divider.scala 47:19]
  wire [15:0] div_io_divisor; // @[Divider.scala 47:19]
  wire [15:0] div_io_quotient; // @[Divider.scala 47:19]
  wire [15:0] div_io_remainder; // @[Divider.scala 47:19]
  Divider div ( // @[Divider.scala 47:19]
    .io_dividend(div_io_dividend),
    .io_divisor(div_io_divisor),
    .io_quotient(div_io_quotient),
    .io_remainder(div_io_remainder)
  );
  assign io_quotient = div_io_quotient; // @[Divider.scala 55:16]
  assign io_remainder = div_io_remainder; // @[Divider.scala 56:16]
  assign div_io_dividend = io_dividend; // @[Divider.scala 51:19]
  assign div_io_divisor = io_divisor; // @[Divider.scala 52:19]
endmodule
