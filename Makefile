#
# Building Chisel examples without too much sbt/scala/... stuff
#
# sbt looks for default into a folder ./project and . for build.sdt and Build.scala
# sbt creates per default a ./target folder

SBT = sbt

# Generate Verilog code

alu:
	$(SBT) "runMain simple.AluMain"

div:
	$(SBT) "runMain simple.DividerMain"

# Generate the C++ simulation and run the tests

alu-test:
	$(SBT) "test:runMain simple.AluTester"

div-test:
	$(SBT) "test:runMain simple.DividerTester --backend-name verilator"

counter-test:
	$(SBT) "test:runMain simple.CounterTester"

GTKWAVE = /Applications/gtkwave.app/Contents/Resources/bin/gtkwave
view:
	$(GTKWAVE) ./test_run_dir/simple.DividerTester2058623782/Driver.gtkw

# clean everything (including IntelliJ project settings)

clean:
	git clean -fd
