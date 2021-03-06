package examples.trimchain.core

import scorex.crypto.hash.Blake2b256


object Constants {
  val n = 20
  val k = 1
  val NElementsInProof = 10

  val hashfn = Blake2b256

  val StateRootLength = hashfn.DigestSize

  val TxRootLength = hashfn.DigestSize


  lazy val MaxTarget = BigInt(1, Array.fill(32)(Byte.MinValue))
  lazy val Difficulty = BigInt("2")
}