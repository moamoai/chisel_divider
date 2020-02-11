# chisel_divider


### Hardware Specification
* 入力データは16bit unsigned int
* valid==1で入力(dividend, divisor)をラッチ。内部カウンタ(r_counter)を初期化(16)。
* validデアサートして16サイクル後に除算結果を出力(quotient, remainder)。ready信号をアサート(パルス)
* r_divisorをシフトして、r_dividendが引けそうなら引いて、r_quotientに追加。それを16サイクル繰り返し

### Vefrification Specification
* ランダム検証: 入力をランダム値(divisorは1-(1<<16)))に降って2000回テスト。PASS確認