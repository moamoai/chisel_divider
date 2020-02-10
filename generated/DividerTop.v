module Divider(
  input         clock,
  input         reset,
  input         io_valid,
  input  [15:0] io_dividend,
  input  [15:0] io_divisor,
  output [15:0] io_quotient,
  output [15:0] io_remainder
);
  reg [15:0] r_dividend; // @[Divider.scala 36:27]
  reg [31:0] _RAND_0;
  reg [15:0] r_quotient; // @[Divider.scala 37:27]
  reg [31:0] _RAND_1;
  wire  _T_1; // @[Divider.scala 44:23]
  wire [15:0] _T_3; // @[Divider.scala 45:34]
  wire [15:0] _T_5; // @[Divider.scala 46:34]
  assign _T_1 = r_dividend >= io_divisor; // @[Divider.scala 44:23]
  assign _T_3 = r_dividend - io_divisor; // @[Divider.scala 45:34]
  assign _T_5 = r_quotient + 16'h1; // @[Divider.scala 46:34]
  assign io_quotient = r_quotient; // @[Divider.scala 66:16]
  assign io_remainder = r_dividend; // @[Divider.scala 67:16]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
  `ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  r_dividend = _RAND_0[15:0];
  `endif // RANDOMIZE_REG_INIT
  `ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  r_quotient = _RAND_1[15:0];
  `endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      r_dividend <= 16'h0;
    end else if (io_valid) begin
      r_dividend <= io_dividend;
    end else if (_T_1) begin
      r_dividend <= _T_3;
    end
    if (reset) begin
      r_quotient <= 16'h0;
    end else if (io_valid) begin
      r_quotient <= 16'h0;
    end else if (_T_1) begin
      r_quotient <= _T_5;
    end
  end
endmodule
module DividerTop(
  input         clock,
  input         reset,
  input         io_valid,
  input  [15:0] io_dividend,
  input  [15:0] io_divisor,
  output [15:0] io_quotient,
  output [15:0] io_remainder
);
  wire  div_clock; // @[Divider.scala 79:19]
  wire  div_reset; // @[Divider.scala 79:19]
  wire  div_io_valid; // @[Divider.scala 79:19]
  wire [15:0] div_io_dividend; // @[Divider.scala 79:19]
  wire [15:0] div_io_divisor; // @[Divider.scala 79:19]
  wire [15:0] div_io_quotient; // @[Divider.scala 79:19]
  wire [15:0] div_io_remainder; // @[Divider.scala 79:19]
  Divider div ( // @[Divider.scala 79:19]
    .clock(div_clock),
    .reset(div_reset),
    .io_valid(div_io_valid),
    .io_dividend(div_io_dividend),
    .io_divisor(div_io_divisor),
    .io_quotient(div_io_quotient),
    .io_remainder(div_io_remainder)
  );
  assign io_quotient = div_io_quotient; // @[Divider.scala 88:16]
  assign io_remainder = div_io_remainder; // @[Divider.scala 89:16]
  assign div_clock = clock;
  assign div_reset = reset;
  assign div_io_valid = io_valid; // @[Divider.scala 83:19]
  assign div_io_dividend = io_dividend; // @[Divider.scala 84:19]
  assign div_io_divisor = io_divisor; // @[Divider.scala 85:19]
endmodule
